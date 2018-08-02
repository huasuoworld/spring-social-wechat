package org.hua.social.wechat.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hua.social.wechat.api.UserOperations;
import org.hua.social.wechat.entry.User;
import org.springframework.web.client.RestTemplate;

/**
 * Retrieving user profile restTemplate
 * @author huacailiang
 *
 */
public class UserTemplate implements UserOperations {
	
	private final static Log log = LogFactory.getLog(UserTemplate.class);
	
	private final RestTemplate restTemplate;
	public final static String USER_INFO_BY_OPENID_URL = "https://api.weixin.qq.com/cgi-bin/user/info?openid=%s&lang=zh_CN";
	public final static String USER_INFO_BY_UNIONID_URL = "https://api.weixin.qq.com/cgi-bin/user/info?unionid=%s&lang=zh_CN";
	
	public UserTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public User getUserProfileByOpenid(String openid) {
		String url = String.format(USER_INFO_BY_OPENID_URL, openid);
		String src = restTemplate.getForObject(url, String.class);
		log.debug(src);
		User user = User.fromResponse(src);
		return user;
	}

	public User getUserProfileByUnionid(String unionid) {
		String url = String.format(USER_INFO_BY_UNIONID_URL, unionid);
		String src = restTemplate.getForObject(url, String.class);
		log.debug(src);
		User user = User.fromResponse(src);
		return user;
	}

	public User getUserProfile() {
		String url = String.format(USER_INFO_BY_OPENID_URL, "");
		String src = restTemplate.getForObject(url, String.class);
		log.debug(src);
		User user = User.fromResponse(src);
		return user;
	}

}
