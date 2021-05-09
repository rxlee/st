package cn.jy.stork.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.jy.stork.biz.service.DischargeService;
import cn.jy.stork.dao.RegionDao;
import cn.jy.stork.dao.SampleRecord;
import cn.jy.stork.dao.SampleRecordDao;
import cn.jy.stork.dao.StationDao;
import cn.jy.stork.exception.DataNotFoundException;
import cn.jy.stork.web.Resp;

@RestController
@RequestMapping("sample-record")
public class SampleRecordController {

	@Autowired
	SampleRecordDao sampleRecordDao;
	@Autowired
	DischargeService dischargeService;
	@Autowired
	RegionDao regionDao;
	@Autowired
	StationDao stationDao;

	/**
	 * 查询所有的排放采样记录
	 * 
	 * @param regionId
	 * @return
	 */
	@GetMapping(value = "")
	public Resp<List<SampleRecord>> queryAll() {
		return Resp.okWithData(sampleRecordDao.select());
	}

	/**
	 * 查询某辖区所有的采样记录（未送检和已送检的都包括）
	 * 
	 * @param regionId
	 * @return
	 */
	@GetMapping(value = "{regionId}", params = { "by=region" })
	public Resp<List<SampleRecord>> queryAllInRegion(@PathVariable("regionId") Integer regionId) {
		return Resp.okWithData(sampleRecordDao.selectUndetectedByRegionId(regionId));
	}

	/**
	 * 查询某辖区未送检的采样记录
	 * 
	 * @param regionId
	 * @return
	 */
	@GetMapping(value = "{regionId}", params = { "by=region", "about=undetected" })
	public Resp<List<SampleRecord>> queryUndetected(@PathVariable("regionId") Integer regionId) {
		return Resp.okWithData(sampleRecordDao.selectUndetectedByRegionId(regionId));
	}

	/**
	 * 查询某辖区已送检的采样记录
	 * 
	 * @param regionId
	 * @return
	 */
	@GetMapping(value = "{regionId}", params = { "by=region", "about=detected" })
	public Resp<List<SampleRecord>> queryDetected(@PathVariable("regionId") Integer regionId) {
		return Resp.okWithData(sampleRecordDao.selectDetectedByRegionId(regionId));
	}

	/**
	 * 通过数据记录id查询
	 * 
	 * @param
	 * @return
	 */
	@GetMapping(value = "{id}")
	public Resp<SampleRecord> queryById(@PathVariable("id") int id) {
		return Resp.okWithData(sampleRecordDao.selectById(id));
	}

	/**
	 * 模拟接口：添加一条（实际中不会从controller接口填加，而是后台自动生成，这个接口只是用于测试）
	 * 
	 * @param pojo
	 * @return
	 */
	@PostMapping(value = "", params = { "mode=simulate" })
	public Resp<SampleRecord> simulate(@RequestParam("station") Integer stationId) {
		try {
			return Resp.okWithData(dischargeService.start(stationId));
		} catch (DataNotFoundException e) {
			return Resp.fail(e.getMessage());
		}

	}

}
