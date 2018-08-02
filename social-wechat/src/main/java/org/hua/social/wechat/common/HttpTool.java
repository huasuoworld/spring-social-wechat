package org.hua.social.wechat.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HttpTool {

	public static HttpHeaders requestHeaders() {
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		return requestHeaders;
	}
}
