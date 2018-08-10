package org.hua.social.wechat.oauth2;

import org.hua.social.wechat.entry.AccessToken;
import org.springframework.social.ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;

/**
 * A ServiceProvider that uses the OAuth 2.0 protocol.
 * @author Keith Donald
 * @param <A> The service provider's API type
 */
public interface OAuth2ServiceProvider<A> extends ServiceProvider<A> {

	/**
	 * Get the service interface for carrying out the "OAuth dance" with this provider.
	 * The result of the OAuth dance is an access token that can be used to obtain a {@link #getApi(String) API binding}.
	 * @return an {@link OAuth2Operations} for carrying out the "OAuth dance" with this provider.
	 */
	OAuth2Operations getOAuthOperations();

	/**
	 * Returns an API interface allowing the client application to access protected resources on behalf of a user.
	 * @param accessToken the API access token
	 * @return a binding to the service provider's API
	 */
	A getApi(AccessToken accessToken);

}