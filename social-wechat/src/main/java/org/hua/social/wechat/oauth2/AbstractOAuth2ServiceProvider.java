package org.hua.social.wechat.oauth2;

import org.hua.social.wechat.entry.AccessToken;
import org.springframework.social.oauth2.OAuth2Operations;

/**
 * Base class for ServiceProviders that use the OAuth2 protocol.
 * OAuth2-based ServiceProvider implementations should extend and implement {@link #getApi(String)}.
 * @author Keith Donald
 * @param <S> the service API type
 */
public abstract class AbstractOAuth2ServiceProvider<S> implements OAuth2ServiceProvider<S> {

	private final OAuth2Operations oauth2Operations;
	
	/**
	 * Create a new {@link OAuth2ServiceProvider}.
	 * @param oauth2Operations the OAuth2Operations template for conducting the OAuth 2 flow with the provider.
	 */
	public AbstractOAuth2ServiceProvider(OAuth2Operations oauth2Operations) {
		this.oauth2Operations = oauth2Operations;
	}

	// implementing OAuth2ServiceProvider
	
	public final OAuth2Operations getOAuthOperations() {
		return oauth2Operations;
	}

	public abstract S getApi(AccessToken accessToken);

}

