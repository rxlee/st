package cn.jy.stork.dao;

/**
 * 这是个虚拟PO，没有表对应，是从tb_realtime_streamspeed中汇总各个站的流速极值
 * 
 * @author jsh
 *
 */
public class StreamspeedRange {
	private Float bottom, top;
	private Integer sid;

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

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

}
