package org.hua.social.wechat.api;

import org.springframework.social.ApiBinding;
import org.springframework.social.oauth2.OAuth2Operations;

public interface Wechat extends ApiBinding {

	/**
	 * API for performing operations on Facebook user profiles.
	 * @return {@link UserOperations}
	 */
	UserOperations userOperations();
}
