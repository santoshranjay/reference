package com.san.ej;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Item 1- consider static factory method instead of constructors.
 * 
 * A service provider framework is a system in which multiple service providers
 * implement a service, and the system makes the implementations available to
 * its clients, decoupling them from the implementations.
 * 
 * There are three essential components of a service provider framework: a
 * service interface, which providers implement; a provider registration API,
 * which the system uses to register implementations, giving clients access to
 * them; and a service access API, which clients use to obtain an instance of
 * the service. The service access API typically allows but does not require the
 * client to specify some criteria for choosing a provider. In the absence of
 * such a specification, the API returns an instance of a default
 * implementation. 
 * 
 * The service access API is the “flexible static factory” that forms the 
 * basis of the service provider framework.
 * 
 * An optional fourth component of a service provider framework is a service
 * provider interface, which providers implement to create instances of their
 * service implementation. In the absence of a service provider interface,
 * implementations are registered by class name and instantiated reflectively
 * (Item 53). In the case of JDBC, Connection plays the part of the service
 * interface, DriverManager.registerDriver is the provider registration API,
 * DriverManager.getConnection is the service access API, and Driver is the
 * service provider interface.
 * 
	service interface (connection)
	service provider interface (Driver)
	provider registration api (DriverManager.registerDriver)
	service access api (DriverManager.getConnection)

 * There are numerous variants of the service provider framework pattern. For
 * example, the service access API can return a richer service interface than
 * the one required of the provider, using the Adapter pattern [Gamma95, p.
 * 139]. Here is a simple implementation with a service provider interface and a
 * default provider:
 * 
 * @author santosh
 * 
 */

// Service provider framework sketch - Service provider interface - Page 12
//service provider interface
interface Provider {
	Service newService();
}
//service interface
interface Service {
	// Service-specific methods go here
}

class Services {
	private Services() {
	} // Prevents instantiation (Item 4)

	// Maps service names to services
	private static final Map<String, Provider> providers = new ConcurrentHashMap<String, Provider>();
	public static final String DEFAULT_PROVIDER_NAME = "<def>";

	// Provider registration API
	public static void registerDefaultProvider(Provider p) {
		registerProvider(DEFAULT_PROVIDER_NAME, p);
	}

	public static void registerProvider(String name, Provider p) {
		providers.put(name, p);
	}

	// Service access API
	public static Service newInstance() {
		return newInstance(DEFAULT_PROVIDER_NAME);
	}

	public static Service newInstance(String name) {
		Provider p = providers.get(name);
		if (p == null)
			throw new IllegalArgumentException(
					"No provider registered with name: " + name);
		return p.newService();
	}
}

public class ServiceProvider {
	public static void main(String[] args) {
		// Providers would execute these lines
		Services.registerDefaultProvider(DEFAULT_PROVIDER);
		Services.registerProvider("comp", COMP_PROVIDER);
		Services.registerProvider("armed", ARMED_PROVIDER);

		// Clients would execute these lines
		Service s1 = Services.newInstance();
		Service s2 = Services.newInstance("comp");
		Service s3 = Services.newInstance("armed");
		System.out.printf("%s, %s, %s%n", s1, s2, s3);
	}

	private static Provider DEFAULT_PROVIDER = new Provider() {
		public Service newService() {
			return new Service() {
				@Override
				public String toString() {
					return "Default service";
				}
			};
		}
	};

	private static Provider COMP_PROVIDER = new Provider() {
		public Service newService() {
			return new Service() {
				@Override
				public String toString() {
					return "Complementary service";
				}
			};
		}
	};

	private static Provider ARMED_PROVIDER = new Provider() {
		public Service newService() {
			return new Service() {
				@Override
				public String toString() {
					return "Armed service";
				}
			};
		}
	};
}
