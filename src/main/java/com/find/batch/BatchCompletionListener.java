package com.find.batch;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

import lombok.extern.java.Log;

@Log
@Component
public class BatchCompletionListener extends JobExecutionListenerSupport {

	@Override
	public void afterJob( final JobExecution jobExecution ){
		if( jobExecution.getStatus() == BatchStatus.COMPLETED ) {
			log.info("!!! JOB FINISHED! Time to verify the results");
		}
	}
}
