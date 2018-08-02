package org.hua.social.wechat.common;

import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Constant {

	public static final ObjectMapper objectMapper = new ObjectMapper();
	public static final String apiVersion = "2.5";
	
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
