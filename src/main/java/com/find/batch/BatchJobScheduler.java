package com.find.batch;

import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class BatchJobScheduler {

	@Bean
	public ResourcelessTransactionManager txManager(){
		return new ResourcelessTransactionManager();
	}

	@Bean
	public MapJobRepositoryFactoryBean mapJobRepositoryFactory( final ResourcelessTransactionManager txManager ) throws Exception{
		final MapJobRepositoryFactoryBean factory = new MapJobRepositoryFactoryBean(txManager);
		factory.afterPropertiesSet();
		return factory;
	}

	@Bean
	public JobRepository jobRepository( final MapJobRepositoryFactoryBean factory ) throws Exception{
		return factory.getObject();
	}

	@Bean
	public SimpleJobLauncher jobLauncher2( final JobRepository jobRepository ){
		final SimpleJobLauncher launcher = new SimpleJobLauncher();
		launcher.setJobRepository(jobRepository);
		return launcher;
	}
}
