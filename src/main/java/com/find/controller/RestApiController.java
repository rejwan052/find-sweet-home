package com.find.controller;


import com.find.vo.Place;
import com.find.xml.Response;
import lombok.extern.java.Log;

import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by buri on 2016. 10. 23..
 */
@RestController
@Log
public class RestApiController {

    private static final String DEFAULT_PAGE_NO = "1";
    private static final String DEFAULT_SEARCH_ROWS = "999";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String BASE_URL = "";

    private static final String SERVICE_KEY = "";

    @RequestMapping(value = "/select",method = RequestMethod.GET)
    public List<Response.Item> select(@RequestParam String dealMonth, @RequestParam String placeCode) {

        StringBuilder urlBuilder = new StringBuilder(BASE_URL); /*URL*/
        try {
            urlBuilder.append("?" + URLEncoder.encode("ServiceKey", DEFAULT_ENCODING) + "="+SERVICE_KEY); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("LAWD_CD",DEFAULT_ENCODING) + "=" + URLEncoder.encode(placeCode, DEFAULT_ENCODING)); /*지역코드*/
            urlBuilder.append("&" + URLEncoder.encode("DEAL_YMD",DEFAULT_ENCODING) + "=" + URLEncoder.encode(dealMonth, DEFAULT_ENCODING)); /*계약월*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows",DEFAULT_ENCODING) + "=" + URLEncoder.encode(DEFAULT_SEARCH_ROWS, DEFAULT_ENCODING)); /*검색건수*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo",DEFAULT_ENCODING) + "=" + URLEncoder.encode(DEFAULT_PAGE_NO, DEFAULT_ENCODING)); /*페이지 번호*/
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        URL url = null;
        String result = null;

        try {
            url = new URL(urlBuilder.toString());

            log.info("url : " + urlBuilder.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            log.info("response code : "+conn.getResponseCode());

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
            result = sb.toString();
            rd.close();
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Response response = null;
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(Response.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            response = (Response)jaxbUnmarshaller.unmarshal(new StringReader(result));
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        log.info("result : " + result);
        log.info("response : " + response);

        return response.getBody().getItems();
    }

    @RequestMapping(name = "/places",method = RequestMethod.GET)
    public List<Place> getPlaces() {
        //TODO DB에 저장된 정보를 장소 정보를 가져온다
        return new ArrayList<Place>();
    }
}
