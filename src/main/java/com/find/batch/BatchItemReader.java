package com.find.batch;

import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.find.Constants;
import com.find.util.APIUtils;
import com.find.xml.Response;

import lombok.extern.java.Log;

@Log
public class BatchItemReader implements ItemReader<List<Response.Item>> {

	private String url;

	public BatchItemReader(final String url) {
		this.url = url;
	}

	// TODO writer 로 써지지않는 문제 수정할 것
	@Override
	public List<Response.Item> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException{
		log.info("Reading api item start.");

		String result = APIUtils.getQueryResult(url);
		Response response = APIUtils.xmlToResponse(result);

		for (Response.Item item : response.getBody().getItems()) {
			log.info(item.toString());
			item.setId(Constants.getUniqueId());
		}

		log.info("response items size : " + response.getBody().getItems().size());

		return response.getBody().getItems();
	}

}
