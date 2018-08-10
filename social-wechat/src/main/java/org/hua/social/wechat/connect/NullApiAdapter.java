package org.hua.social.wechat.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

final class NullApiAdapter implements ApiAdapter<Object> {

	public static final NullApiAdapter INSTANCE = new NullApiAdapter();
	
	public boolean test(Object api) {
		return true;
	}

	public void setConnectionValues(Object api, ConnectionValues values) {
		
	}

	public UserProfile fetchUserProfile(Object api) {
		return UserProfile.EMPTY;
	}

	public void updateStatus(Object api, String message) {
	}

	// internal helpers
	
	private NullApiAdapter() {}
	
}
