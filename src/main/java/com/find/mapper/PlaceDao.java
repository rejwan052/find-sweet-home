package com.find.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.find.vo.Place;

@Mapper
public interface PlaceDao {

	@Select("SELECT NOW()")
	String getCurrentDateTime();

	@Select("SELECT code, province, city from place")
	List<Place> getPlaces();
}
