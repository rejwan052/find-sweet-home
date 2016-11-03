package com.find.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by buri on 2016. 10. 23..
 */
@Data
@XmlRootElement(name = "response")
public class Response {

	private Header header;
	private Body body;

	@Data
	@XmlRootElement(name = "header")
	public static class Header {
		private String resultCode;
		private String resultMsg;
	}

	@XmlRootElement(name = "body")
	public static class Body {
		@Setter
		private List<Item> items;

		@Getter
		@Setter
		private String numOfRows;

		@Getter
		@Setter
		private String pageNo;
		@Getter
		@Setter
		private String totalCount;

		@XmlElementWrapper(name = "items")
		@XmlElement(name = "item")
		public List<Item> getItems(){
			return items;
		}

	}

	@Data
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Item {

		private String id;
		@XmlElement(name = "거래금액")
		private String money;
		@XmlElement(name = "년")
		private String year;
		@XmlElement(name = "월")
		private String month;
		@XmlElement(name = "일")
		private String day;
		@XmlElement(name = "아파트")
		private String apartment;
		@XmlElement(name = "전용면적")
		private String spaceSize;
		@XmlElement(name = "법정동")
		private String place;
		@XmlElement(name = "지번")
		private String placeNum;
		@XmlElement(name = "지역코드")
		private String placeCode;

	}
}
