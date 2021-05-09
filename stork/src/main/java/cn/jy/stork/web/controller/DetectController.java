package cn.jy.stork.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.jy.stork.biz.pojo.DetectContractPojo;
import cn.jy.stork.biz.pojo.DetectCorpLocatePojo;
import cn.jy.stork.biz.pojo.DetectCorpPojo;
import cn.jy.stork.biz.pojo.DetectStaffPojo;
import cn.jy.stork.biz.service.DetectService;
import cn.jy.stork.exception.DataNotFoundException;
import cn.jy.stork.web.Resp;

@RestController
@RequestMapping("detect")
public class DetectController {

	@Autowired
	DetectService detectService;

	/**
	 * 添加一个检测机构
	 * 
	 * @param pojo
	 * @return
	 */
	@PostMapping("corp")
	public Resp add(@RequestBody DetectCorpPojo pojo) {
		// TODO 接口有修改，注意前端修改
		return Resp.okWithData(detectService.addCorp(pojo));
	}

	/**
	 * 添加一个合同
	 * 
	 * @param pojo
	 * @return
	 */
	@PostMapping("contract")
	public Resp addContract(@RequestBody DetectContractPojo pojo) {
		try {
			return Resp.okWithData(detectService.addContract(pojo));
		} catch (DataNotFoundException e) {
			return Resp.fail(e.getMessage());
		}
	}

	/**
	 * 通过id查询检测机构
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("corp/{id}")
	public Resp detail(@PathVariable Integer id) {
		// TODO 接口有修改，注意前端修改
		try {
			return Resp.okWithData(detectService.findCorpById(id));
		} catch (DataNotFoundException e) {
			return Resp.fail(e.getMessage());
		}
	}

	/**
	 * 通过机构id查询取样送样人
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "corp/{corpId}/staff", params = { "type=delivery" })
	public Resp queryDeliveryByCorp(@PathVariable Integer corpId) {
		return Resp.okWithData(detectService.findDeliveriesByCorpId(corpId));
	}

	/**
	 * 通过送样人id查询检验人id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "corp/{staffId}/staff", params = { "type=test" })
	public Resp queryTestByStaffId(@PathVariable Integer staffId) {
		return Resp.okWithData(detectService.findTestByStaffId(staffId));
	}
	
	/**
	 * 通过辖区id查询取样送样人
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "region/{regionId}/delivery")
	public Resp queryDeliveryByRegion(@PathVariable Integer regionId) {
		return Resp.okWithData(detectService.findDeliveriesByRegionId(regionId));
	}

	/**
	 * 通过辖区id查询合同
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "contract/{regionId}", params = { "by=region" })
	public Resp queryContractByRegionId(@PathVariable Integer regionId) {
		try {
			return Resp.okWithData(detectService.queryContractByRegionId(regionId));
		} catch (DataNotFoundException e) {
			return Resp.fail(e.getMessage());
		}
	}

	/**
	 * 查询所有检测机构
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("corp")
	public Resp queryAllCorps() {
		// TODO 接口有修改，注意前端修改
		return Resp.okWithData(detectService.findAllCorps());
	}

	/**
	 * 需要查询检测公司员工
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("corp/{corpId}/staff")
	public Resp queryByCorpId(@PathVariable Integer corpId) {
		return Resp.okWithData(detectService.queryByCorpId(corpId));
	}

	/**
	 * 修改检测机构位置信息
	 * 
	 * @param
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping(value = "corp/{corpId}", params = { "op=modify", "about=locate" })
	public Resp modifyLocat(@PathVariable("corpId") int corpId, @RequestBody DetectCorpLocatePojo pojo) {
		// TODO 接口有修改，注意前端修改
		detectService.modifyCorpLocate(corpId, pojo.getLng(), pojo.getLat());
		return Resp.ok();
	}

	/**
	 * 修改检测机构基本信息
	 * 
	 * @param
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping(value = "corp/{corpId}", params = { "op=modify", "about=profile" })
	public Resp modifyProfile(@PathVariable("corpId") int corpId, @RequestBody DetectCorpPojo pojo) {
		// TODO 接口有修改，注意前端修改
		detectService.modifyCorpProfile(corpId, pojo);
		return Resp.ok();
	}

	/**
	 * 封禁检测机构
	 * 
	 * @param
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping(value = "corp/{corpId}", params = { "op=ban" })
	public Resp banCorp(@PathVariable("id") int id) {
		detectService.removeCorp(id);
		return Resp.ok();
	}

	/**
	 * 添加检测机构员工
	 * 
	 * @param
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping("corp/{corpId}/staff")
	public Resp addStaff(@PathVariable("corpId") int corpId, @RequestBody DetectStaffPojo pojo) {
		try {
			detectService.addStaffInCorp(corpId, pojo);
			return Resp.ok();
		} catch (DataNotFoundException e) {
			return Resp.fail(e.getMessage());
		}
	}

	// TODO 需要针对合同，填加执行人员（来自于监测公司）
//	@PostMapping("contract/{contractId}/executor")

	/**
	 * 删除检测机构员工
	 * 
	 * @param
	 * @return
	 * @throws DataNotFoundException
	 */
	@PostMapping(value = "corp/{corpId}/staff/{id}", params = { "op=remove" })
	public Resp delectStaff(@PathVariable("corpId") Integer corpId, @PathVariable("id") Integer id) {
		detectService.delectStaff(corpId, id);
		return Resp.ok();
	}

}
