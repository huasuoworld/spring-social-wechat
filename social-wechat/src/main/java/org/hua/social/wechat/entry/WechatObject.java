package org.hua.social.wechat.entry;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;

public abstract class WechatObject {
	
	private Map<String, Object> extraData;

	public WechatObject() {
		this.extraData = new HashMap<String, Object>();
	}
	
	/**
	 * @return Any fields in response from Wechat that are otherwise not mapped to any properties.
	 */
	public Map<String, Object> getExtraData() {
		return extraData;
	}
	
	/**
	 * {@link JsonAnySetter} hook. Called when an otherwise unmapped property is being processed during JSON deserialization.
	 * @param key The property's key.
	 * @param value The property's value.
	 */
	protected void add(String key, Object value) {
		extraData.put(key, value);
	}
}
