package com.find.batch;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.java.Log;

@Configuration
@EnableBatchProcessing
@Import({ BatchJobScheduler.class })
@Log
public class BatchJobConfig {

	private static final String JOB_API_UPDATE = "api_update";

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private Step stepName;

	@Autowired
	private SimpleJobLauncher apiJobLauncher;

	@Autowired
	BatchCompletionListener jobCompletionNotiListener;

	@Bean
	public Job job( final BatchCompletionListener listener ){
		return jobBuilderFactory.get(JOB_API_UPDATE).start(stepName).listener(listener).build();
	}

	@Scheduled(cron = " * 0/1 * * * ?")
	public void perform() throws Exception{

		log.info("Job Started at :" + new Date());

		JobParameters param = new JobParametersBuilder().addString(JOB_API_UPDATE, String.valueOf(System.currentTimeMillis())).toJobParameters();

		JobExecution execution = apiJobLauncher.run(job(jobCompletionNotiListener), param);

		log.info("Job finished with status :" + execution.getStatus());
	}

}
