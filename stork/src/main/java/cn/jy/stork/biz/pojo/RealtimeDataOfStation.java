package cn.jy.stork.biz.pojo;

import java.math.BigDecimal;

import cn.jy.stork.util.AJsonedObject;

/**
 * 一个站的实时数据
 * 
 * @author jsh
 *
 */
public class RealtimeDataOfStation extends AJsonedObject {
	private int stationId;
	private BigDecimal t;// 温度
	private BigDecimal s;// 流速
	private BigDecimal p;// 总磷
	private BigDecimal cond;// 电导
	private BigDecimal ph;
	private BigDecimal oxy;// 溶氧
	private BigDecimal cod;
	private BigDecimal turb;// 浊度
	private BigDecimal nh3;// 氨氮
	private Integer te;
	private Integer se;
	private Integer pe;
	private Integer conde;
	private Integer phe;
	private Integer oxye;
	private Integer code;
	private Integer turbe;
	private Integer nh3e;

	public RealtimeDataOfStation() {
	}

	public RealtimeDataOfStation(int stationId) {
		this.stationId = stationId;
	}

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public BigDecimal getT() {
		return t;
	}

	public void setT(BigDecimal t) {
		this.t = t.setScale(1, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getS() {
		return s;
	}

	public void setS(BigDecimal s) {
		this.s = s.setScale(1, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getP() {
		return p;
	}

	public void setP(BigDecimal p) {
		this.p = p.setScale(1, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getCond() {
		return cond;
	}

	public void setCond(BigDecimal cond) {
		this.cond = cond.setScale(1, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getPh() {
		return ph;
	}

	public void setPh(BigDecimal ph) {
		this.ph = ph.setScale(1, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getOxy() {
		return oxy;
	}

	public void setOxy(BigDecimal oxy) {
		this.oxy = oxy.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getCod() {
		return cod;
	}

	public void setCod(BigDecimal cod) {
		this.cod = cod.setScale(0, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getTurb() {
		return turb;
	}

	public void setTurb(BigDecimal turb) {
		this.turb = turb.setScale(0, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getNh3() {
		return nh3;
	}

	public void setNh3(BigDecimal nh3) {
		this.nh3 = nh3.setScale(0, BigDecimal.ROUND_HALF_UP);
	}

	public Integer getTe() {
		return te;
	}

	public void setTe(Integer te) {
		this.te = te;
	}

	public Integer getSe() {
		return se;
	}

	public void setSe(Integer se) {
		this.se = se;
	}

	public Integer getPe() {
		return pe;
	}

	public void setPe(Integer pe) {
		this.pe = pe;
	}

	public Integer getConde() {
		return conde;
	}

	public void setConde(Integer conde) {
		this.conde = conde;
	}

	public Integer getPhe() {
		return phe;
	}

	public void setPhe(Integer phe) {
		this.phe = phe;
	}

	public Integer getOxye() {
		return oxye;
	}

	public void setOxye(Integer oxye) {
		this.oxye = oxye;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getTurbe() {
		return turbe;
	}

	public void setTurbe(Integer turbe) {
		this.turbe = turbe;
	}

	public Integer getNh3e() {
		return nh3e;
	}

	public void setNh3e(Integer nh3e) {
		this.nh3e = nh3e;
	}

}
