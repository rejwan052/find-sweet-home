package com.find.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Utils {

	private static final String ENCODING = "UTF-8";
	private static final String DEFAULT_PAGE_NO = "1";
	private static final String DEFAULT_SEARCH_ROWS = "999";
	private static final String BASE_URL = "";
	private static final String SERVICE_KEY = "";

	public static String buildUrl( String dealMonth, String placeCode ){

		StringBuilder urlBuilder = new StringBuilder(BASE_URL); /* URL */
		try {
			urlBuilder.append("?" + URLEncoder.encode("ServiceKey", ENCODING) + "=" + SERVICE_KEY); /* Service Key */
			urlBuilder.append("&" + URLEncoder.encode("LAWD_CD", ENCODING) + "=" + URLEncoder.encode(placeCode, ENCODING)); /* 지역코드 */
			urlBuilder.append("&" + URLEncoder.encode("DEAL_YMD", ENCODING) + "=" + URLEncoder.encode(dealMonth, ENCODING)); /* 계약월 */
			urlBuilder.append("&" + URLEncoder.encode("numOfRows", ENCODING) + "=" + URLEncoder.encode(DEFAULT_SEARCH_ROWS, ENCODING)); /* 검색건수 */
			urlBuilder.append("&" + URLEncoder.encode("pageNo", ENCODING) + "=" + URLEncoder.encode(DEFAULT_PAGE_NO, ENCODING)); /* 페이지 번호 */
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return urlBuilder.toString();
	}
}
