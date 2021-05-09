package cn.jy.stork.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.jy.stork.biz.pojo.DetectProcessDispatchPojo;
import cn.jy.stork.biz.pojo.DetectReportPojo;
import cn.jy.stork.biz.service.DetectProcessService;
import cn.jy.stork.dao.DetectReport;
import cn.jy.stork.dao.DetectStaff;
import cn.jy.stork.web.Resp;

@RestController
@RequestMapping("detect-process")
public class DetectProcessController {
	@Autowired
	DetectProcessService detectProcessService;

	/**
	 * 派发一个任务
	 * 
	 */
	@PostMapping("")
	public Resp add(@RequestBody DetectProcessDispatchPojo pojo) {
		return Resp.okWithData(detectProcessService.dispatch(pojo));
	}

	/**
	 * 根据所有送检
	 */
	@GetMapping(value = "")
	public Resp queryAll() {
		return Resp.okWithData(detectProcessService.finalAll());
	}

	/**
	 * 根据人员id查询他的送样任务
	 */
	@GetMapping(value = "{deliveryId}", params = { "by=delivery" })
	public Resp queryByDelivery(@PathVariable Integer deliveryId) {
		return Resp.okWithData(detectProcessService.findByDelivery(deliveryId));
	}
	
	/**
	 * 根据人员id查询他的送样任务
	 */
	@GetMapping(value = "{deliveryId}", params = { "by=test" })
	public Resp queryByTes(@PathVariable Integer deliveryId) {
		return Resp.okWithData(detectProcessService.findByTestId(deliveryId));
	}
	
	/**
	 * 根据任务id确认取样
	 */
	@PostMapping(value = "{detectProcessId}", params = { "by=sample" })
	public Resp sampleSuccess(@PathVariable Integer detectProcessId ) {
		detectProcessService.sampleSuccess(detectProcessId);
		return Resp.ok();
	}
	
	/**
	 * 根据任务id确认送样
	 */
	@PostMapping(value = "{detectProcessId}", params = { "by=delivery" })
	public Resp deliverySuccess(@PathVariable("detectProcessId") Integer detectProcessId ,@RequestBody DetectStaff detectStaff) {
		detectProcessService.deliverySuccess(detectProcessId,detectStaff);
		return Resp.ok();
	}

	/**
	 * 根据任务id确认收货
	 */
	@PostMapping(value = "{detectProcessId}", params = { "by=accept" })
	public Resp acceptSuccess(@PathVariable("detectProcessId") Integer detectProcessId ) {
		detectProcessService.acceptSuccess(detectProcessId);
		return Resp.ok();
	}
	
	/**
	 * 根据任务id检验成功
	 */
	@PostMapping(value = "{detectProcessId}", params = { "by=chargeComplete" })
	public Resp chargeComplete(@PathVariable("detectProcessId") Integer detectProcessId ,@RequestBody DetectReportPojo pojo) {
		detectProcessService.chargeComplete(detectProcessId,pojo);
		return Resp.ok();
	}
}
