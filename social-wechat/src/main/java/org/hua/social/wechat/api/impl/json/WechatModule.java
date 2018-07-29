package org.hua.social.wechat.api.impl.json;

import org.hua.social.wechat.entry.User;

import com.fasterxml.jackson.databind.module.SimpleModule;

public class WechatModule extends SimpleModule {

	/**
	 * 
	 */
	private static final long serialVersionUID = -324545274198831979L;

	public WechatModule() {
		super("WechatModule");
	}
	
	@Override
	public void setupModule(SetupContext context) {
		
		context.setMixInAnnotations(User.class, User.class);
	}
}
