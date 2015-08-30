package poc.kafka;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;

import kafka.server.KafkaConfig;
import kafka.server.KafkaServer;

public class Kafka {
	private final int port;
    private final String zkConnection;
    private final Properties baseProperties;
    private final String broker;
    private  KafkaServer kafkaServer;
    private final File logDir;
	private int brokerId;

    public Kafka(String zkConnection, int brokerId) {
        this(zkConnection, new Properties(),brokerId);
    }

    public Kafka(String zkConnection, Properties baseProperties,int brokerId) {
        this(zkConnection, baseProperties, -1,brokerId);
    }

    public Kafka(String zkConnection, Properties baseProperties, int port, int brokerId) {
        this.zkConnection = zkConnection;
        this.port = resolvePort(Integer.getInteger("kafka.port",-1));
        this.baseProperties = baseProperties;
        broker = System.getProperty("kafka.host","localhost")+ ":" + this.port;
        logDir = Util.constructTempDir("kafka-local");
        this.brokerId = brokerId;
    }

    private int resolvePort(int port) {
        if (port == -1) {
            return Util.getAvailablePort();
        }
        return port;
    }

    public void startup() {
            Properties properties = new Properties();
            properties.putAll(baseProperties);
            properties.setProperty("zookeeper.connect", zkConnection);
            properties.setProperty("broker.id", Integer.toString(brokerId));
            properties.setProperty("host.name", System.getProperty("kafka.host","localhost"));
            properties.setProperty("port", Integer.toString(port));
            properties.setProperty("log.dir", logDir.getAbsolutePath());
            properties.setProperty("log.flush.interval.messages", String.valueOf(1));
//            properties.setProperty("  auto.create.topics.enable", "true");
            kafkaServer = startBroker(properties);
    }


    private KafkaServer startBroker(Properties props) {
    	try {
    		 KafkaServer server = new KafkaServer(new KafkaConfig(props), new SystemTime());
    	        server.startup();
    	        return server;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
       return null;
    }

    public void shutdown() {
            try {
                kafkaServer.shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Util.deleteFile(logDir);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Kafka{");
        sb.append("broker='").append(broker).append('\'');
        sb.append('}');
        return sb.toString();
    }

	public int getBrokerId() {
		return brokerId;
	}
}
