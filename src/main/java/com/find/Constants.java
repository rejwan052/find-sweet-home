package com.find;

import lombok.extern.java.Log;

@Log
public class Constants {

	public static String getUniqueId(){
		try {
			Thread.sleep(100);
		}
		catch (InterruptedException e) {
			log.info("Generating unique id error");
		}
		return String.valueOf(System.currentTimeMillis());
	}
}
