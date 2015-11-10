package san.poc.kafka.embedded

import java.net.InetSocketAddress
import org.apache.zookeeper.server.ServerCnxnFactory
import org.apache.zookeeper.server.ZooKeeperServer
import java.util.Properties
import kafka.server.KafkaConfig
import kafka.utils.SystemTime
import org.I0Itec.zkclient.ZkClient
import kafka.utils.ZKStringSerializer
import kafka.admin.AdminUtils
import kafka.producer.ProducerConfig
import kafka.consumer.ConsumerConfig
import kafka.consumer.Whitelist
import kafka.serializer.DefaultEncoder
import kafka.serializer.DefaultEncoder
import kafka.serializer.DefaultDecoder
import kafka.producer.KeyedMessage
import java.io.File
import kafka.server.KafkaConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @author santkumk
 */
object KafkaTest extends Logging {

  def main(args: Array[String]): Unit = {
    val kafkaserver = new KafkaServer
    logger.debug("kafka server started.")
    val producer = new Producer("testtopic")
    val actualList = 1 to 10
    for (i <- actualList.toList) producer.send(i.toString())
    val expectedList = scala.collection.mutable.ListBuffer.empty[Int]
    val consumer = new Consumer("testtopic")
    for (i <- actualList.toList) expectedList.append(consumer.next.toString().toInt)
    println("actualList: " + actualList + " ExpectedList: " + expectedList)
    println(actualList.toList == expectedList.toList)
    //stop all services
    consumer.shutdown()
    producer.shutdown()
    kafkaserver.shutdown()
    Thread.sleep(10 * 1000)
  }

}

class KafkaServer(zkPort: Int = 2181, kafkaPort: Int = 2188,
    zkSnapshotDir: String = "d:\\temp\\zk-snapshot", zkLogDir: String = "d:\\temp\\zk-log", tickTime: Int = 500,
    kafkaLogDir: String = "d:\\temp\\kafka-log") extends Logging {

  val zookeeper: ServerCnxnFactory = {
    val factory = ServerCnxnFactory.createFactory(new InetSocketAddress(zkPort), 10)
    new Thread(new Runnable() {
      override def run(): Unit = {
        try {
          factory.startup(new ZooKeeperServer(new File(zkSnapshotDir), new File(zkLogDir), tickTime))
        } catch {
          case e: Exception => println(e)
        }
      }
    }).start
    logger.debug("zookeeper service invoked. waiting 5 sec..")
    Thread.sleep(2 * 1000) //wait 5 sec to start zookeeper
    factory
  }

  val kafkaServer = {
    val prop = new Properties
    prop.setProperty("zookeeper.connect", "localhost:" + zkPort)
    prop.setProperty("broker.id", "0")
    prop.setProperty("host.name", "localhost")
    prop.setProperty("port", kafkaPort.toString())
    prop.setProperty("zookeeper.session.timeout.ms", "10000");
    prop.setProperty("log.dir", kafkaLogDir);
    prop.setProperty("log.flush.interval.messages", String.valueOf(1));
    val server = new kafka.server.KafkaServer(new KafkaConfig(prop), SystemTime)
    new Thread(new Runnable() {
      override def run(): Unit = {
        try { server.startup() }
        catch { case e: Exception => logger.warn("error starting kafka server", e) }
      }
    }).start
    logger.debug("kafka server started. waiting 5 sec")
    Thread.sleep(2 * 1000) //wait 5 sec to start kafka
    server

  }

  def shutdown(): Unit = {
    kafkaServer.shutdown()
    zookeeper.shutdown()
  }

}

class Producer(val topic: String, zkConn: String = "localhost:2181", kafkaBroker: String = "localhost:2188") extends Logging {
  logger.debug("creating producer")
  val zkclient = new ZkClient(zkConn, 10000, 10000, ZKStringSerializer)
  logger.debug(s"created zkclient at $zkConn")
  if (AdminUtils.topicExists(zkclient, topic)) {
    logger.debug(s"topic already exist $topic . deleting it")
    AdminUtils.deleteTopic(zkclient, topic)
  }
  logger.debug(s"creating topic $topic")
  AdminUtils.createTopic(zkclient, topic, 1, 1, new Properties)
  logger.debug(s"created topic $topic")
  val prop = new Properties
  prop.put("serializer.class", "kafka.serializer.DefaultEncoder")
//  prop.put("key.serializer.class", "kafka.serializer.StringEncoder")
  prop.put("metadata.broker.list", kafkaBroker)
   prop.put("request.required.acks", "1")
  logger.debug(s"cerating producer at $kafkaBroker")
  val producer = new kafka.producer.Producer[String, Array[Byte]](new ProducerConfig(prop))

  def send(msg: String): Unit = {
    val kmsg = new KeyedMessage[String, Array[Byte]](topic, null, msg.getBytes)
    logger.debug(s"sending msg to kafka $msg")
    producer.send(kmsg)
  }

  def shutdown(): Unit = {
    producer.close()
  }
}

class Consumer(topic: String, zkConn: String = "localhost:2181") extends Logging {
     val props: Properties = new Properties
    props.put("zookeeper.connect", zkConn)
    props.put("group.id", "group_1")
    props.put("zookeeper.session.timeout.ms", "400")
    props.put("zookeeper.sync.time.ms", "200")
    props.put("auto.commit.interval.ms", "1000")
    props.put("auto.offset.reset", "smallest")
    props.put("consumer.timeout.ms", "1000")
  logger.debug("creating consumer.")
  val connector = kafka.consumer.Consumer.create(new ConsumerConfig(props))
  logger.debug(s"created consumer at $zkConn on topic $topic")
  val it = connector.createMessageStreamsByFilter(new Whitelist(topic), 1, new DefaultDecoder, new DefaultDecoder)(0).iterator()

  def next = {
    var msg = "0"
    try {
      if (it.hasNext()) {
        msg = new String(it.next.message.asInstanceOf[Array[Byte]])
        logger.debug(s"consumed msg $msg")
      }
    }catch{
    case  e:Exception => logger.warn("Error getting msg ", e)
    }
    msg
  }

  def shutdown(): Unit = connector.shutdown()
}

trait Logging {
  def logger: Logger = LoggerFactory.getLogger(getClass.getName)
}

