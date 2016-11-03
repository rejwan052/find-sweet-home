package com.find;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.find.util.APIUtils;
import com.find.xml.Response;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HomeApplicationTests {

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

		/*
		 * System.out.println(response.getBody().getNumOfRows()); System.out.println(response.getBody().getPageNo());
		 * System.out.println(response.getBody().getTotalCount());
		 */

		// for(int i = 0 ; i < response.getBody().getItems().size(); i++){
		// System.out.println(response.getBody().getItems().get(i).getApartment());
		// System.out.println(response.getBody().getItems().get(i).getPlace());
		// System.out.println(response.getBody().getItems().get(i).getMoney());
		// System.out.println(response.getBody().getItems().get(i).getPlaceCode());
		// }
	}

}
