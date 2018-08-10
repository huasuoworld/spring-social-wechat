package org.hua.social.wechat.connect;

import org.hua.social.wechat.api.Wechat;

public class WechatConnectionFactory extends OAuth2ConnectionFactory<Wechat> {

	/**
	 * Creates a WechatConnectionFactory for the given application ID and secret.
	 * Using this constructor, no application namespace is set .
	 * @param appId The application's App ID as assigned by Wechat 
	 * @param appSecret The application's App Secret as assigned by Wechat
	 */
	public WechatConnectionFactory(String appId, String appSecret) {
		this(appId, appSecret, null);
	}

	/**
	 * Creates a WechatConnectionFactory for the given application ID, secret, and namespace.
	 * @param appId The application's App ID as assigned by Wechat 
	 * @param appSecret The application's App Secret as assigned by Wechat
	 * @param appNamespace The application's App Namespace as configured with Wechat.
	 */
	public WechatConnectionFactory(String appId, String appSecret, String appNamespace) {
		super("wechat", new WechatServiceProvider(appId, appSecret, appNamespace), new WechatAdapter());
	}

}
