package org.hua.social.wechat.api.impl;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hua.social.wechat.api.MediaOperations;
import org.hua.social.wechat.common.HttpClientTool;
import org.hua.social.wechat.entry.AccessToken;
import org.json.JSONObject;

public class MediaTemplate implements MediaOperations {
	
	private final static Log log = LogFactory.getLog(MediaTemplate.class);
	
	public static final String UPLOAD_IMAGE = "https://api.weixin.qq.com/cgi-bin/media/upload?type=image&access_token=%s";
	public static final String UPLOAD_VOICE = "https://api.weixin.qq.com/cgi-bin/media/upload?type=voic&access_token=%s";
	public static final String UPLOAD_VIDEO = "https://api.weixin.qq.com/cgi-bin/media/upload?type=video&access_token=%s";
	public static final String UPLOAD_THUMB = "https://api.weixin.qq.com/cgi-bin/media/upload?type=thumb&access_token=%s";
	private AccessToken accessToken;
	
	public MediaTemplate(AccessToken accessToken) {
		this.accessToken = accessToken;
	}

	public String uploadImage(File file) {
		try {
			String url = String.format(UPLOAD_IMAGE, accessToken.get());
			String src = HttpClientTool.upload(file, url);
			JSONObject json = new JSONObject(src);
			log.debug(src);
			return json.getString("media_id");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	public String uploadVoice(File file) {
		try {
			String url = String.format(UPLOAD_VOICE, accessToken);
			String src = HttpClientTool.upload(file, url);
			JSONObject json = new JSONObject(src);
			log.debug(src);
			return json.getString("media_id");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	public String uploadVideo(File file) {
		try {
			String url = String.format(UPLOAD_VIDEO, accessToken);
			String src = HttpClientTool.upload(file, url);
			JSONObject json = new JSONObject(src);
			log.debug(src);
			return json.getString("media_id");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	public String uploadThumb(File file) {
		try {
			String url = String.format(UPLOAD_THUMB, accessToken);
			String src = HttpClientTool.upload(file, url);
			JSONObject json = new JSONObject(src);
			log.debug(src);
			return json.getString("media_id");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
}
