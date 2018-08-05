package org.hua.social.wechat.common;

import java.io.File;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpClientTool {
	
	private final static Log log = LogFactory.getLog(HttpClientTool.class);
	
	public static String upload(File file, String url) {
		
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		try {
			FilePart filePart = new FilePart(file.getName(), file);
			Part[] parts = { filePart };

			postMethod.setRequestEntity(new MultipartRequestEntity(parts, postMethod.getParams()));
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
			httpClient.executeMethod(postMethod);
			log.info("responseString:" + postMethod.getResponseBodyAsString());
			return postMethod.getResponseBodyAsString();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		} finally {
			postMethod.releaseConnection();
		}
	}

}
