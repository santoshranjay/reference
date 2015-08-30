package poc.kafka;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class Main 
{
    public static void main( String[] args )
    {
    	AppConfig.loadConfig();
    	new Main().startZookeeperAndKafka();
    	KafkaSimpleProducer producer = new KafkaSimpleProducer();
    	for(int i=0; i<500; i++){
    		producer.sendMessage("Hello " + i);
    		try {
    			TimeUnit.SECONDS.sleep(5);
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    	try {
			TimeUnit.MINUTES.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.exit(1);
    }
    
    public void startZookeeperAndKafka(){
    	Zookeeper zkserver = new Zookeeper(Integer.getInteger("zk.port",8001));
    	Kafka kafkaServer = new Kafka(Zookeeper.getZkConnectionString(),Integer.getInteger("kafka.id",1));
    	Thread th = new Thread(new ShutDownHook(zkserver,kafkaServer));
    
    	Runtime.getRuntime().addShutdownHook(th);
    	try {
			zkserver.startup();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	try {
			kafkaServer.startup();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private static final class ShutDownHook implements Runnable{

    	final Zookeeper zkserver;
    	final Kafka kafkaServer;
    	
    	public ShutDownHook(Zookeeper zk, Kafka kafka) {
    		this.zkserver = zk;
    		this.kafkaServer = kafka;
		}
		public void run() {

			System.out.println("Shutting down kafka - " + kafkaServer.getBrokerId());
			kafkaServer.shutdown();
			zkserver.shutdown();
		}
    	
    }
}
