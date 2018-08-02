package org.hua.social.wechat.entry;

import java.io.Serializable;

public class Tags  extends WechatObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6663603981104130220L;

	private Tag tag;

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}
}
