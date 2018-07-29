package org.hua.social.wechat.connect;

import org.hua.social.wechat.api.Wechat;
import org.hua.social.wechat.entry.User;
import org.springframework.social.ApiException;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;

public class WechatAdapter implements ApiAdapter<Wechat> {

	public boolean test(Wechat wechat) {
		try {
			wechat.userOperations().getUserProfile();
			return true;
		} catch (ApiException e) {
			return false;
		}
	}

	public void setConnectionValues(Wechat wechat, ConnectionValues values) {
	}

	public UserProfile fetchUserProfile(Wechat wechat) {
		User profile = wechat.userOperations().getUserProfile();
		return new UserProfileBuilder().setName(profile.getNickname()).setFirstName("").setLastName("").
				setEmail("").build();
	}

	public void updateStatus(Wechat wechat, String message) {
	}

}
