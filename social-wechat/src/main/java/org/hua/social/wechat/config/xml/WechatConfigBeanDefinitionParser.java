package org.hua.social.wechat.config.xml;

import java.util.Map;

import org.hua.social.wechat.config.support.WechatApiHelper;
import org.hua.social.wechat.connect.WechatConnectionFactory;
import org.hua.social.wechat.security.SocialAuthenticationService;
import org.hua.social.wechat.security.WechatAuthenticationService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

public class WechatConfigBeanDefinitionParser extends AbstractProviderConfigBeanDefinitionParser {

	public WechatConfigBeanDefinitionParser() {
		super(WechatConnectionFactory.class, WechatApiHelper.class);
	}
	
	@Override
	protected Class<? extends SocialAuthenticationService<?>> getAuthenticationServiceClass() {
		return WechatAuthenticationService.class;
	}
	
	@Override
	protected BeanDefinition getConnectionFactoryBeanDefinition(String appId, String appSecret, Map<String, Object> allAttributes) {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(WechatConnectionFactory.class).addConstructorArgValue(appId).addConstructorArgValue(appSecret);
		if (allAttributes.containsKey("app-namespace")) {
			builder.addConstructorArgValue(allAttributes.get("app-namespace"));
		}
		return builder.getBeanDefinition();
	}

}