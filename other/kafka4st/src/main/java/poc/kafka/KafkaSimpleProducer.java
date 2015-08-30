package poc.kafka;

import java.util.Properties;

import kafka.admin.AdminUtils;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.Partitioner;
import kafka.producer.ProducerConfig;
import kafka.utils.VerifiableProperties;
import kafka.utils.ZKStringSerializer$;

import org.I0Itec.zkclient.ZkClient;

public class KafkaSimpleProducer {
	private Properties props;

	public KafkaSimpleProducer() {

		// The first step in your code is to define properties for how the
		// Producer finds the cluster,
		// serializes the messages and if appropriate directs the message to a
		// specific Partition.
		props = new Properties();

		props.put("metadata.broker.list", "localhost:"+Integer.getInteger("kafka.port",9001));
		props.put("serializer.class", "kafka.serializer.StringEncoder");
//		props.put("partitioner.class", "poc.kafka.KafkaSimpleProducer.SimplePartitioner");
		props.put("request.required.acks", "1");
		createTopic();
	}

	public void sendMessage(String msg){
		ProducerConfig config = new ProducerConfig(props);
		//the Producer is a Java Generic and you need to tell it the type of two parameters. The first is the type of the Partition key, 
		//the second the type of the message. 
		Producer<String, String> producer = new Producer<String, String>(config);
		KeyedMessage<String, String> data = new KeyedMessage<String, String>(System.getProperty("kafka.topic", "testtopic") ,msg);
		producer.send(data);
	}
	
	private void createTopic() {
		/*
		 * Note that when not initializing the ZkClient with ZKStringSerializer,
		 * createTopic will return without error. The topic will exist in
		 * zookeeper and be returned when listing topics, but Kafka itself does
		 * not create the topic
		 * http://stackoverflow.com/questions/16946778/how-can-we-create-a-topic-in-kafka-from-the-ide-using-api
		 */
		ZkClient zkClient = new ZkClient(Zookeeper.getZkConnectionString(), 10000, 10000, ZKStringSerializer$.MODULE$);
		String topicName = System.getProperty("kafka.topic", "testtopic");
		if(AdminUtils.topicExists(zkClient, topicName)){
			System.out.println("Topic " + topicName + " already exist.");
		}
		try {
			AdminUtils.createTopic(zkClient, topicName, 1, 1, new Properties());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("created topic-" + topicName);
	}

	public static class SimplePartitioner implements Partitioner {
	    public SimplePartitioner (VerifiableProperties props) {
	 
	    }
	 
	    public int partition(Object key, int a_numPartitions) {
	        String stringKey = (String) key;
	        return Integer.parseInt( stringKey) % a_numPartitions;
	  }
	 
	}
	

}
