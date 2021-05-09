package cn.jy.stork.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.jy.stork.biz.service.RealtimeDataService;
import cn.jy.stork.dao.RealtimeData;
import cn.jy.stork.util.QueryDataHelper;
import cn.jy.stork.web.Resp;

/**
 * 实时数据controller
 * 
 * @author jsh
 *
 */
@RestController
@RequestMapping("realtime")
public class RealtimeController {

	@Autowired
	RealtimeDataService realtimeDataService;

	/**
	 * 取指定水站的最新数据
	 * 
	 * @param stationId 水站id。正常水站id一定是非负数，根据实际的值查询相应水站的最新数据即可，但如果id是负值，则查询所有水站的最新数据。
	 * @return
	 */
	@GetMapping(value = "{stationId}", params = { "about=newest", "by=station" })
	public Resp<List<RealtimeData>> queryNewest(@PathVariable int stationId) {
		return Resp.okWithData(realtimeDataService.findNewestByStation(stationId));
	}

	/**
	 * 取所有水站的实时数据（历史记录）
	 * 
	 * @param from
	 * @param to
	 * @param asc
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 */
	@GetMapping(value = "", params = { "about=history" })
	public Resp<List<RealtimeData>> query(@RequestParam(required = false) Long from,
			@RequestParam(required = false) Long to, @RequestParam(required = false, defaultValue = "true") boolean asc,
			@RequestParam(required = false) Integer pageSize,
			@RequestParam(required = false, defaultValue = "0") Integer pageIndex) {
		QueryDataHelper qdh = new QueryDataHelper(from, to);
		return Resp.okWithData(realtimeDataService.find(null, qdh.from(), qdh.to(), pageSize, pageIndex, asc));
	}

	/**
	 * 取某个水站的实时数据（历史记录）
	 * 
	 * @param stationId 水站id，必填
	 * @param from
	 * @param to
	 * @param asc
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 */
	@GetMapping(value = "{stationId}", params = { "by=station", "about=history" })
	public Resp<List<RealtimeData>> queryByStation(@PathVariable Integer stationId,
			@RequestParam(required = false) Long from, @RequestParam(required = false) Long to,
			@RequestParam(required = false, defaultValue = "true") boolean asc,
			@RequestParam(required = false) Integer pageSize,
			@RequestParam(required = false, defaultValue = "0") Integer pageIndex) {
		QueryDataHelper qdh = new QueryDataHelper(from, to);
		return Resp.okWithData(realtimeDataService.find(stationId, qdh.from(), qdh.to(), pageSize, pageIndex, asc));
	}

}
