package cn.jy.stork.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.jy.stork.biz.service.StationService;
import cn.jy.stork.web.Resp;

@RestController
@RequestMapping("lock")
public class LockController {

	@Autowired
	StationService stationService;

	/**
	 * 开指定水站的锁（柜门）
	 * 
	 * @param station 水站sn
	 * @return
	 */
	@PostMapping(value = "{station}", params = { "op=release" })
	Resp release(@PathVariable Integer station) {
		stationService.lockRelease(station);
		return null;
	}
}
