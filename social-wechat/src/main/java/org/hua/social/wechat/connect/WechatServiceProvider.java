package org.hua.social.wechat.connect;

import org.hua.social.wechat.api.Wechat;
import org.hua.social.wechat.api.impl.WechatTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.StringUtils;

public class WechatServiceProvider extends AbstractOAuth2ServiceProvider<Wechat> {
	
	private static final String AUTHORIZE_URL = "";
	private static final String ACCESS_TOKEN_URL = "";
	
	private String appNamespace;
	
	public WechatServiceProvider(String appId, String appSecret, String appNamespace) {
		super(getOAuth2Template(appId, appSecret));
		this.appNamespace = appNamespace;
	}
	
	private static OAuth2Template getOAuth2Template(String appId, String appSecret) {
		String authorizeUrl = System.getProperty("authorize.url");
		String accessTokenUrl = System.getProperty("access.token.url");
		OAuth2Template oAuth2Template = null;
		if(StringUtils.isEmpty(authorizeUrl) || StringUtils.isEmpty(accessTokenUrl)) {
			oAuth2Template = new OAuth2Template(appId, appSecret, AUTHORIZE_URL, ACCESS_TOKEN_URL);
		} else {
			oAuth2Template = new OAuth2Template(appId, appSecret, authorizeUrl, accessTokenUrl);
		}
		oAuth2Template.setUseParametersForClientAuthentication(true);
		return oAuth2Template;
	}

	@Override
	public Wechat getApi(String accessToken) {
		return new WechatTemplate(accessToken, appNamespace);
	}

	public String getAppNamespace() {
		return appNamespace;
	}

	public void setAppNamespace(String appNamespace) {
		this.appNamespace = appNamespace;
	}
}
