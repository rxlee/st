package cn.jy.stork.exception;

/**
 * 数据重复（唯一字段冲突）
 * 
 * @author jsh
 *
 */
public class DataDuplicatedException extends Exception {

	private static final long serialVersionUID = -7909414889968916308L;
	private String key;
	private Object value;

	@Deprecated
	public DataDuplicatedException() {
	}

	public DataDuplicatedException(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public String getMessage() {
		return String.format("已存在 %s 为 %s 的信息", key, value);
	}

}
