package org.hua.social.wechat.api.impl;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hua.social.wechat.common.WechatErrors;
import org.hua.social.wechat.entry.WechatError;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.ServerException;
import org.springframework.social.UncategorizedApiException;
import org.springframework.web.client.DefaultResponseErrorHandler;

public class WechatErrorHandler extends DefaultResponseErrorHandler {
	
	private static final String WECHAT_PROVIDER_ID = "wechat";
	
	private final static Log log = LogFactory.getLog(WechatErrorHandler.class);
	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		WechatError error = WechatError.extractErrorFromResponse(response);
		handleWechatError(response.getStatusCode(), error);
	}
	
	void handleWechatError(HttpStatus statusCode, WechatError error) {
		if (error != null && error.getErrcode() != null) {
			int code = error.getErrcode();
			log.info("start handleWechatError");
			if (code == WechatErrors.BUSY_SYSTEM) {
				throw new UncategorizedApiException(WECHAT_PROVIDER_ID, error.getErrmsg(), null);
			} 
			else if (code == WechatErrors.ACCESS_TOKEN_ERROR) {
				throw new ServerException(WECHAT_PROVIDER_ID, error.getErrmsg());
			} 
			else if (code == WechatErrors.JSON_XML_ERROR) {
				throw new ServerException(WECHAT_PROVIDER_ID, error.getErrmsg());
			} 
			else if (code == WechatErrors.INTERFACE_NO_OAUTH_ERROR) {
				throw new ServerException(WECHAT_PROVIDER_ID, error.getErrmsg());
			} 
			else {
				throw new UncategorizedApiException(WECHAT_PROVIDER_ID, error.getErrmsg(), null);
			}
		}
	}
}
