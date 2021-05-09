package cn.jy.stork.dao;

import java.util.Date;

import cn.jy.stork.util.AJsonedObject;

/**
 * 实时数据
 * 
 * @author jsh
 *
 */
public class RealtimeData extends AJsonedObject {
	private Integer id;
	private Date rtime;// 数据采集的时间
	private Integer sid;
	private Date year;
	private Date month;
	private Date day;

	private Float t;
	private Float d;
	private Float s;
	private Float p;
	private Float cond;
	private Float oxy;
	private Integer ph;
	private Integer nh;
	private Integer turb;
	private Integer cod;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public Date getRtime() {
		return rtime;
	}

	public void setRtime(Date rtime) {
		this.rtime = rtime;
	}

	public Date getYear() {
		return year;
	}

	public void setYear(Date year) {
		this.year = year;
	}

	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		this.month = month;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public Float getT() {
		return t;
	}

	public void setT(Float t) {
		this.t = t;
	}

	public Float getD() {
		return d;
	}

	public void setD(Float d) {
		this.d = d;
	}

	public Float getS() {
		return s;
	}

	public void setS(Float s) {
		this.s = s;
	}

	public Float getP() {
		return p;
	}

	public void setP(Float p) {
		this.p = p;
	}

	public Float getCond() {
		return cond;
	}

	public void setCond(Float cond) {
		this.cond = cond;
	}

	public Float getOxy() {
		return oxy;
	}

	public void setOxy(Float oxy) {
		this.oxy = oxy;
	}

	public Integer getPh() {
		return ph;
	}

	public void setPh(Integer ph) {
		this.ph = ph;
	}

	public Integer getNh() {
		return nh;
	}

	public void setNh(Integer nh) {
		this.nh = nh;
	}

	public Integer getTurb() {
		return turb;
	}

	public void setTurb(Integer turb) {
		this.turb = turb;
	}

	public Integer getCod() {
		return cod;
	}

	public void setCod(Integer cod) {
		this.cod = cod;
	}

}
