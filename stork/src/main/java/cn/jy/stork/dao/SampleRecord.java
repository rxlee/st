package cn.jy.stork.dao;

import java.util.Date;

import cn.jy.stork.util.AJsonedObject;

/**
 * 突发排放和自动采样记录
 * 
 * @author jsh
 *
 */
public class SampleRecord extends AJsonedObject {
	private Integer id;
	private Date stime;
	private Integer rid;
	private String rname;
	private String ralias;
	private Integer sid;
	private String sname;
	private String salias;
	private Integer bottle;
	private Integer detectId;
	private String scode;
	private Date dischargeStart;
	private Date dischargeEnd;
	private String photo;

	public String getScode() {
		return scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}

	public Date getDischargeStart() {
		return dischargeStart;
	}

	public void setDischargeStart(Date dischargeStart) {
		this.dischargeStart = dischargeStart;
	}

	public Date getDischargeEnd() {
		return dischargeEnd;
	}

	public void setDischargeEnd(Date dischargeEnd) {
		this.dischargeEnd = dischargeEnd;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getStime() {
		return stime;
	}

	public void setStime(Date stime) {
		this.stime = stime;
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

	public String getRalias() {
		return ralias;
	}

	public void setRalias(String ralias) {
		this.ralias = ralias;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getSalias() {
		return salias;
	}

	public void setSalias(String salias) {
		this.salias = salias;
	}

	public Integer getBottle() {
		return bottle;
	}

	public void setBottle(Integer bottle) {
		this.bottle = bottle;
	}

	public Integer getDetectId() {
		return detectId;
	}

	public void setDetectId(Integer detectId) {
		this.detectId = detectId;
	}

}
