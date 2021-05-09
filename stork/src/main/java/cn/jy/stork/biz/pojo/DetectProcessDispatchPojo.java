package cn.jy.stork.biz.pojo;

public class DetectProcessDispatchPojo {
	private Integer record;// 采样记录id
	private Integer delivery;// 送样人id
	private Integer dispatcher;// 任务派发人id

	public Integer getRecord() {
		return record;
	}

	public void setRecord(Integer record) {
		this.record = record;
	}

	public Integer getDelivery() {
		return delivery;
	}

	public void setDelivery(Integer delivery) {
		this.delivery = delivery;
	}

	public Integer getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(Integer dispatcher) {
		this.dispatcher = dispatcher;
	}

}
