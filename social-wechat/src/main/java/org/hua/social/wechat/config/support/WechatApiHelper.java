package org.hua.social.wechat.config.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.xml.ApiHelper;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UsersConnectionRepository;
import org.hua.social.wechat.api.Wechat;

/**
 * Support class for JavaConfig and XML configuration support.
 * Creates an API binding instance for the current user's connection.
 * @author Craig Walls
 */
public class WechatApiHelper implements ApiHelper<Wechat> {

	private final UsersConnectionRepository usersConnectionRepository;

	private final UserIdSource userIdSource;

	public WechatApiHelper(UsersConnectionRepository usersConnectionRepository, UserIdSource userIdSource) {
		this.usersConnectionRepository = usersConnectionRepository;
		this.userIdSource = userIdSource;		
	}

	public Wechat getApi() {
		if (log.isDebugEnabled()) {
			log.debug("Getting API binding instance for Wechat");
		}
		
		Connection<Wechat> connection = usersConnectionRepository.createConnectionRepository(userIdSource.getUserId()).findPrimaryConnection(Wechat.class);
		if (log.isDebugEnabled() && connection == null) {
			log.debug("No current connection; Returning default WechatTemplate instance.");
		}
		return connection != null ? connection.getApi() : null;
	}

	private final static Log log = LogFactory.getLog(WechatApiHelper.class);


}
