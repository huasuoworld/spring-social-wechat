package org.hua.social.wechat.oauth2;

import java.io.Serializable;

/**
 * OAuth2 access token.
 * @author Keith Donald
 */
@SuppressWarnings("serial")
public class AccessGrant implements Serializable {

	private final String accessToken;

	private final String scope;

	private final String refreshToken;
	
	private final Long expireTime;

	public AccessGrant(String accessToken) {
		this(accessToken, null, null, null);
	}

	public AccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn) {
		this.accessToken = accessToken;
		this.scope = scope;
		this.refreshToken = refreshToken;
		this.expireTime = expiresIn != null ? System.currentTimeMillis() + expiresIn * 1000l : null;
	}

	/**
	 * The access token value.
	 * @return The access token value.
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * The scope of the access grant.
	 * May be null if the provider doesn't return the granted scope in the response.
	 * @return The scope of the access grant.
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * The refresh token that can be used to renew the access token.
	 * May be null if the provider does not support refresh tokens.
	 * @return The refresh token that can be used to renew the access token.
	 */
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * The time (in milliseconds since Jan 1, 1970 UTC) when this access grant will expire.
	 * May be null if the token is non-expiring.
	 * @return The time (in milliseconds since Jan 1, 1970 UTC) when this access grant will expire.
	 */
	public Long getExpireTime() {
		return expireTime;
	}
	
}

