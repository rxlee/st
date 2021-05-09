package cn.jy.stork.dao;

public class Threshold {

	/**
	 * 水温
	 */
	public static final String TEMP = "temp";
	/**
	 * 流速
	 */
	public static final String STREAMSPEED = "streamspeed";
	/**
	 * 酸碱度
	 */
	public static final String PH = "ph";
	/**
	 * 氨氮
	 */
	public static final String NH3 = "nh3";
	/**
	 * 总磷
	 */
	public static final String PHOS = "phosphorus";
	/**
	 * COD
	 */
	public static final String COD = "cod";
	/**
	 * 溶氧
	 */
	public static final String OXY = "oxy";
	/**
	 * 电导
	 */
	public static final String COND = "cond";
	/**
	 * 浊度
	 */
	public static final String TURB = "turb";
	private Integer id;
	private Integer stationId;
	private String indicator;
	private Float bottom;
	private Float top;

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public Float getBottom() {
		return bottom;
	}

	public void setBottom(Float bottom) {
		this.bottom = bottom;
	}

	public Float getTop() {
		return top;
	}

	public void setTop(Float top) {
		this.top = top;
	}

	public Integer getStationId() {
		return stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
