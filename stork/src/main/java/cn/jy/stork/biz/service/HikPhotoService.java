package cn.jy.stork.biz.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Service
public class HikPhotoService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${upload.discharge-photo-base-dir}")
	private String baseDir;

	private final String url = "https://open.ys7.com/api/lapp/device/capture";
	private final OkHttpClient client = new OkHttpClient();

	public String take(String device, String token) {
		final RequestBody requestBody = new FormBody.Builder()
				//
				.add("accessToken", token)
				//
				.add("deviceSerial", device)
				//
				.add("channelNo", String.valueOf(1))
				//
				.build();
		final Request request = new Request.Builder()
				//
				.url(url)
				//
				.post(requestBody)
				//
				.build();
		try (Response response = client.newCall(request).execute()) {
			final ResponseBody responseBody = response.body();
			if (responseBody == null) {
				return null;
			}
			final HikPic pic = new ObjectMapper().readValue(responseBody.string(), HikPic.class);
			if (!"200".equals(pic.getCode())) {
				return null;
			}
			final String imgUrl = pic.getData().getPicUrl();
			final String postfix = ".jpg";// imgUrl.substring(beginIndex, endIndex)

			// 检查和创建目录
			final File dir = new File(baseDir + "/" + device);
			if (dir.exists() == false) {
				dir.mkdirs();
			}

			// 生成文件名和创建文件
			final String fileName = String.format("%s%s", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()),
					postfix);
			final File file = new File(dir, fileName);
			file.createNewFile();

			// 写入内容
			final OutputStream out = new FileOutputStream(file);
			final InputStream in = new URL(imgUrl).openStream();
			final byte[] buf = new byte[4096];
			int len = 0;
			while ((len = in.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();

			return device + "/" + fileName;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	static class HikPic {
		private String msg;
		private String code;
		private HikPicData data;

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public HikPicData getData() {
			return data;
		}

		public void setData(HikPicData data) {
			this.data = data;
		}

	}

	static class HikPicData {
		private String picUrl;

		public String getPicUrl() {
			return picUrl;
		}

		public void setPicUrl(String picUrl) {
			this.picUrl = picUrl;
		}
	}
}
