package org.hua.social.wechat.oauth2;

import java.io.IOException;

import org.hua.social.wechat.entry.AccessToken;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.support.HttpRequestDecorator;

/**
 * ClientHttpRequestInterceptor implementation that adds the OAuth2 access token as a query parameter to protected resource requests before execution.
 * @author Craig Walls
 * @author hcl
 */
class OAuth2TokenParameterRequestInterceptor implements ClientHttpRequestInterceptor {

	private final String parameterName;
	
	private final AccessToken accessToken;
	
	/**
	 * Creates an instance of the interceptor, defaulting to use a parameter named "access_token".
	 * @param accessToken The access token.
	 */
	public OAuth2TokenParameterRequestInterceptor(AccessToken accessToken) {
		this(accessToken, "access_token");
	}

	/**
	 * Creates an instance of the interceptor, using a parameter with the specified name.
	 * @param accessToken The access token.
	 * @param parameterName The name of the query parameter that will carry the access token. 
	 */
	public OAuth2TokenParameterRequestInterceptor(AccessToken accessToken, String parameterName) {
		this.accessToken = accessToken;
		this.parameterName = parameterName;
	}

	public ClientHttpResponse intercept(final HttpRequest request, final byte[] body, ClientHttpRequestExecution execution) throws IOException {
		HttpRequestDecorator protectedResourceRequest = new HttpRequestDecorator(request);
		protectedResourceRequest.addParameter(parameterName, accessToken.get());
		return execution.execute(protectedResourceRequest, body);
	}

}
