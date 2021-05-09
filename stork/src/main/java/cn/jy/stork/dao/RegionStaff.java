package cn.jy.stork.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.jy.stork.util.AJsonedObject;
import cn.jy.stork.util.StorkConst;

//辖区人员
public class RegionStaff extends AJsonedObject {
	private Integer id;
	private Integer rid;
	private Integer uid;
	private String uname;
	private String uphone;
	private Integer utype;

	@JsonIgnore
	public String type2role() {
		switch (utype) {
		case StorkConst.RegionStaffType.RIVER_CHIEF:
			return StorkConst.RegionStaffRole.RIVER_CHIEF;
		case StorkConst.RegionStaffType.ADMINISTRA:
			return StorkConst.RegionStaffRole.ADMINISTRA;
		case StorkConst.RegionStaffType.PATROL:
			return StorkConst.RegionStaffRole.PATROL;
		case StorkConst.RegionStaffType.OFFICER:
			return StorkConst.RegionStaffRole.OFFICER;
		default:
			return null;
		}
	}

	public final Integer getId() {
		return id;
	}

	public final void setId(Integer id) {
		this.id = id;
	}

	public final Integer getRid() {
		return rid;
	}

	public final void setRid(Integer rid) {
		this.rid = rid;
	}

	public final Integer getUid() {
		return uid;
	}

	public final void setUid(Integer uid) {
		this.uid = uid;
	}

	public final String getUname() {
		return uname;
	}

	public final void setUname(String uname) {
		this.uname = uname;
	}

	public final String getUphone() {
		return uphone;
	}

	public final void setUphone(String uphone) {
		this.uphone = uphone;
	}

	public final Integer getUtype() {
		return utype;
	}

	public final void setUtype(Integer utype) {
		this.utype = utype;
	}

}
