package org.hua.social.wechat.config.xml;

public class WechatNamespaceHandler extends AbstractProviderConfigNamespaceHandler {

	@Override
	protected AbstractProviderConfigBeanDefinitionParser getProviderConfigBeanDefinitionParser() {
		return new WechatConfigBeanDefinitionParser();
	}

}