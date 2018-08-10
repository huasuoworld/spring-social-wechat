package org.hua.social.wechat.connect;

import java.util.UUID;

import org.hua.social.wechat.entry.AccessToken;
import org.hua.social.wechat.entry.AccessTokenImpl;
import org.hua.social.wechat.oauth2.OAuth2ServiceProvider;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;

/**
 * Factory for creating OAuth2-based {@link Connection}s.
 * May be subclassed to further simplify construction e.g. FacebookConnectionFactory.
 * @author Keith Donald
 * @param <S> the service API type.
 */
public class OAuth2ConnectionFactory<S> extends ConnectionFactory<S> {

	private String scope = null;
	
	/**
	 * Create a {@link OAuth2ConnectionFactory}.
	 * @param providerId the provider id e.g. "facebook"
	 * @param serviceProvider the ServiceProvider model for conducting the authorization flow and obtaining a native service API instance.
	 * @param apiAdapter the ApiAdapter for mapping the provider-specific service API model to the uniform {@link Connection} interface.
	 */
	public OAuth2ConnectionFactory(String providerId, OAuth2ServiceProvider<S> serviceProvider, ApiAdapter<S> apiAdapter) {
		super(providerId, serviceProvider, apiAdapter);
	}
	
	/**
	 * Sets the default value to send in the scope parameter during authorization.
	 * Null by default, meaning that no scope parameter will be sent and the default scope will be determined by the provider.
	 * @param scope The default value to send as scope during authorization.
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	public String getScope() {
		return scope;
	}
	
	/**
	 * Generates a value for the state parameter.
	 * @return a random UUID by default. 
	 */
	public String generateState() {
		return UUID.randomUUID().toString();
	}

	/**
	 * Indicates that this provider supports the state parameter in callbacks to prevent against CSRF.
	 * Default implementation returns true. 
	 * @return true if the provider supports the state parameter
	 */
	public boolean supportsStateParameter() {
		return true;
	}

	/**
	 * Get the ServiceProvider's {@link OAuth2Operations} that allows the client application to conduct the OAuth2 flow with the provider.
	 * @return an OAuth2Operations
	 */
	public OAuth2Operations getOAuthOperations() {
		return getOAuth2ServiceProvider().getOAuthOperations();
	}

	/**
	 * Create a OAuth2-based {@link Connection} from the {@link AccessGrant} returned after {@link #getOAuthOperations() completing the OAuth2 flow}.
	 * @param accessGrant the access grant
	 * @return the new service provider connection
	 * @see OAuth2Operations#exchangeForAccess(String, String, org.springframework.util.MultiValueMap)
	 */
	public Connection<S> createConnection(AccessGrant accessGrant) {
		AccessToken AccessToken = new AccessTokenImpl();
		AccessToken.setAccessToken( accessGrant.getAccessToken());
		return new OAuth2Connection<S>(getProviderId(), extractProviderUserId(accessGrant), AccessToken,
				accessGrant.getRefreshToken(), accessGrant.getExpireTime(), getOAuth2ServiceProvider(), getApiAdapter());		
	}

	/**
	 * Create a OAuth2-based {@link Connection} from the connection data.
	 * @param data connection data from which to create the connection
	 */
	public Connection<S> createConnection(ConnectionData data) {
		return new OAuth2Connection<S>(data, getOAuth2ServiceProvider(), getApiAdapter());
	}
	
	// subclassing hooks

	/**
	 * Hook for extracting the providerUserId from the returned {@link AccessGrant}, if it is available.
	 * Default implementation returns null, indicating it is not exposed and another remote API call will be required to obtain it.
	 * Subclasses may override.
	 * @param accessGrant an AccessGrant from which to extract the provider ID
	 * @return the pvodier ID, if available
	 */
	protected String extractProviderUserId(AccessGrant accessGrant) {
		return null;
	}

	// internal helpers
	
	private OAuth2ServiceProvider<S> getOAuth2ServiceProvider() {
		return (OAuth2ServiceProvider<S>) getServiceProvider();
	}
	
}

