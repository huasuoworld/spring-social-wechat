package org.hua.social.wechat.api;

import java.util.List;

import org.hua.social.wechat.entry.BatchGagging;
import org.hua.social.wechat.entry.Tag;
import org.hua.social.wechat.entry.Tags;

public interface TagsOperations {

	public List<Tag> get();
	public void update(Tags tags);
	public void delete(Tags tags);
	public void create(Tags tags);
	public String userTagGet(Integer tagid);
	public void batchtagging(BatchGagging batchtagging);
	public void batchuntagging(BatchGagging batchtagging);
	public List<Object> getidlist(String openid);
}
