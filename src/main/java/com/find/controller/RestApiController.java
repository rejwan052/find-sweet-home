package com.find.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.find.mapper.DealInfoDao;
import com.find.util.APIUtils;
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
	DealInfoDao placeDao;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public List<Response.Item> select( @RequestParam final String dealMonth, @RequestParam final String placeCode ){

		String urlStr = APIUtils.buildUrl(dealMonth, placeCode);
		String result = APIUtils.getQueryResult(urlStr);

		Response response = APIUtils.xmlToResponse(result);

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
