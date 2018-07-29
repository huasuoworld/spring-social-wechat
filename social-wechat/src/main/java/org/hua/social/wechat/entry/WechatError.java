package org.hua.social.wechat.entry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.http.client.ClientHttpResponse;

/**
 * @author hua
 *	wechat error Transformation
 */
public class WechatError {
	
	private final static Log log = LogFactory.getLog(WechatError.class);
	
	private static final String ERRCODE_ = "errcode";
	private static final String ERRMSG_ = "errmsg";

	private Integer errcode;
	private String errmsg;
	@Override
	public String toString() {
		return "WechatError [errcode=" + errcode + ", errmsg=" + errmsg + "]";
	}
	public WechatError(Integer errcode, String errmsg) {
		super();
		this.errcode = errcode;
		this.errmsg = errmsg;
	}
	public static WechatError getError(String response) {
		JSONObject json = new JSONObject(response);
		Integer errorcodeJ = json.getInt(ERRCODE_);
		String errmsgJ = json.getString(ERRMSG_);
		return new WechatError(errorcodeJ, errmsgJ);
	}
	public static boolean hasError(String response) {
		JSONObject json = new JSONObject(response);
		boolean has = json.has("errcode");
		return has;
	}
	
	public static WechatError extractErrorFromResponse(ClientHttpResponse response) throws IOException {
		String json = readResponseJson(response);
		if(hasError(json)) {
			WechatError error = getError(json);
			log.info(error.toString());
			return error;
		}
		return null;
	}
	
	private static String readResponseJson(ClientHttpResponse response) throws IOException {
		String json = readFully(response.getBody());
		if (log.isDebugEnabled()) {
			log.debug("Error from Wechat: " + json);
		}
		return json;
	}
	
	private static String readFully(InputStream in) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder sb = new StringBuilder();
		while (reader.ready()) {
			sb.append(reader.readLine());
		}
		return sb.toString();
	}
	public Integer getErrcode() {
		return errcode;
	}
	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
}
