package com.san.poc.springbatch;

import javax.sql.DataSource;

import org.hsqldb.jdbcDriver;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@ContextConfiguration(classes = TestBatchConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestBatchConfig {

	JobLauncher jobLauncher;
	@Autowired
	JobBuilderFactory jobBuilderFactory;
	@Autowired
	StepBuilderFactory stepBuilderFactory;

	@Before
	public void setUp() throws Exception {
		jobLauncher = new SimpleJobLauncher();
		((SimpleJobLauncher) jobLauncher).setJobRepository(jobRepository());
		((SimpleJobLauncher) jobLauncher).setTaskExecutor(taskExecutor());
		new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.HSQL)
				.addScript(
						"classpath:/org/springframework/batch/core/schema-hsqldb.sql")
				.build();
	}

	@Bean
	public DataSource dataSource() {
		return new SimpleDriverDataSource(new jdbcDriver(),
				"jdbc:hsqldb:mem:testdb", "sa", "");
	}

	@Bean
	public JobRepository jobRepository() throws Exception {
		JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
		factory.setDatabaseType("hsql");
		factory.setDataSource(dataSource());
		factory.setTransactionManager(transactionManager());
		return factory.getJobRepository();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new ResourcelessTransactionManager();
	}

	@Bean
	public TaskExecutor taskExecutor() {
		return new SyncTaskExecutor();
	}

}
