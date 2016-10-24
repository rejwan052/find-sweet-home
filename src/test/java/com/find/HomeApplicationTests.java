package com.find;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.find.xml.Response;
@RunWith(SpringRunner.class)
@SpringBootTest
public class HomeApplicationTests {

	@Test
	public void contextLoads() throws Exception{
		StringBuilder urlBuilder = new StringBuilder("http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTrade"); /*URL*/
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=서비스키"); /*Service Key*/
		urlBuilder.append("&" + URLEncoder.encode("LAWD_CD","UTF-8") + "=" + URLEncoder.encode("11110", "UTF-8")); /*지역코드*/
		urlBuilder.append("&" + URLEncoder.encode("DEAL_YMD","UTF-8") + "=" + URLEncoder.encode("201512", "UTF-8")); /*계약월*/
		urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("999", "UTF-8")); /*검색건수*/
		urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
		URL url = new URL(urlBuilder.toString());
		System.out.println(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
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
		Response response = (Response)jaxbUnmarshaller.unmarshal(new StringReader(sb.toString()));

		System.out.println(response.getBody().getNumOfRows());
		System.out.println(response.getBody().getPageNo());
		System.out.println(response.getBody().getTotalCount());

//		for(int i = 0 ; i < response.getBody().getItems().size(); i++){
//			System.out.println(response.getBody().getItems().get(i).getApartment());
//			System.out.println(response.getBody().getItems().get(i).getPlace());
//			System.out.println(response.getBody().getItems().get(i).getMoney());
//			System.out.println(response.getBody().getItems().get(i).getPlaceCode());
//		}
	}

}
