package org.hua.social.wechat.config.xml;


import java.util.HashMap;
import java.util.Map;

import org.hua.social.wechat.connect.ConnectionFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.social.config.xml.ApiHelper;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Abstract bean definition parser for configuring provider-specific beans in a Spring application context.
 * Automatically creates a {@link ConnectionFactoryLocator} bean if none exists and registers the {@link ConnectionFactory} bean with the {@link ConnectionFactoryLocator}.
 * Also creates a request-scoped API binding bean retrieved from the connection repository.
 * @author Craig Walls
 */
public abstract class AbstractProviderConfigBeanDefinitionParser extends ProviderConfigurationSupport implements BeanDefinitionParser {

	/**
	 * Constructs a connection factory-creating {@link BeanDefinitionParser}.
	 * @param connectionFactoryClass The type of {@link ConnectionFactory} to create. Must have a two-argument constructor taking an application's ID and secret as Strings.
	 * @param apiHelperClass the API helper class
	 */
	protected AbstractProviderConfigBeanDefinitionParser(Class<? extends ConnectionFactory<?>> connectionFactoryClass, Class<? extends ApiHelper<?>> apiHelperClass) {
		super(connectionFactoryClass, apiHelperClass);
	}

	public BeanDefinition parse(Element element, ParserContext parserContext) {
		BeanDefinitionRegistry registry = parserContext.getRegistry();
		Map<String, Object> allAttributes = toMap(element.getAttributes());		
		return registerBeanDefinitions(registry, allAttributes);
	}

	private Map<String, Object> toMap(NamedNodeMap attributes) {
		Map<String, Object> map = new HashMap<String, Object>(attributes.getLength());
		for (int i=0; i < attributes.getLength(); i++) {
			Node attribute = attributes.item(i);
			map.put(attribute.getNodeName(),  attribute.getNodeValue());
		}
		return map;
	}
	
	@Override
	protected String getAppId(Map<String, Object> allAttributes) {
		return (String) allAttributes.get("app-id");
	}
	
	@Override
	protected String getAppSecret(Map<String, Object> allAttributes) {
		return (String) allAttributes.get("app-secret");
	}

}

