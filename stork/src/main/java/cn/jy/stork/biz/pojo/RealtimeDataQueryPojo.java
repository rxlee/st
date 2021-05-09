package cn.jy.stork.biz.pojo;

import cn.jy.stork.util.APageablePojo;

public class RealtimeDataQueryPojo extends APageablePojo {
	private Long time;// 数据采集的时间

	public final Long getTime() {
		return time;
	}

	public final void setTime(Long time) {
		this.time = time;
	}

}
