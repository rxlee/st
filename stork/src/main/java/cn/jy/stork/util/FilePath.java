package cn.jy.stork.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FilePath {
	private String base;// 基准地址，如C:\dir\
	private String dir;// 相对于基准地址的目录
	private String prefix;// 文件名前缀
	private String postfix;// 文件名后缀（含.）

	private String _fullDir;// 完整目录
	private String _fullFileName;// 完整文件名

	private File file = null;

	/**
	 * @param base    基准地址，最后不要带\或/，如/data/project/tjenv/estimate/，会被nginx
	 *                alias映射为host:port/u
	 * @param dir     相对于基准地址的目录，必须以/打头，以/结尾，如/u/
	 * @param prefix
	 * @param postfix
	 * @throws IOException
	 */
	public FilePath(String base, String dir, String prefix, String postfix) throws IOException {
		if (base.endsWith("/") || base.endsWith("\\")) {
			base = base.substring(0, base.length() - 1);
		}
		if (!(dir.startsWith("/") || dir.startsWith("\\"))) {
			dir = "/" + dir;
		}
		if (!(dir.endsWith("/") || dir.endsWith("\\"))) {
			dir = dir + "/";
		}
		this.base = base;
		this.dir = dir;
		this.prefix = prefix;
		this.postfix = postfix;
		this._fullDir = base + dir;
		final File fullDir = new File(_fullDir);
		if (!fullDir.exists()) {
			fullDir.mkdirs();
		}
		this._fullFileName = String.format("%s-%s%s", prefix, fileNameMiddle(), postfix);
		this.file = new File(fullDir, _fullFileName);
		this.file.createNewFile();
	}

	public String write(InputStream in) throws IOException {
		final byte[] buf = new byte[4096];
		final OutputStream out = new FileOutputStream(file);
		int len = 0;
		while ((len = in.read(buf)) != -1) {
			out.write(buf, 0, len);
		}
		out.close();
		System.err.println("dir + _fullFileName === " + (dir + _fullFileName));
		return dir + _fullFileName;
	}

	/**
	 * 文件名中间段
	 * 
	 * @return
	 */
	protected String fileNameMiddle() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

}
