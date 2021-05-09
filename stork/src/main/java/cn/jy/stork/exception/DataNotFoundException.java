package cn.jy.stork.exception;

public class DataNotFoundException extends Exception {

	private static final long serialVersionUID = 1980341131255650055L;
	private String key;
	private Object value;

	@Deprecated
	public DataNotFoundException() {
	}

	public DataNotFoundException(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public String getMessage() {
		return String.format("找不到 %s 为 %s 的信息", key, value);
	}
}
