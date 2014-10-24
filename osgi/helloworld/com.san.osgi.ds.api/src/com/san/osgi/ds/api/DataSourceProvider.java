package com.san.osgi.ds.api;

import javax.sql.DataSource;

import aQute.bnd.annotation.ProviderType;

@ProviderType
public interface DataSourceProvider {

	DataSource getDataSource(String dsName);
}
