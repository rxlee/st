package cn.jy.stork.dao;

import cn.jy.stork.util.AJsonedObject;

/**
 * 
 * 扩展的检测执行人员信息，
 * 
 * 不直接对应表，是从tb_detect_corp和tb_detect_executor以及tb_detect_contract等表联合查询而来的
 * 
 * @author jsh
 *
 */
public class DetectExecutorEx extends AJsonedObject {
	private Integer uid; // 送样人员
	private String uname;
	private String uphone;
	private Integer rid;// 服务辖区
	private Integer cid; // 所属公司
	private String cname;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUphone() {
		return uphone;
	}

	public void setUphone(String uphone) {
		this.uphone = uphone;
	}

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

}
