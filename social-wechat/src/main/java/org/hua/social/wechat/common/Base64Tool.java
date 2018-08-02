package org.hua.social.wechat.common;

import java.net.URLDecoder;
import java.util.Base64;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Base64Tool {
	
	private final static Log log = LogFactory.getLog(Base64Tool.class);

	public static String encodeImage(byte[] imageData) {
		String encodeStr = Base64.getEncoder().encodeToString(imageData);
		log.info("encodeImage>>" + encodeStr);
		return encodeStr;
	}
	
	public static byte[] decodeImage(String base64file) {
		byte[] decodeStr = Base64.getDecoder().decode(base64file);
		log.info("decodeImage>>" + decodeStr);
		return decodeStr;
	}
	
	public static byte[] decodeUrlImage(String base64file) {
		byte[] decodeStr = null;
		try {
			base64file = URLDecoder.decode(base64file, "UTF-8");
			decodeStr = Base64.getDecoder().decode(base64file);
			log.info("decodeImage>>" + decodeStr);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return decodeStr;
	}
	
}

