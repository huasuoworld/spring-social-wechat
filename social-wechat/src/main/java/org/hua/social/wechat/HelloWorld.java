package org.hua.social.wechat;

import org.hua.social.wechat.api.Wechat;
import org.hua.social.wechat.api.impl.WechatTemplate;
import org.hua.social.wechat.common.AccessTokenTool;
import org.hua.social.wechat.entry.AccessToken;
import org.hua.social.wechat.entry.User;

public class HelloWorld {
	
	public static void main(String[] args) {
		AccessToken accessToken = new AccessTokenTool();
    	Wechat wechat = new WechatTemplate(accessToken);
    	User user = wechat.userOperations().getUserProfileByOpenid("oj-qXw8jOt0CMT7M99Yf8V-bwWkU");
    	System.out.println(user.toString());
	}

	public String get() {
		return "hello world!";
	}
}
