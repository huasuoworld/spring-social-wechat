package org.hua.social.wechat.common;

import org.springframework.social.oauth2.AccessGrant;

public class WechatAccessGrant extends AccessGrant {

	private static final long serialVersionUID = 6318805867132706528L;
	
	private String openid;

	public WechatAccessGrant(String accessToken) {
		super(accessToken);
	}

	public WechatAccessGrant(String openid, String accessToken, String scope, String refreshToken, Long expiresIn) {
		super(accessToken, scope, refreshToken, expiresIn);
		this.openid = openid;
	}
	
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
}
