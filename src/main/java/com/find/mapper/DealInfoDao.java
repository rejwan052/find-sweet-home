package com.find.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.find.vo.Place;
import com.find.xml.Response;

/**
 * 지역코드 관련 DAO
 * 
 * @author kyungseop
 *
 */
@Mapper
public interface DealInfoDao {

	/**
	 * 현재시간 확인
	 * 
	 * @return
	 */
	@Select("SELECT NOW()")
	String getCurrentDateTime();

	/**
	 * 지역별 코드 정보 조회
	 * 
	 * @return
	 */
	@Select("SELECT code, province, city from place")
	List<Place> getPlaces();

	/**
	 * batch 테스트
	 * 
	 * @param respList
	 */
	@Insert("<script> "
			+ "INSERT INTO deal_info "
			+		"(id, money, year, month, day, apartment, space_size, place, place_num, place_code, create_date_time) VALUES"
			+	"<foreach collection='list' item='item' separator=' , '> "
			+		"(#{item.id}, #{item.money}, #{item.year}, #{item.month}, #{item.day}, #{item.apartment}, #{item.spaceSize}, "
			+ 		" #{item.place}, #{item.placeNum}, #{item.placeCode}, now()) "
			+	"</foreach>"
			+ "ON DUPLICATE KEY UPDATE money=values(money), year=values(year), month=values(month), day=values(day), space_size=values(space_size), create_date_time=values(create_date_time)"
			+ "</script>")
	void insertResponseItems( List<Response.Item> items );
}
