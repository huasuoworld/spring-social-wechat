package org.hua.social.wechat.connect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.hua.social.wechat.entry.AccessToken;
import org.hua.social.wechat.entry.AccessTokenImpl;
import org.hua.social.wechat.oauth2.OAuth2ServiceProvider;
import org.springframework.core.GenericTypeResolver;
import org.springframework.social.ExpiredAuthorizationException;
import org.springframework.social.ServiceProvider;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.AbstractConnection;
import org.springframework.social.oauth2.AccessGrant;

/**
 * An OAuth2-based Connection implementation.
 * In general, this implementation is expected to be suitable for all OAuth2-based providers and should not require subclassing.
 * Subclasses of {@link OAuth2ConnectionFactory} should be favored to encapsulate details specific to an OAuth2-based provider.
 * @author Keith Donald
 * @param <A> the service provider's API type
 * @see OAuth2ConnectionFactory
 */
public class OAuth2Connection<A> extends AbstractConnection<A> {

	private static final long serialVersionUID = 4057584084077577480L;

	private transient final OAuth2ServiceProvider<A> serviceProvider;

	private AccessToken accessToken;
	
	private String refreshToken;
	
	private Long expireTime;

	private transient A api;
	
	private transient A apiProxy;

	/**
	 * Creates a new {@link OAuth2Connection} from a access grant response.
	 * Designed to be called to establish a new {@link OAuth2Connection} after receiving an access grant successfully.
	 * The providerUserId may be null in this case: if so, this constructor will try to resolve it using the service API obtained from the {@link OAuth2ServiceProvider}.
	 * @param providerId the provider id e.g. "facebook".
	 * @param providerUserId the provider user id (may be null if not returned as part of the access grant)
	 * @param accessToken the granted access token
	 * @param refreshToken the granted refresh token
	 * @param expireTime the access token expiration time
	 * @param serviceProvider the OAuth2-based ServiceProvider
	 * @param apiAdapter the ApiAdapter for the ServiceProvider
	 */
	public OAuth2Connection(String providerId, String providerUserId, AccessToken accessToken, String refreshToken, Long expireTime,
			OAuth2ServiceProvider<A> serviceProvider, ApiAdapter<A> apiAdapter) {
		super(apiAdapter);
		this.serviceProvider = serviceProvider;
		initAccessTokens(accessToken, refreshToken, expireTime);
		initApi();
		initApiProxy();
		initKey(providerId, providerUserId);
	}
	
	/**
	 * Creates a new {@link OAuth2Connection} from the data provided.
	 * Designed to be called when re-constituting an existing {@link Connection} from {@link ConnectionData}.
	 * @param data the data holding the state of this connection
	 * @param serviceProvider the OAuth2-based ServiceProvider
	 * @param apiAdapter the ApiAdapter for the ServiceProvider
	 */
	public OAuth2Connection(ConnectionData data, OAuth2ServiceProvider<A> serviceProvider, ApiAdapter<A> apiAdapter) {
		super(data, apiAdapter);
		this.serviceProvider = serviceProvider;
		AccessToken AccessToken = new AccessTokenImpl();
		AccessToken.setAccessToken(data.getAccessToken());
		initAccessTokens(AccessToken, data.getRefreshToken(), data.getExpireTime());
		initApi();
		initApiProxy();
	}

	// implementing Connection

	public boolean hasExpired() {
		synchronized (getMonitor()) {
			return expireTime != null && System.currentTimeMillis() >= expireTime;
		}
	}

	public void refresh() {
		synchronized (getMonitor()) {
			AccessGrant accessGrant = serviceProvider.getOAuthOperations().refreshAccess(refreshToken, null);
			AccessToken AccessToken = new AccessTokenImpl();
			AccessToken.setAccessToken(accessGrant.getAccessToken());
			initAccessTokens(AccessToken, accessGrant.getRefreshToken(), accessGrant.getExpireTime());
			initApi();
		}
	}

	public A getApi() {
		if (apiProxy != null) {
			return apiProxy;
		} else {
			synchronized (getMonitor()) {
				return api;
			}
		}
	}

	public ConnectionData createData() {
		synchronized (getMonitor()) {
			return new ConnectionData(getKey().getProviderId(), getKey().getProviderUserId(), getDisplayName(), getProfileUrl(), getImageUrl(), accessToken.get(), null, refreshToken, expireTime);
		}
	}

	// internal helpers
	
	private void initAccessTokens(AccessToken accessToken, String refreshToken, Long expireTime) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expireTime = expireTime;		
	}
	
	private void initApi() {
		api = serviceProvider.getApi(accessToken);
	}
	
	@SuppressWarnings("unchecked")
	private void initApiProxy() {
		Class<?> apiType = GenericTypeResolver.resolveTypeArgument(serviceProvider.getClass(), ServiceProvider.class);
		if (apiType.isInterface()) {
			apiProxy = (A) Proxy.newProxyInstance(apiType.getClassLoader(), new Class<?>[] { apiType }, new ApiInvocationHandler());
		}		
	}
	
	private class ApiInvocationHandler implements InvocationHandler {

		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			synchronized (getMonitor()) {
				if (hasExpired()) {
					throw new ExpiredAuthorizationException(getKey().getProviderId());
				}
				try {
					return method.invoke(OAuth2Connection.this.api, args);
				} catch (InvocationTargetException e) {
					throw e.getTargetException();
				}
			}
		}
	}

	// equas() and hashCode() generated by Eclipse
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((accessToken == null) ? 0 : accessToken.hashCode());
		result = prime * result + ((expireTime == null) ? 0 : expireTime.hashCode());
		result = prime * result + ((refreshToken == null) ? 0 : refreshToken.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (getClass() != obj.getClass()) return false;
		@SuppressWarnings("rawtypes")
		OAuth2Connection other = (OAuth2Connection) obj;
		
		if (accessToken == null) {
			if (other.accessToken != null) return false;
		} else if (!accessToken.equals(other.accessToken)) return false;

		if (expireTime == null) {
			if (other.expireTime != null) return false;
		} else if (!expireTime.equals(other.expireTime)) return false;
		
		if (refreshToken == null) {
			if (other.refreshToken != null) return false;
		} else if (!refreshToken.equals(other.refreshToken)) return false;

		return true;
	}


}
