package cn.jy.stork.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.jy.stork.biz.pojo.InspectPojo;
import cn.jy.stork.biz.service.InspectService;
import cn.jy.stork.dao.Inspect;
import cn.jy.stork.exception.DataNotFoundException;
import cn.jy.stork.web.Resp;

@RestController
@RequestMapping("inspect")
public class InspectController {
	
	@Autowired
	InspectService inspectService;
	/**
	 * 添加一个巡查情况
	 * 
	 * @param pojo
	 * @return
	 */
	@PostMapping("")
	public Resp<Inspect> addRegion(@RequestBody InspectPojo pojo) {
		return Resp.okWithData(inspectService.addInspect(pojo));
	}
	
	/**
	 * 通过辖区id查巡查情况
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "{regionId}", params = { "about=outline" })
	public Resp<List<Inspect>> getInspectByRgionId(@PathVariable Integer regionId) throws DataNotFoundException {
		return Resp.okWithData(inspectService.findByRegionId(regionId));
	}
	/**
	 * 河长审批
	 * @param id
	 * @param pojo
	 * @return
	 */
	@PostMapping(value="{id}",params= {"op=approve"})
	public Resp<Object> approveInspect(@PathVariable("id") int id,@RequestBody InspectPojo pojo){
		
		return Resp.okWithData(inspectService.approveInspect(id,pojo));
	}
	
	/**
	 * 审批结果
	 * @param id
	 * @param pojo
	 * @return
	 */
	@PostMapping(value="{id}",params= {"op=result"})
	public Resp<Object> inspectResult(@PathVariable("id") int id,@RequestBody InspectPojo pojo){
		return Resp.okWithData(inspectService.inspectResult(id,pojo));
	}
}
