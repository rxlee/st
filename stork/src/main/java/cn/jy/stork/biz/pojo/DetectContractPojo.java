package cn.jy.stork.biz.pojo;

//合同传入对象
public class DetectContractPojo {
	private Integer corp;
	private Integer region;
	private Long from;
	private Long to;
	private Integer delivery;// 合同里第一个送样服务人员，更多人员可以由这个人来加

	public Long getFrom() {
		return from;
	}

	public void setFrom(Long from) {
		this.from = from;
	}

	public Long getTo() {
		return to;
	}

	public void setTo(Long to) {
		this.to = to;
	}

	public Integer getCorp() {
		return corp;
	}

	public void setCorp(Integer corp) {
		this.corp = corp;
	}

	public Integer getRegion() {
		return region;
	}

	public void setRegion(Integer region) {
		this.region = region;
	}

	public Integer getDelivery() {
		return delivery;
	}

	public void setDelivery(Integer delivery) {
		this.delivery = delivery;
	}

}
