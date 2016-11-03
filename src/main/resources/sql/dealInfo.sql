CREATE TABLE `deal_info` (
  `id` varchar(14) NOT NULL,
  `money` varchar(50) NOT NULL,
  `year` varchar(4) NOT NULL,
  `month` varchar(2) NOT NULL,
  `day` varchar(5) NOT NULL,
  `apartment` varchar(20) NOT NULL,
  `space_size` varchar(10) NOT NULL,
  `place` varchar(20) NOT NULL,
  `place_num` varchar(6) NOT NULL,
  `place_code` varchar(6) NOT NULL,
  `create_date_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `deal_info_idx01` (`space_size`,`place_num`,`place_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;