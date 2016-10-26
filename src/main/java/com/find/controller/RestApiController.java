package com.find.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.find.mapper.PlaceDao;
import com.find.util.Utils;
import com.find.vo.Place;
import com.find.xml.Response;

import lombok.extern.java.Log;

/**
 * Created by buri on 2016. 10. 23..
 */
@RestController
@Log
public class RestApiController {

	@Autowired
	PlaceDao placeDao;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public List<Response.Item> select( @RequestParam String dealMonth, @RequestParam String placeCode ){

		String urlStr = Utils.buildUrl(dealMonth, placeCode);

		URL url = null;
		String result = null;

		try {
			url = new URL(urlStr);

			log.info("url : " + urlStr);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");

			log.info("response code : " + conn.getResponseCode());

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
			result = sb.toString();
			rd.close();
			conn.disconnect();
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (ProtocolException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		Response response = null;
		JAXBContext jaxbContext = null;
		try {
			jaxbContext = JAXBContext.newInstance(Response.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			response = (Response) jaxbUnmarshaller.unmarshal(new StringReader(result));
		}
		catch (JAXBException e) {
			e.printStackTrace();
		}

		log.info("result : " + result);
		log.info("response : " + response);

		return response.getBody().getItems();
	}

	@RequestMapping(name = "/places", method = RequestMethod.GET)
	public List<Place> getPlaces(){
		// TODO DB에 저장된 정보를 장소 정보를 가져온다
		return placeDao.getPlaces();
	}
}
