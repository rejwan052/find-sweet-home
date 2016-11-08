package com.find;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.find.mapper.DealInfoDao;
import com.find.util.APIUtils;
import com.find.vo.Place;
import com.find.xml.Response;

import lombok.extern.java.Log;

@Log
@RunWith(SpringRunner.class)
@SpringBootTest
public class HomeApplicationTests {

	@Autowired
	DealInfoDao dealDao;

	@Test
	public void testSelectPlaceInfo() throws Exception{
		List<Place> places = dealDao.getPlaces();

		for (Place place : places) {
			log.info(place.getCode());
		}

	}

	@Test
	public void testBuildUrl() throws Exception{
		List<Place> places = dealDao.getPlaces();

		for (Place place : places) {

			String url = APIUtils.buildUrl("201601", place.getCode());
			log.info("Request Url : " + url);
		}

	}

	@Ignore
	@Test
	public void insertApiData() throws Exception{

		//dealDao.removeDealInfo();

		List<Place> places = dealDao.getPlaces();

		for (Place place : places) {

			String url = APIUtils.buildUrl("201610", place.getCode());

			String result = APIUtils.getQueryResult(url);
			Response response = APIUtils.xmlToResponse(result);
			List<Response.Item> items = response.getBody().getItems();

			for (Response.Item item : items) {
				if( item.getPlaceNum() == null ) {
					log.info(item.toString());
				}
				item.setId(Constants.getUniqueId());
			}

			if( items != null && !items.isEmpty() ) {
				dealDao.insertResponseItems(items);
			}
		}

	}

	@Ignore
	@Test
	public void contextLoads() throws Exception{

		String apiUrl = APIUtils.buildUrl("201512", "11110");

		URL url = new URL(apiUrl);
		System.out.println(apiUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if( conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300 ) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		}
		else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();

		JAXBContext jaxbContext = JAXBContext.newInstance(Response.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		System.out.println(sb.toString());
		Response response = (Response) jaxbUnmarshaller.unmarshal(new StringReader(sb.toString()));

	}

}
