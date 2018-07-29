package org.hua.social.wechat.entry;

import org.springframework.social.UserIdSource;

public class WechatUserIdSource implements UserIdSource {

	private String userid;
	public WechatUserIdSource(String userid) {
		this.userid = userid;
	}
	public String getUserId() {
		return userid;
	}
}
