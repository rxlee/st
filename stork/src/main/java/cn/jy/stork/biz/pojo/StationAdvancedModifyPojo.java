package cn.jy.stork.biz.pojo;

/**
 * 水站高级修改的对象，通常用于前端向后台传递用户输入
 * 
 * @author jsh
 *
 */
public class StationAdvancedModifyPojo {
	private Integer sn;
	private String vsn;
	private String vtoken;

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

}
