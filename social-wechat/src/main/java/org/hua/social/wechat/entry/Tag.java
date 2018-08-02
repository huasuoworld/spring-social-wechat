package org.hua.social.wechat.entry;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hua.social.wechat.common.Constant;
import org.json.JSONArray;
import org.json.JSONObject;

public class Tag  extends WechatObject implements Serializable  {
	
	private final static Log log = LogFactory.getLog(Tag.class);
	
	@SuppressWarnings("unchecked")
	public static List<Tag> fromResponse(String src) {
		try {
			JSONObject tags = new JSONObject(src);
			JSONArray array = tags.getJSONArray("tags");
			List<Tag> tag = Constant.objectMapper.readValue(array.toString(), List.class);
			return tag;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	private static final long serialVersionUID = -8174718304567758866L;
	private Integer id;
	private String name;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
