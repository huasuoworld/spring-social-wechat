package org.hua.social.wechat.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HttpTool {

	public static HttpHeaders applicationJson() {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		return requestHeaders;
	}
	
	public static HttpHeaders multipartFormData() {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
		return requestHeaders;
	}
}
