package com.find.xml;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by buri on 2016. 10. 23..
 */
@Data
@XmlRootElement(name="response")
public class Response {

    private Header header;
    private Body body;

    @XmlRootElement(name="header")
    public static class Header{
        private String resultCode;
        private String resultMsg;
    }

    @XmlRootElement(name = "body")
    public static class Body{
        @Setter
        private List<Item> items;

        @Getter @Setter
        private String numOfRows;

        @Getter @Setter
        private String pageNo;
        @Getter @Setter
        private String totalCount;

        @XmlElementWrapper(name="items")
        @XmlElement(name="item")
        public List<Item> getItems() {
            return items;
        }

    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Item{
        @XmlElement(name="거래금액")
        private String money;
        @XmlElement(name="년")
        private  String year;
        @XmlElement(name="월")
        private String month;
        @XmlElement(name="일")
        private  String day;
        @XmlElement(name="법정동")
        private  String place;
        @XmlElement(name="아파트")
        private  String apartment;
        @XmlElement(name="전용면적")
        private String spaceSize;
        @XmlElement(name="지번")
        private String postCode;
        @XmlElement(name="지역코드")
        private String placeCode;

    }
}
