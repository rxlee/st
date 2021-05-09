package cn.jy.stork.dao;

import java.util.Date;

import cn.jy.stork.util.AJsonedObject;

public class DetectProcess extends AJsonedObject {
	/**
	 * 进度：派发待取样。
	 */
	public static final int STEP_GETTING = 0;
	/**
	 * 进度：已取样送样中。
	 */
	public static final int STEP_SENDING = 10;
	/**
	 * 进度：已送达检测中。
	 */
	public static final int STEP_DETECTING = 20;
	/**
	 * 进度：检测完成。
	 */
	public static final int STEP_COMPLETED = 30;
	private Integer id;
	private String code;

	private Integer sampleRid; // 样品辖区
	private String sampleRname;
	private String sampleRalias;
	private Integer sampleSid;// 样品水站
	private String sampleSname;
	private String sampleSalias;
	private Date sampleTime;// 采样时间
	private Integer sampleBottle;

	private Integer dispatchUid;// 任务派发人
	private String dispatchUname;
	private Date dispatchTime;

	private Integer deliveryUid;// 送样人
	private String deliveryUname;
	private Date deliveryTime;// 开始送样时间（取样时间）
	private Date deliveryArrivedTime;// 送达时间

	private Integer chargeCid; // 检测机构
	private String chargeCname;
	private String chargeCalias;
	private Integer chargeUid;// 检验员
	private String chargeUname;
	private Date chargeAcceptTime;// 样品接收时间
	private Date chargeCompleteTime;// 检测完成时间

	private Integer chargexCid; // 非标检测机构
	private String chargexCname;
	private String chargexCalias;
	private Integer chargexUid;// 检验员
	private String chargexUname;
	private Date chargexAcceptTime;// 样品接收时间
	private Date chargexCompleteTime;// 检测完成时间

	private Integer step;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSampleRname() {
		return sampleRname;
	}

	public void setSampleRname(String sampleRname) {
		this.sampleRname = sampleRname;
	}

	public String getSampleRalias() {
		return sampleRalias;
	}

	public void setSampleRalias(String sampleRalias) {
		this.sampleRalias = sampleRalias;
	}

	public String getSampleSname() {
		return sampleSname;
	}

	public void setSampleSname(String sampleSname) {
		this.sampleSname = sampleSname;
	}

	public String getSampleSalias() {
		return sampleSalias;
	}

	public void setSampleSalias(String sampleSalias) {
		this.sampleSalias = sampleSalias;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getSampleRid() {
		return sampleRid;
	}

	public void setSampleRid(Integer sampleRid) {
		this.sampleRid = sampleRid;
	}

	public Integer getSampleSid() {
		return sampleSid;
	}

	public void setSampleSid(Integer sampleSid) {
		this.sampleSid = sampleSid;
	}

	public Date getSampleTime() {
		return sampleTime;
	}

	public void setSampleTime(Date sampleTime) {
		this.sampleTime = sampleTime;
	}

	public Integer getDispatchUid() {
		return dispatchUid;
	}

	public void setDispatchUid(Integer dispatchUid) {
		this.dispatchUid = dispatchUid;
	}

	public String getDispatchUname() {
		return dispatchUname;
	}

	public void setDispatchUname(String dispatchUname) {
		this.dispatchUname = dispatchUname;
	}

	public Date getDispatchTime() {
		return dispatchTime;
	}

	public void setDispatchTime(Date dispatchTime) {
		this.dispatchTime = dispatchTime;
	}

	public Integer getDeliveryUid() {
		return deliveryUid;
	}

	public void setDeliveryUid(Integer deliveryUid) {
		this.deliveryUid = deliveryUid;
	}

	public String getDeliveryUname() {
		return deliveryUname;
	}

	public void setDeliveryUname(String deliveryUname) {
		this.deliveryUname = deliveryUname;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Date getDeliveryArrivedTime() {
		return deliveryArrivedTime;
	}

	public void setDeliveryArrivedTime(Date deliveryArrivedTime) {
		this.deliveryArrivedTime = deliveryArrivedTime;
	}

	public Integer getChargeCid() {
		return chargeCid;
	}

	public void setChargeCid(Integer chargeCid) {
		this.chargeCid = chargeCid;
	}

	public String getChargeCname() {
		return chargeCname;
	}

	public void setChargeCname(String chargeCname) {
		this.chargeCname = chargeCname;
	}

	public String getChargeCalias() {
		return chargeCalias;
	}

	public void setChargeCalias(String chargeCalias) {
		this.chargeCalias = chargeCalias;
	}

	public Integer getChargeUid() {
		return chargeUid;
	}

	public void setChargeUid(Integer chargeUid) {
		this.chargeUid = chargeUid;
	}

	public String getChargeUname() {
		return chargeUname;
	}

	public void setChargeUname(String chargeUname) {
		this.chargeUname = chargeUname;
	}

	public Date getChargeAcceptTime() {
		return chargeAcceptTime;
	}

	public void setChargeAcceptTime(Date chargeAcceptTime) {
		this.chargeAcceptTime = chargeAcceptTime;
	}

	public Date getChargeCompleteTime() {
		return chargeCompleteTime;
	}

	public void setChargeCompleteTime(Date chargeCompleteTime) {
		this.chargeCompleteTime = chargeCompleteTime;
	}

	public Integer getChargexCid() {
		return chargexCid;
	}

	public void setChargexCid(Integer chargexCid) {
		this.chargexCid = chargexCid;
	}

	public String getChargexCname() {
		return chargexCname;
	}

	public void setChargexCname(String chargexCname) {
		this.chargexCname = chargexCname;
	}

	public String getChargexCalias() {
		return chargexCalias;
	}

	public void setChargexCalias(String chargexCalias) {
		this.chargexCalias = chargexCalias;
	}

	public Integer getChargexUid() {
		return chargexUid;
	}

	public void setChargexUid(Integer chargexUid) {
		this.chargexUid = chargexUid;
	}

	public String getChargexUname() {
		return chargexUname;
	}

	public void setChargexUname(String chargexUname) {
		this.chargexUname = chargexUname;
	}

	public Date getChargexAcceptTime() {
		return chargexAcceptTime;
	}

	public void setChargexAcceptTime(Date chargexAcceptTime) {
		this.chargexAcceptTime = chargexAcceptTime;
	}

	public Date getChargexCompleteTime() {
		return chargexCompleteTime;
	}

	public void setChargexCompleteTime(Date chargexCompleteTime) {
		this.chargexCompleteTime = chargexCompleteTime;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public Integer getSampleBottle() {
		return sampleBottle;
	}

	public void setSampleBottle(Integer sampleBottle) {
		this.sampleBottle = sampleBottle;
	}

}
