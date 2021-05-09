package cn.jy.stork.dao;

import java.util.Date;

import cn.jy.stork.util.AJsonedObject;

/**
 * 数据库对象：水站
 * 
 * @author jsh
 *
 */
public class Station extends AJsonedObject {

	private Integer id;
	private Integer sn;
	private String name;
	private String code;
	private String alias;
	private String memo;
	private Date maintainTime;// 维护结束的时间，在此之前有些操作会暂停
	private Float lng;// 经度
	private Float lat;// 纬度
	private Integer rid;// 所属辖区id
	private String rname;// 所属辖区名称
	private String vsn;
	private String vtoken;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getMaintainTime() {
		return maintainTime;
	}

	public void setMaintainTime(Date maintainTime) {
		this.maintainTime = maintainTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Float getLng() {
		return lng;
	}

	public void setLng(Float lng) {
		this.lng = lng;
	}

	public Float getLat() {
		return lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
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

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
