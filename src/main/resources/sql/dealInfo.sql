CREATE TABLE `deal_info` (
  `id` varchar(14) NOT NULL,
  `money` varchar(50) NOT NULL COMMENT '거래금액 ',
  `year` varchar(4) NOT NULL,
  `month` varchar(2) NOT NULL,
  `day` varchar(5) NOT NULL,
  `apartment` varchar(255) NOT NULL COMMENT '아파트 ',
  `space_size` varchar(10) NOT NULL COMMENT '전용면적 ',
  `place` varchar(20) NOT NULL COMMENT '법정동 ',
  `place_num` varchar(10) DEFAULT NULL COMMENT '지번 ',
  `place_code` varchar(6) NOT NULL COMMENT '지역코드 ',
  `create_date_time` datetime NOT NULL COMMENT '데이터 생성일 ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

