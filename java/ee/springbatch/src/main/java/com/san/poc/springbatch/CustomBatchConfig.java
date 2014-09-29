package com.san.poc.springbatch;

import javax.sql.DataSource;

import org.hsqldb.jdbcDriver;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
//@ContextConfiguration(classes = CustomBatchConfig.class)
public class CustomBatchConfig {

	private JobLauncher jobLauncher;
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public DataSource dataSource(){
		return new SimpleDriverDataSource(new jdbcDriver(), "jdbc:hsqldb:test:memdb", "sa", "");
	}
	
	@Bean
	public JobRepository jobRepository() throws Exception{
		JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
		factory.setDatabaseType("hsqldb");
		factory.setDataSource(dataSource());
		factory.setTransactionManager(transactionManager());
		return factory.getJobRepository();
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(){
		return new ResourcelessTransactionManager();
	}
	
	@Bean
	public TaskExecutor taskExecutor(){
		return new SimpleAsyncTaskExecutor();
	}
	
	
}
