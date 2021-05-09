package cn.jy.stork.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.jy.stork.biz.pojo.RegionPojo;
import cn.jy.stork.biz.pojo.RegionStaffPojo;
import cn.jy.stork.biz.service.RegionService;
import cn.jy.stork.biz.service.RegionStaffService;
import cn.jy.stork.dao.Region;
import cn.jy.stork.dao.RegionStaff;
import cn.jy.stork.dao.RegionWithOutline;
import cn.jy.stork.exception.DataNotFoundException;
import cn.jy.stork.web.Resp;

@RestController
@RequestMapping("region")
public class RegionController {
	@Autowired
	RegionService regionService;
	@Autowired
	RegionStaffService regionStaffService;

	/**
	 * 通过id查询辖区
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("{id}")
	public Resp<Region> getRegionDetail(@PathVariable Integer id) {
		try {
			final Region r = regionService.findRegionById(id);
			return Resp.okWithData(r);
		} catch (DataNotFoundException e) {
			return Resp.fail(e.getMessage());
		}
	}

	/**
	 * 通过id查询辖区地理范围
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "{regionId}", params = { "about=outline" })
	public Resp<RegionWithOutline> getRegionOutline(@PathVariable Integer regionId) {
		try {
			return Resp.okWithData(regionService.findOutlineByRegionId(regionId));
		} catch (DataNotFoundException e) {
			return Resp.fail(e.getMessage());
		}
	}

	/**
	 * 通过人员id（例如河长）查询所在辖区（目前只支持一个）
	 * 
	 * @param id
	 * @return
	 */

	@GetMapping(value = "{staffId}", params = { "by=staff" })
	public Resp<Region> getRegionByStaff(@PathVariable Integer staffId) {
		try {
			return Resp.okWithData(regionService.findRegionByStaffId(staffId));
		} catch (DataNotFoundException e) {
			return Resp.fail(e.getMessage());
		}
	}

	/**
	 * 查询辖区内所有人员
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("{regionId}/staff")
	public Resp<List<RegionStaff>> queryStaffsByRegion(@PathVariable Integer regionId) {
		return Resp.okWithData(regionStaffService.findStaffsByRegionId(regionId));
	}

	/**
	 * 查询所有辖区
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("")
	public Resp<List<Region>> queryAllRegions() {
		return Resp.okWithData(regionService.findAllRegions());
	}

	/**
	 * 添加一个辖区
	 * 
	 * @param pojo
	 * @return
	 */
	@PostMapping("")
	public Resp<Region> addRegion(@RequestBody RegionPojo pojo) {
		return Resp.okWithData(regionService.addRegion(pojo));
	}

	/**
	 * 修改辖区基本信息
	 * 
	 * @param id
	 * @param pojo
	 * @return
	 */
	@PostMapping(value = "{id}", params = { "op=modify", "about=profile" })
	public Resp<Object> modifyRegionProfile(@PathVariable("id") int id, @RequestBody RegionPojo pojo) {
		regionService.modifyRegionProfile(id, pojo.getName(), pojo.getAlias());
		return Resp.ok();
	}

	/**
	 * 修改辖区地理范围
	 * 
	 * @param id
	 * @param pojo
	 * @return
	 */
	@PostMapping(value = "{id}", params = { "op=modify", "about=outline" })
	public Resp<Object> modifyRegionOutline(@PathVariable("id") int id, @RequestBody RegionPojo pojo) {
		regionService.modifyRegionOutline(id, pojo.getOutline());
		return Resp.ok();
	}

	/**
	 * 添加辖区人员
	 * 
	 * @param
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping("{regionId}/staff")
	public Resp<RegionStaff> addStaffInRegion(@PathVariable("regionId") int regionId, @RequestBody RegionStaffPojo pojo) {
		// TODO 注意，这个接口被改了，前端注意修改，原来是@PostMapping("staff/{id}")
		return Resp.okWithData(regionStaffService.addStaff(regionId, pojo));
	}

	/**
	 * 删除辖区人员
	 * 
	 * @param id 不是人员id也不是辖区id，而是tb_region_staff表的id
	 * @return
	 */
	@PostMapping(value = "{regionId}/staff/{staffId}", params = { "op=remove" })
	public Resp<Object> removeStaffInRegion(@PathVariable("regionId") Integer regionId,
			@PathVariable("staffId") Integer staffId) {
		regionStaffService.removeStaffInRegion(regionId, staffId);
		return Resp.ok();
	}
}
