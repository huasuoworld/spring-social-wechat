package org.hua.social.wechat.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hua.social.wechat.api.UserOperations;
import org.hua.social.wechat.api.Wechat;
import org.hua.social.wechat.api.impl.json.WechatModule;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.OAuth2Version;
import org.springframework.social.oauth2.TokenStrategy;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WechatTemplate extends AbstractOAuth2ApiBinding implements Wechat {
	
	private final static Log log = LogFactory.getLog(WechatTemplate.class);
	
	private String appId;

	private ObjectMapper objectMapper;

	private String applicationNamespace;
	
	private UserOperations userOperations;
	
	
	/**
	 * Create a new instance of WechatTemplate.
	 * This constructor creates the WechatTemplate using a given access token.
	 * @param accessToken An access token given by Wechat after a successful OAuth 2 authentication (or through Wechat JS library).
	 */
	public WechatTemplate(String accessToken) {
		this(accessToken, null);
	}

	public WechatTemplate(String accessToken, String applicationNamespace) {
		this(accessToken, applicationNamespace, null);
	}
	
	public WechatTemplate(String accessToken, String applicationNamespace, String appId) {
		super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
		this.applicationNamespace = applicationNamespace;
		this.appId = appId;
		initialize();
	}
	
	@Override
	public void setRequestFactory(ClientHttpRequestFactory requestFactory) {
		// Wrap the request factory with a BufferingClientHttpRequestFactory so that the error handler can do repeat reads on the response.getBody()
		super.setRequestFactory(ClientHttpRequestFactorySelector.bufferRequests(requestFactory));
	}
	
	// AbstractOAuth2ApiBinding hooks
	@Override
	protected OAuth2Version getOAuth2Version() {
		return OAuth2Version.BEARER_DRAFT_2;
	}

	@Override
	protected void configureRestTemplate(RestTemplate restTemplate) {
		restTemplate.setErrorHandler(new WechatErrorHandler());
	}
	
	@Override
	protected MappingJackson2HttpMessageConverter getJsonMessageConverter() {
		MappingJackson2HttpMessageConverter converter = super.getJsonMessageConverter();
		objectMapper = new ObjectMapper();				
		objectMapper.registerModule(new WechatModule());
		converter.setObjectMapper(objectMapper);		
		return converter;
	}
	
	// private helpers
	private void initialize() {
		// Wrap the request factory with a BufferingClientHttpRequestFactory so that the error handler can do repeat reads on the response.getBody()
		super.setRequestFactory(ClientHttpRequestFactorySelector.bufferRequests(getRestTemplate().getRequestFactory()));
		initSubApis();
	}
	
	private void initSubApis() {
		log.info("start initSubApis..............");
		userOperations = new UserTemplate(getRestTemplate());
		log.info("end initSubApis..............");
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public String getApplicationNamespace() {
		return applicationNamespace;
	}

	public void setApplicationNamespace(String applicationNamespace) {
		this.applicationNamespace = applicationNamespace;
	}

	public UserOperations getUserOperations() {
		return userOperations;
	}

	public void setUserOperations(UserOperations userOperations) {
		this.userOperations = userOperations;
	}

	public UserOperations userOperations() {
		return userOperations;
	}
}