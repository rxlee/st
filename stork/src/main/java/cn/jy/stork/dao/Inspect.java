package cn.jy.stork.dao;

import java.util.Date;

public class Inspect {
	private int id;
	private Date utime;
	private int uid;
	private String umemo;
	private Date atime;
	private int aid;
	private int rid;
	private String amemo;
	private String result;
	private float lng;
	private float lat;
	private int step;
	
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getLng() {
		return lng;
	}
	public void setLng(float lng) {
		this.lng = lng;
	}
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	public Date getUtime() {
		return utime;
	}
	public void setUtime(Date utime) {
		this.utime = utime;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUmemo() {
		return umemo;
	}
	public void setUmemo(String umemo) {
		this.umemo = umemo;
	}
	public Date getAtime() {
		return atime;
	}
	public void setAtime(Date atime) {
		this.atime = atime;
	}
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public String getAmemo() {
		return amemo;
	}
	public void setAmemo(String amemo) {
		this.amemo = amemo;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}
