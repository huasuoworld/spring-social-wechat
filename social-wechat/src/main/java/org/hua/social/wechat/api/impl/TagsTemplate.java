package org.hua.social.wechat.api.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hua.social.wechat.api.TagsOperations;
import org.hua.social.wechat.common.Base64Tool;
import org.hua.social.wechat.common.Constant;
import org.hua.social.wechat.common.HttpTool;
import org.hua.social.wechat.entry.BatchGagging;
import org.hua.social.wechat.entry.Tag;
import org.hua.social.wechat.entry.Tags;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

public class TagsTemplate implements TagsOperations {
	
	private final static Log log = LogFactory.getLog(TagsTemplate.class);
	
	public static final String CREATE = "https://api.weixin.qq.com/cgi-bin/tags/create";
	public static final String GET = "https://api.weixin.qq.com/cgi-bin/tags/get";
	public static final String UPDATE = "https://api.weixin.qq.com/cgi-bin/tags/update";
	public static final String DELETE = "https://api.weixin.qq.com/cgi-bin/tags/delete";
	public static final String TAG_USERS = "https://api.weixin.qq.com/cgi-bin/user/tag/get";
	public static final String BATCH_TAGGING = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging";
	public static final String BATCH_UN_TAGGING = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging";
	public static final String GETID_LIST = "https://api.weixin.qq.com/cgi-bin/tags/getidlist";
	
	private final RestTemplate restTemplate;
	
	public TagsTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public List<Tag> get() {
		String src = restTemplate.getForObject(GET, String.class);
		log.debug(src);
		List<Tag> tags = Tag.fromResponse(src);
		return tags;
	}

	public void update(Tags tags) {
		try {
			HttpEntity<String> request = new HttpEntity<String>(Constant.objectMapper.writeValueAsString(tags), HttpTool.applicationJson());
			String src = restTemplate.postForObject(UPDATE, request, String.class);
			log.debug(src);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void delete(Tags tags) {
		try {
			HttpEntity<String> request = new HttpEntity<String>(Constant.objectMapper.writeValueAsString(tags), HttpTool.applicationJson());
			String src = restTemplate.postForObject(DELETE, request, String.class);
			log.debug(src);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void create(Tags tags) {
		try {
			HttpEntity<String> request = new HttpEntity<String>(Constant.objectMapper.writeValueAsString(tags), HttpTool.applicationJson());
			String src = restTemplate.postForObject(CREATE, request, String.class);
			log.debug(src);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public String userTagGet(Integer tagid) {
		
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			String path = System.getProperty("temp.file.path");
			if(StringUtils.isEmpty(path)) {
				path = "/tmp/";
			}
			File file = new File(path + Constant.uuid() + ".csv");
			fw = new FileWriter(file, true);
			pw = new PrintWriter(fw);
			
			String next_openid = null;
			while(true) {
				
				JSONObject param = new JSONObject();
				param.put("tagid", tagid);
				if(!StringUtils.isEmpty(next_openid)) {
					param.put("next_openid", next_openid);
				}
				
				HttpEntity<String> request = new HttpEntity<String>(param.toString(), HttpTool.applicationJson());
				String response = restTemplate.postForObject(TAG_USERS, request, String.class);
				JSONObject responseObject = new JSONObject(response);
				
				JSONArray resp = responseObject.getJSONObject("data").getJSONArray("openid");
				for(int i = 0; i < resp.length(); i++) {
					pw.println(resp.getString(i));
				}
				
				if(responseObject.has("next_openid") && !StringUtils.isEmpty(responseObject.getString("next_openid"))) {
					next_openid = responseObject.getString("next_openid");
				} else {
					break;
				}
			}
			
			byte[] fileByte = FileUtils.readFileToByteArray(file);
			String base64file = Base64Tool.encodeImage(fileByte);
			return base64file;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		} finally {
			if(pw != null) {
				try {
					pw.close();
				} catch (Exception e2) {
					log.error(e2.getMessage(), e2);
				}
			}
			if(fw != null) {
				try {
					fw.close();
				} catch (Exception e2) {
					log.error(e2.getMessage(), e2);
				}
			}
		}
	}

	public void batchtagging(BatchGagging batchtagging) {
		try {
			HttpEntity<String> request = new HttpEntity<String>(Constant.objectMapper.writeValueAsString(batchtagging), HttpTool.applicationJson());
			String src = restTemplate.postForObject(BATCH_TAGGING, request, String.class);
			log.debug(src);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void batchuntagging(BatchGagging batchtagging) {
		try {
			HttpEntity<String> request = new HttpEntity<String>(Constant.objectMapper.writeValueAsString(batchtagging), HttpTool.applicationJson());
			String src = restTemplate.postForObject(BATCH_UN_TAGGING, request, String.class);
			log.debug(src);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public List<Object> getidlist(String openid) {
		try {
			JSONObject json = new JSONObject();
			json.put("openid", openid);
			HttpEntity<String> request = new HttpEntity<String>(json.toString(), HttpTool.applicationJson());
			String src = restTemplate.postForObject(BATCH_UN_TAGGING, request, String.class);
			log.debug(src);
			JSONObject response = new JSONObject(src);
			JSONArray array = response.getJSONArray("tagid_list");
			return array.toList();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

}
