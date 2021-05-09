package cn.jy.stork.dao;

import java.util.Date;

//合同和关联人一起传入
public class DetectContract {
	private Integer id;
	private Integer cid;
	private String cname;
	private String calias;
	private Integer rid;
	private String rname;
	private String ralias;
	private Date startTime;
	private Date endTime;

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCalias() {
		return calias;
	}

	public void setCalias(String calias) {
		this.calias = calias;
	}

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	public String getRalias() {
		return ralias;
	}

	public void setRalias(String ralias) {
		this.ralias = ralias;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

}
