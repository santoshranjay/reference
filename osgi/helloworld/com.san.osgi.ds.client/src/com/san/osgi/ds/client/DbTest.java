package com.san.osgi.ds.client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.san.osgi.ds.api.DataSourceProvider;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;

@Component
public class DbTest {
	private DataSourceProvider dataSourceProvider;
	
	@Reference
	public void setDataSourceProvider(DataSourceProvider dataSourceProvider) {
		this.dataSourceProvider = dataSourceProvider;
	}
	
	@Activate
	public void test() throws SQLException{
		DataSource ds = dataSourceProvider.getDataSource("testds");
		System.out.println("Test datasource -" + ds);
		Connection conn = ds.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT 1");
		System.out.println("results - " + rs.getString(0));
		if(conn!=null){rs.close();stmt.close();conn.close();}
	}

}
