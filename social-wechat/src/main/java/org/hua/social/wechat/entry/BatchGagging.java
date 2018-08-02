package org.hua.social.wechat.entry;

import java.util.List;

public class BatchGagging {

	private Integer tagid;
	private List<String> openid_list;
	
	public Integer getTagid() {
		return tagid;
	}
	public void setTagid(Integer tagid) {
		this.tagid = tagid;
	}
	public List<String> getOpenid_list() {
		return openid_list;
	}
	public void setOpenid_list(List<String> openid_list) {
		this.openid_list = openid_list;
	}
}
