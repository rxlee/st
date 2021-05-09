package cn.jy.stork.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.jy.stork.biz.pojo.StationLocateModifyPojo;
import cn.jy.stork.biz.pojo.StationProfilePojo;
import cn.jy.stork.biz.service.StationService;
import cn.jy.stork.dao.Region;
import cn.jy.stork.exception.DataDuplicatedException;
import cn.jy.stork.exception.DataNotFoundException;
import cn.jy.stork.web.Resp;

@RestController
@RequestMapping("station")
public class StationController {

	@Autowired
	StationService stationService;

	/**
	 * 添加一个水站
	 * 
	 * @param pojo
	 * @return
	 */
	@PostMapping("")
	public Resp StationAdd(@RequestBody StationProfilePojo pojo) {
		try {
			return Resp.okWithData(stationService.add(pojo));
		} catch (DataDuplicatedException | DataNotFoundException e) {
			// sn不可重复
			return Resp.fail(e.getMessage());
		}
	}

	/**
	 * 查询水站
	 * 
	 * @param
	 * @return
	 */
	@GetMapping("")
	public Resp query() {
		return Resp.okWithData(stationService.all());
	}

	/**
	 * 通过id查询水站
	 * 
	 * @param
	 * @return
	 */
	@GetMapping(value = "{id}")
	public Resp queryById(@PathVariable("id") int id) {
		try {
			return Resp.okWithData(stationService.findById(id));
		} catch (DataNotFoundException e) {
			return Resp.fail(e.getMessage());
		}
	}

	/**
	 * 通过辖区id查询水站
	 * 
	 * @param
	 * @return
	 */
	@GetMapping(value = "{regionId}", params = { "by=region" })
	public Resp queryByRegionId(@PathVariable("regionId") int regionId) {
		return Resp.okWithData(stationService.findByRegionId(regionId));
	}

	/**
	 * 通过id删除水站
	 * 
	 * @param
	 * @return
	 */
	@PostMapping(value = "{id}", params = { "op=remove" })
	public Resp removeOne(@PathVariable("id") int id) {
		stationService.removeById(id);
		return Resp.ok();
	}

	/**
	 * 通过id把水站改为维护状态或解除维护状态
	 * 
	 * @param id    水站id
	 * @param start true/false 是否进入维护状态，不输入则表示进入维护
	 * @return
	 */
	@PostMapping(value = "{id}", params = { "op=maintain" })
	public Resp maintain(@PathVariable("id") int id,
			@RequestParam(name = "start", required = false, defaultValue = "true") Boolean start) {
		stationService.maintain(id, start);
		return Resp.ok();
	}

	/**
	 * 修改水站基本信息
	 * 
	 * @param Station
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping(value = "{id}", params = { "op=modify", "about=profile" })
	public Resp modifyProfile(@PathVariable("id") int id, @RequestBody StationProfilePojo pojo) {
		try {
			return Resp.okWithData(stationService.modifyProfile(id, pojo));
		} catch (DataDuplicatedException e) {
			return Resp.fail(e.getMessage());
		} catch (DataNotFoundException e) {
			return Resp.fail(e.getMessage());
		}

	}

	/**
	 * 修改水站地理位置
	 * 
	 * @param id
	 * @param pojo
	 * @return
	 */
	@PostMapping(value = "{id}", params = { "op=modify", "about=locate" })
	public Resp locate(@PathVariable("id") int id, @RequestBody StationLocateModifyPojo pojo) {
		stationService.locate(id, pojo);
		return Resp.ok();
	}

	/**
	 * 批量修改水站地理位置
	 * 
	 * @param id
	 * @param pojo
	 * @return
	 */
	@PostMapping(value = "", params = { "op=modify", "about=locate" })
	public Resp locateAll(@RequestBody List<StationLocateModifyPojo> pojo) {
		stationService.locateBatch(pojo);
		return Resp.ok();
	}
	
	/**
	 * 修改水站辖区
	 * 
	 * @param id
	 * @param pojo
	 * @return
	 * @throws DataNotFoundException 
	 */
	@PostMapping(value = "{id}", params = { "op=modify", "about=region" })
	public Resp updateRegion(@PathVariable("id") int id, @RequestBody Region pojo) throws DataNotFoundException {
		stationService.updateRegion(id, pojo);
		return Resp.ok();
	}

}
