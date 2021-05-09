package cn.jy.stork.biz.pojo;

/**
 * 水站创建和修改的对象，通常用于前端向后台传递用户输入
 * 
 * @author jsh
 *
 */
public class StationProfilePojo {
	private String name;
	private String alias;
	private String memo;
	private String code;
	private Integer rid;// 所属辖区id，由前端传递
	private String rname;// 所属辖区名称，一般不需要前端输入，由后台查询后填充
	private Integer sn;
	private String vsn;
	private String vtoken;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public String getVsn() {
		return vsn;
	}

	public void setVsn(String vsn) {
		this.vsn = vsn;
	}

	public String getVtoken() {
		return vtoken;
	}

	public void setVtoken(String vtoken) {
		this.vtoken = vtoken;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
