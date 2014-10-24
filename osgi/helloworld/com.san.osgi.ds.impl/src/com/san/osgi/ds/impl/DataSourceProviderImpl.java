package com.san.osgi.ds.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.service.jdbc.DataSourceFactory;

import static org.osgi.service.jdbc.DataSourceFactory.*;
import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;

import com.san.osgi.ds.api.DataSourceProvider;
/**
 * Service Properties
The H2 Driver Adapter registers its DataSourceFactory with the following service properties:
osgi.jdbc.driver.class = org.h2.Driver
osgi.jdbc.driver.name = h2
ref - https://ops4j1.jira.com/wiki/display/PAXJDBC/H2+Driver+Adapter
 * @author Santoshkumar
 *
 */
@Component
public class DataSourceProviderImpl implements DataSourceProvider {
	Map<String, DataSource> dsMap;
	DataSourceFactory dataSourceFactory;
	
	@Activate
	public void activate(BundleContext context, Map<String, String> config) throws SQLException{
		Properties prop = new Properties();
		prop.put(JDBC_URL, "jdbc:h2:mem:testdb");
		prop.put(JDBC_USER, "sa");
		prop.put(JDBC_PASSWORD,"");
		System.out.println("Creating datasource");
		DataSource ds = dataSourceFactory.createDataSource(prop);
		testds(ds);
		dsMap.put("testds", ds);
		Properties serviceProperties = new Properties();
		serviceProperties.setProperty("dsFilter", "testds");
		serviceProperties.setProperty("osgi.jndi.service.name", "testds");
		context.registerService("javax.sql.DataSource", ds, serviceProperties);
	}

	private void testds(DataSource ds) throws SQLException {
		System.out.println("Test datasource -" + ds);
		Connection conn = ds.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT 1");
		System.out.println(rs.getInt(1));
		if(conn!=null){rs.close();stmt.close();conn.close();}
	}

	@Override
	public DataSource getDataSource(String dsName) {
		return dsMap.get("testds");
	}
	
	@Reference(target="(osgi.jdbc.driver.name=h2)")
	public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
		this.dataSourceFactory = dataSourceFactory;
	}

}
