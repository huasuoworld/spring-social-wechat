package org.hua.social.wechat.api;

import org.springframework.social.ApiBinding;

public interface Wechat extends ApiBinding {

	/**
	 * API for performing operations on Facebook user profiles.
	 * @return {@link UserOperations}
	 */
	UserOperations userOperations();
}
