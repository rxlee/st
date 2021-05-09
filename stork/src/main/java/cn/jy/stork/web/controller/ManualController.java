package cn.jy.stork.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.jy.stork.hardware.event.ManualSamplingEvent;
import cn.jy.stork.web.Resp;

/**
 * 手动采样控制
 * 
 * @author jsh
 *
 */
@RestController
@RequestMapping("manual")
public class ManualController {

	@Autowired
	ApplicationContext dispatcher;
	private Long lastManualTime = 0L;

	@PostMapping(value = "on")
	public Resp on() {
		if (System.currentTimeMillis() - lastManualTime < 1000L * 60 * 5) {
			return Resp.fail("不允许5分钟之内重复手动采样");
		}
		lastManualTime = System.currentTimeMillis();
		dispatcher.publishEvent(new ManualSamplingEvent(this, true));
		return Resp.ok();
	}

	@PostMapping(value = "off")
	public Resp off() {
		dispatcher.publishEvent(new ManualSamplingEvent(this, false));
		return Resp.ok();
	}
}
