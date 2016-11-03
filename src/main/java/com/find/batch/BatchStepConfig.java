package com.find.batch;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.find.util.APIUtils;
import com.find.xml.Response;

@Configuration
public class BatchStepConfig {

	private static final String MAPPER_INSERT_REPONSE_ITEMS = "com.find.mapper.DealInfoDao.insertResponseItems";

	private static final String STEP_API_UPDATE = "api_update_step";

	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Step Step(){
		return stepBuilderFactory.get(STEP_API_UPDATE).<List<Response.Item>, List<Response.Item>> chunk(1).reader(apiItemReader()).writer(apiItemWriter())
				.build();
	}

	@Bean
	public ItemReader<List<Response.Item>> apiItemReader(){
		String urlStr = APIUtils.buildUrl("201510", "11110");
		return new BatchItemReader(urlStr);
	}


	@Bean
	public ItemWriter<List<Response.Item>> apiItemWriter(){
		MyBatisBatchItemWriter<List<Response.Item>> writer = new MyBatisBatchItemWriter<List<Response.Item>>();
		writer.setSqlSessionFactory(sqlSessionFactory);
		writer.setStatementId(MAPPER_INSERT_REPONSE_ITEMS);
		return writer;
	}
}
