package cn.jy.stork.web.controller;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.jy.stork.dao.AlarmDao;
import cn.jy.stork.web.Resp;

@RestController
@RequestMapping("alarm")
public class AlarmController {
	@Autowired
	AlarmDao alarmDao;

	/**
	 * 综合条件查询
	 * 
	 * @param station
	 * @param from
	 * @param to
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@GetMapping("")
	public Resp query(@RequestParam(required = false) Integer station, @RequestParam(required = false) Long from,
			@RequestParam(required = false) Long to,
			@RequestParam(required = false, defaultValue = "0") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "15") Integer pageSize) {
		if (to == null) {
			to = System.currentTimeMillis();// to如果未输入，则取当前
		}
		Date toDate = new Date(to);
		Date fromDate = null;
		if (from == null || from >= to) {// 如果from未输入或输入了但大于to，则取7天前（以to为基准）
			Calendar b7d = Calendar.getInstance();
			b7d.setTimeInMillis(to);
			b7d.add(Calendar.DAY_OF_YEAR, -7);
			fromDate = b7d.getTime();
		} else {
			fromDate = new Date(from);
		}
		return Resp.okWithData(alarmDao.select(station, fromDate, toDate, pageIndex, pageSize));
	}
}
