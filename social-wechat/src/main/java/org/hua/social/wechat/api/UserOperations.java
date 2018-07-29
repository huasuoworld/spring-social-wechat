package org.hua.social.wechat.api;

import org.hua.social.wechat.entry.User;

public interface UserOperations {

	public User getUserProfile();
	public User getUserProfileByOpenid(String openid);
	public User getUserProfileByUnionid(String unionid);
}
