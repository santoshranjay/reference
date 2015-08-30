package poc.kafka;

import org.apache.zookeeper.server.NIOServerCnxnFactory;
import org.apache.zookeeper.server.ServerCnxnFactory;
import org.apache.zookeeper.server.ZooKeeperServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Zookeeper {
    private int port = -1;
    private int tickTime = 500;

    private ServerCnxnFactory factory;
    private File snapshotDir;
    private File logDir;

    public Zookeeper() {
        this(-1);
    }

    public Zookeeper(int port) {
        this(port, 500);
    }

    public Zookeeper(int port, int tickTime) {
        this.port = resolvePort(port);
        this.tickTime = tickTime;
    }

    private int resolvePort(int port) {
        if (port == -1) {
            return Util.getAvailablePort();
        }
        return port;
    }

    public void startup() throws IOException{
        if (this.port == -1) {
            this.port = Util.getAvailablePort();
        }
        this.factory = NIOServerCnxnFactory.createFactory(new InetSocketAddress(System.getProperty("zk.host","localhost"), port), 1024);
        this.snapshotDir = Util.constructTempDir("embeeded-zk/snapshot");
        this.logDir = Util.constructTempDir("embeeded-zk/log");

        try {
            factory.startup(new ZooKeeperServer(snapshotDir, logDir, tickTime));
        } catch (InterruptedException e) {
            throw new IOException(e);
        }
    }


    public void shutdown() {
        factory.shutdown();
        try {
            Util.deleteFile(snapshotDir);
        } catch (FileNotFoundException e) {
        	e.getMessage();
        }
        try {
            Util.deleteFile(logDir);
        } catch (FileNotFoundException e) {
        	e.getMessage();
        }
    }

    public String getConnection() {
        return "localhost:" + port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setTickTime(int tickTime) {
        this.tickTime = tickTime;
    }

    public int getPort() {
        return port;
    }

    public int getTickTime() {
        return tickTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Zookeeper{");
        sb.append("connection=").append(getConnection());
        sb.append('}');
        return sb.toString();
    }

	public static String getZkConnectionString() {
		return System.getProperty("zk.host","localhost")+":"+Integer.getInteger("zk.port",8991);
	}
}
