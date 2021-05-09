package cn.jy.stork.dao;

import java.util.Date;

import cn.jy.stork.util.AJsonedObject;

/**
 * 数据库对象：水站
 * 
 * @author jsh
 *
 */
public class StationStatus extends AJsonedObject {
	private Integer id;
	private Integer ssn;
	private Date recentActive;// 最近活动时间
	private Integer storage1;
	private Integer storage2;
	private Integer storage3;
	private Integer storage4;
	private Integer storage5;
	private Integer storage6;
	private Integer lockOpen;// 智能锁
	private Integer generalOpen;// 多参数
	private Integer phosphorusOpen;// 总磷
	private Integer streamSpeedOpen;// 流速
	private Integer pumpOpen;// 泵

	public StationStatus() {
	}

	public StationStatus(int ssn) {
		this.ssn = ssn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getRecentActive() {
		return recentActive;
	}

	public void setRecentActive(Date recentActive) {
		this.recentActive = recentActive;
	}

	public Integer getStorage1() {
		return storage1;
	}

	public void setStorage1(Integer storage1) {
		this.storage1 = storage1;
	}

	public Integer getStorage2() {
		return storage2;
	}

	public void setStorage2(Integer storage2) {
		this.storage2 = storage2;
	}

	public Integer getStorage3() {
		return storage3;
	}

	public void setStorage3(Integer storage3) {
		this.storage3 = storage3;
	}

	public Integer getStorage4() {
		return storage4;
	}

	public void setStorage4(Integer storage4) {
		this.storage4 = storage4;
	}

	public Integer getStorage5() {
		return storage5;
	}

	public void setStorage5(Integer storage5) {
		this.storage5 = storage5;
	}

	public Integer getStorage6() {
		return storage6;
	}

	public void setStorage6(Integer storage6) {
		this.storage6 = storage6;
	}

	public Integer getLockOpen() {
		return lockOpen;
	}

	public void setLockOpen(Integer lockOpen) {
		this.lockOpen = lockOpen;
	}

	public Integer getGeneralOpen() {
		return generalOpen;
	}

	public void setGeneralOpen(Integer generalOpen) {
		this.generalOpen = generalOpen;
	}

	public Integer getPhosphorusOpen() {
		return phosphorusOpen;
	}

	public void setPhosphorusOpen(Integer phosphorusOpen) {
		this.phosphorusOpen = phosphorusOpen;
	}

	public Integer getStreamSpeedOpen() {
		return streamSpeedOpen;
	}

	public void setStreamSpeedOpen(Integer streamSpeedOpen) {
		this.streamSpeedOpen = streamSpeedOpen;
	}

	public Integer getPumpOpen() {
		return pumpOpen;
	}

	public void setPumpOpen(Integer pumpOpen) {
		this.pumpOpen = pumpOpen;
	}

	public Integer getSsn() {
		return ssn;
	}

	public void setSsn(Integer ssn) {
		this.ssn = ssn;
	}

}
