package cn.jy.stork.dao;

import java.util.Date;

public class DetectReport {
	private int id;
	private int processId;
	private Date ptime;
	private String pname;
	private int pid;
	private int rid;
	private int sid;
	private Date stime ;
	private String chargeContent;
	private String chargexContent;
	private int v1;
	private int v2;
	private int v3;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProcessId() {
		return processId;
	}
	public void setProcessId(int processId) {
		this.processId = processId;
	}
	public Date getPtime() {
		return ptime;
	}
	public void setPtime(Date ptime) {
		this.ptime = ptime;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public Date getStime() {
		return stime;
	}
	public void setStime(Date stime) {
		this.stime = stime;
	}
	public String getChargeContent() {
		return chargeContent;
	}
	public void setChargeContent(String chargeContent) {
		this.chargeContent = chargeContent;
	}
	public String getChargexContent() {
		return chargexContent;
	}
	public void setChargexContent(String chargexContent) {
		this.chargexContent = chargexContent;
	}
	public int getV1() {
		return v1;
	}
	public void setV1(int v1) {
		this.v1 = v1;
	}
	public int getV2() {
		return v2;
	}
	public void setV2(int v2) {
		this.v2 = v2;
	}
	public int getV3() {
		return v3;
	}
	public void setV3(int v3) {
		this.v3 = v3;
	}
	
}
