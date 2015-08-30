package poc.kafka;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
	
	private AppConfig() {
		
	}
	
	public static void loadConfig(){
		InputStream inputStream  = AppConfig.class.getClassLoader().getResourceAsStream("kafka.properties");
		   Properties p =
		            new Properties(System.getProperties());
		        try {
					p.load(inputStream);
				} catch (IOException e) {
					e.printStackTrace();
				}
		        System.setProperties(p);
	}
	
}
