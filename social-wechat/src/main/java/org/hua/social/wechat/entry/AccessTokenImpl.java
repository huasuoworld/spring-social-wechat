package org.hua.social.wechat.entry;

public class AccessTokenImpl implements AccessToken {
	
	private String accessToken;

	public String get() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
