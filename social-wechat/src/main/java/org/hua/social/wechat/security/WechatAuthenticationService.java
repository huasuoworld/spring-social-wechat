package org.hua.social.wechat.security;

import org.hua.social.wechat.api.Wechat;
import org.hua.social.wechat.connect.WechatConnectionFactory;
import org.springframework.social.security.provider.OAuth2AuthenticationService;

public class WechatAuthenticationService extends OAuth2AuthenticationService<Wechat> {

	public WechatAuthenticationService(String apiKey, String appSecret) {
		super(new WechatConnectionFactory(apiKey, appSecret));
	}

	public WechatAuthenticationService(String apiKey, String appSecret, String appNamespace) {
		super(new WechatConnectionFactory(apiKey, appSecret, appNamespace));
	}

}
