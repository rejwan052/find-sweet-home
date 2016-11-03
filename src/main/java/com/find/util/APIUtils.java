package com.find.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.find.xml.Response;

import lombok.extern.java.Log;

/**
 * API 요청 URL 생성을 위한 유틸
 */
@Log
public final class APIUtils {

	/**
	 * URL 인코딩 종류
	 */
	private static final String ENCODING = "UTF-8";
	/**
	 * 조회 페이지
	 */
	private static final String DEFAULT_PAGE_NO = "1";
	/**
	 * 요청 데이터 수
	 */
	private static final String SEARCH_ROWS = "1";
	/**
	 * 공공데이터 API 요청 URL
	 */
	private static final String BASE_URL = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTrade";
	/**
	 * 공공데이터 요청시 받은 서버키
	 */
	private static final String SERVICE_KEY = "";

	private APIUtils() {

	}

	/**
	 * 데이터 요청을 위한 URL 생성
	 * 
	 * @param dealMonth
	 * @param placeCode
	 * @return
	 */
	public static String buildUrl( final String dealMonth, final String placeCode ){

		final StringBuilder urlBuilder = new StringBuilder(BASE_URL); /* URL */
		try {
			urlBuilder.append("?" + URLEncoder.encode("ServiceKey", ENCODING) + "=" + SERVICE_KEY); /* Service Key */
			urlBuilder.append("&" + URLEncoder.encode("LAWD_CD", ENCODING) + "=" + URLEncoder.encode(placeCode, ENCODING)); /* 지역코드 */
			urlBuilder.append("&" + URLEncoder.encode("DEAL_YMD", ENCODING) + "=" + URLEncoder.encode(dealMonth, ENCODING)); /* 계약월 */
			urlBuilder.append("&" + URLEncoder.encode("numOfRows", ENCODING) + "=" + URLEncoder.encode(SEARCH_ROWS, ENCODING)); /* 검색건수 */
			urlBuilder.append("&" + URLEncoder.encode("pageNo", ENCODING) + "=" + URLEncoder.encode(DEFAULT_PAGE_NO, ENCODING)); /* 페이지 번호 */
		}
		catch (final UnsupportedEncodingException e) {
			log.info("building url was faild.");
		}
		return urlBuilder.toString();
	}

	public static String getQueryResult( String urlStr ){
		URL url = null;
		String result = null;

		try {
			url = new URL(urlStr);

			log.info("url : " + urlStr);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");

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
		return result;
	}

	public static Response xmlToResponse( String result ){
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
		return response;
	}
}
