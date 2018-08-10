package org.hua.social.wechat.connect;

import org.hua.social.wechat.api.Wechat;
import org.hua.social.wechat.api.impl.WechatOAuth2Template;
import org.hua.social.wechat.api.impl.WechatTemplate;
import org.hua.social.wechat.entry.AccessToken;
import org.hua.social.wechat.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.util.StringUtils;

public class WechatServiceProvider extends AbstractOAuth2ServiceProvider<Wechat> {
	
//	private static final String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	private static final String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";
//	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
	
	private String appNamespace;
	
	public WechatServiceProvider(String appId, String appSecret, String appNamespace) {
		super(getOAuth2Template(appId, appSecret));
		this.appNamespace = appNamespace;
	}
	
	private static WechatOAuth2Template getOAuth2Template(String appId, String appSecret) {
		String authorizeUrl = System.getProperty("authorize.url");
		String accessTokenUrl = System.getProperty("access.token.url");
		WechatOAuth2Template oAuth2Template = null;
		if(StringUtils.isEmpty(authorizeUrl) || StringUtils.isEmpty(accessTokenUrl)) {
			oAuth2Template = new WechatOAuth2Template(appId, appSecret, AUTHORIZE_URL, ACCESS_TOKEN_URL);
		} else {
			oAuth2Template = new WechatOAuth2Template(appId, appSecret, authorizeUrl, accessTokenUrl);
		}
		oAuth2Template.setUseParametersForClientAuthentication(true);
		return oAuth2Template;
	}

	@Override
	public Wechat getApi(AccessToken accessToken) {
		return new WechatTemplate(accessToken, appNamespace);
	}

	public String getAppNamespace() {
		return appNamespace;
	}

	public void setAppNamespace(String appNamespace) {
		this.appNamespace = appNamespace;
	}
}
