package cn.jy.stork.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.jy.stork.dao.ThresholdDao;
import cn.jy.stork.web.Resp;

@RestController
@RequestMapping("threshold")
public class ThresholdController {

	@Autowired
	ThresholdDao thresholdDao;

	@GetMapping("")
	public Resp all() {
		return Resp.okWithData(thresholdDao.findAll());
	}

	@PostMapping(value = "{id}", params = { "op=modify" })
	public Resp update(@PathVariable Integer id, @RequestBody ThresholdModifyPojo pojo) {
		thresholdDao.update(id, pojo.getBottom(), pojo.getTop());
		return Resp.ok();
	}
}
