<h1>spring-social-wechat</h1>
<blockquote>
<p>The Spring Social Wechat project is an extension to Spring Social that enables integration with Wechat.</p>
</blockquote>

<h2>1. Introduction</h2>
<blockquote>
With over 10.4 billion users (and growing), Wechat is the largest online social network. While bringing together friends and family, Wechat also offers a rich platform on which to develop applications.

Spring Social Wechat enables integration with Wechat with WechatConnectionFactory, a connection factory that can be plugged into Spring Social’s service provider connection framework, and with an API binding to Wechat’s REST API.
</blockquote>

<h2>2. Maven </h2>

```xml
<repositories>
    <repository>
        <id>social-wechat-master</id>
        <url>https://raw.githubusercontent.com/huasuoworld/social-wechat/master</url>
        <releases>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
</repositories>

<dependency>
    <groupId>org.hua</groupId>
    <artifactId>social-wechat</artifactId>
    <version>0.0.1</version>
</dependency>
```

<h2>3. Java configuration</h2>

```
import org.hua.social.wechat.connect.WechatConnectionFactory;
import org.hua.social.wechat.entry.WechatUserIdSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;

@Configuration
@ConfigurationProperties
public class SocialConfig implements SocialConfigurer {
	
	@Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
        cfConfig.addConnectionFactory(new WechatConnectionFactory(
            env.getProperty("wechat.clientId"),
            env.getProperty("wechat.clientSecret")));
    }

	@Override
	public UserIdSource getUserIdSource() {
		String openid = "xxx";
		return new WechatUserIdSource(openid);
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator 						connectionFactoryLocator) {
		return null;
	}
}
```

<h2>4. Retrieving a user’s profile data</h2>

```
import org.hua.social.wechat.api.Wechat;
import org.hua.social.wechat.api.impl.WechatTemplate;
import org.hua.social.wechat.entry.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class HelloworldApp {
	
	private static final Logger log = LoggerFactory.getLogger(HelloworldApp.class);
    @RequestMapping("/")
    public String index() {
    	log.info("helloworld!");
    	// access token received from Wechat after OAuth authorization
    	String accessToken = "xxx"; 
    	Wechat wechat = new WechatTemplate(accessToken);
    	User user = wechat.userOperations().getUserProfileByOpenid("xxx");
        return user.toString();
    }
    
    public static void main(String[] args) {
    	SpringApplication.run(HelloworldApp.class, args);
    }
}
```
