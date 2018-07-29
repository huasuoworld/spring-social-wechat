package org.hua.social.wechat;

import org.hua.social.wechat.api.Wechat;
import org.hua.social.wechat.api.impl.WechatTemplate;
import org.hua.social.wechat.entry.User;

public class HelloWorld {
	
	public static void main(String[] args) {
		String accessToken = "12_BKB15WpMal4dQPKjW6UpqGdkCKsNNDW6ExAyhwS1cZyV1n7XVcn7tvRpiNxNHwy6uv-hEh1PaZUVEslJbVD80gIPL-Fh60vnc7EI8LJ5_DwsaI3PXlPj9HCRdHNSgAYOgik4yuUpPno4J2PYUJFgAFACRN"; 
    	Wechat wechat = new WechatTemplate(accessToken);
    	User user = wechat.userOperations().getUserProfileByOpenid("oj-qXw8jOt0CMT7M99Yf8V-bwWkU");
    	System.out.println(user.toString());
	}

	public String get() {
		return "hello world!";
	}
}
