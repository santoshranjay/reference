package com.san.osgi.ds.h2;

import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.osgi.service.jdbc.DataSourceFactory;

import aQute.bnd.annotation.component.Component;

/**
 * TODO This impl is not required since the h2 driver is already implementing
 * osgi DataSourceFactory &  if it's registering the same as a service
 * 
 * @author Santoshkumar
 *
 */
@Component(properties="osgi.jdbc.driver.name=h2")
public class H2DataSourceFactory implements DataSourceFactory{

	@Override
	public DataSource createDataSource(Properties props) throws SQLException {
//		OsgiDataSourceFactory dataSourceFactory = new OsgiDataSourceFactory(org.h2.Driver.load());
		JdbcDataSource datasource = new JdbcDataSource();
		datasource.setURL(props.getProperty(JDBC_URL));
		datasource.setUser(props.getProperty(JDBC_USER));
		datasource.setPassword(props.getProperty(JDBC_PASSWORD));
		return datasource;
	}

	@Override
	public ConnectionPoolDataSource createConnectionPoolDataSource(
			Properties props) throws SQLException {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public XADataSource createXADataSource(Properties props)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Driver createDriver(Properties props) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
