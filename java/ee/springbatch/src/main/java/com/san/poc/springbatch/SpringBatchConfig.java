package com.san.poc.springbatch;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig extends DefaultBatchConfigurer{
	@Override
	protected JobRepository createJobRepository() throws Exception {
		return new MapJobRepositoryFactoryBean().getJobRepository();
	}
	
	@Override
	public void setDataSource(DataSource dataSource) {
		if(dataSource!=null)
			super.setDataSource(dataSource);
	}
}
