package cn.jy.stork.biz.pojo;

/**
 * 水站定位信息修改的对象，通常用于前端向后台传递用户输入
 * 
 * @author jsh
 *
 */
public class StationLocateModifyPojo {
	private Integer station;// 水站的id
	private Float lng;
	private Float lat;

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

	public Integer getStation() {
		return station;
	}

	public void setStation(Integer station) {
		this.station = station;
	}

}
