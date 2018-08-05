package org.hua.social.wechat.api;

import java.io.File;

public interface MediaOperations {

	public String uploadImage(File file);
	public String uploadVoice(File file);
	public String uploadVideo(File file);
	public String uploadThumb(File file);
}
