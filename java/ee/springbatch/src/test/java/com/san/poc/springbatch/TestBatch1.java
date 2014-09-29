package com.san.poc.springbatch;

import java.io.File;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;

public class TestBatch1 extends TestBatchConfig {

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public final void test() throws JobExecutionAlreadyRunningException,
			JobRestartException, JobInstanceAlreadyCompleteException,
			JobParametersInvalidException, InterruptedException {
		jobLauncher.run(job(),
				new JobParametersBuilder().addDate("time", new Date())
						.toJobParameters());
	}

	@Bean
	private Job job() {
		return jobBuilderFactory.get("job").start(step()).build();
	}

	@Bean
	public Step step() {
		return stepBuilderFactory.get("step1").chunk(5).reader(reader())
				.writer(writer()).build();
	}

	private ItemWriter<? super Object> writer() {
		FlatFileItemWriter<Object> writer = new FlatFileItemWriter<Object>();
		writer.setResource(new FileSystemResource(new File(
				"C:\\temp\\testbatch1_out.txt")));
		writer.setLineAggregator(new PassThroughLineAggregator<Object>());
		return writer;
	}

	private ItemReader<? extends Object> reader() {
		FlatFileItemReader<String> reader = new FlatFileItemReader<String>();
		reader.setResource(new FileSystemResource(new File(
				"C:\\temp\\testbatch1_in.txt")));
		reader.setLineMapper(new PassThroughLineMapper());
		return reader;
	}
}
