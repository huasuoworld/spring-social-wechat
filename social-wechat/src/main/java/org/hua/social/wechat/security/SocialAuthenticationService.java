package org.hua.social.wechat.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hua.social.wechat.connect.ConnectionFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.security.SocialAuthenticationRedirectException;
import org.springframework.social.security.SocialAuthenticationToken;

/**
 * Authentication for social {@link ConnectionFactory}
 * @param <S> The provider's API type.
 * @author Stefan Fussennegger
 */
public interface SocialAuthenticationService<S> {

	public enum ConnectionCardinality {
		/**
		 * only one connected providerUserId per userId and vice versa
		 */
		ONE_TO_ONE(false, false),

		/**
		 * many connected providerUserIds per userId, but only one userId per providerUserId
		 */
		ONE_TO_MANY(false, true),

		/**
		 * only one providerUserId per userId, but many userIds per providerUserId. 
		 * Authentication of users not possible
		 */
		MANY_TO_ONE(true, false),

		/**
		 * no restrictions. Authentication of users not possible
		 */
		MANY_TO_MANY(true, true);
		
		private final boolean multiUserId;
		private final boolean multiProviderUserId;

		private ConnectionCardinality(boolean multiUserId, boolean multiProviderUserId) {
			this.multiUserId = multiUserId;
			this.multiProviderUserId = multiProviderUserId;
		}

		/**
		 * allow many userIds per providerUserId. If true, authentication is not possible
		 * @return true if multiple local users are allowed per provider user ID
		 */
		public boolean isMultiUserId() {
			return multiUserId;
		}

		/**
		 * allow many providerUserIds per userId
		 * @return true if users are allowed multiple connections to a provider
		 */
		public boolean isMultiProviderUserId() {
			return multiProviderUserId;
		}

		public boolean isAuthenticatePossible() {
			return !isMultiUserId();
		}
	}

	/**
	 * @return {@link ConnectionCardinality} for connections to this provider
	 */
	ConnectionCardinality getConnectionCardinality();
	
	/**
	 * @return {@link ConnectionFactory} used for authentication
	 */
	ConnectionFactory<S> getConnectionFactory();

	/**
	 * extract {@link SocialAuthenticationToken} from request
	 * 
	 * @param request current {@link HttpServletRequest}
	 * @param response current {@link HttpServletResponse}
	 * 
	 * @return new unauthenticated token or null
	 * @throws SocialAuthenticationRedirectException if social auth requires a redirect, e.g. OAuth
	 */
	SocialAuthenticationToken getAuthToken(HttpServletRequest request, HttpServletResponse response) throws SocialAuthenticationRedirectException;

	/**
	 * 
	 * @param request current {@link HttpServletRequest}
	 * @param connection the connection from which to calculate the redirect URL
	 * @return null to use filter default
	 */
	String getConnectionAddedRedirectUrl(HttpServletRequest request, Connection<?> connection);

}

