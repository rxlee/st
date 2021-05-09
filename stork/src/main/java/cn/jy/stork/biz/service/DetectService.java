package cn.jy.stork.biz.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jy.stork.biz.pojo.DetectContractPojo;
import cn.jy.stork.biz.pojo.DetectCorpPojo;
import cn.jy.stork.biz.pojo.DetectStaffPojo;
import cn.jy.stork.dao.Account;
import cn.jy.stork.dao.DetectContract;
import cn.jy.stork.dao.DetectContractDao;
import cn.jy.stork.dao.DetectCorp;
import cn.jy.stork.dao.DetectCorpDao;
import cn.jy.stork.dao.DetectExecutor;
import cn.jy.stork.dao.DetectExecutorEx;
import cn.jy.stork.dao.DetectStaff;
import cn.jy.stork.dao.Region;
import cn.jy.stork.exception.DataNotFoundException;

@Service
public class DetectService {
	@Autowired
	DetectCorpDao corpDao;
	@Autowired
	DetectContractDao contractDao;

	@Autowired
	AccountService accountService;

	@Autowired
	RegionService regionService;

	/**
	 * 添加检测机构
	 * 
	 * @param pojo
	 * @return
	 */
	public DetectCorp addCorp(DetectCorpPojo pojo) {
		final DetectCorp dc = new DetectCorp();
		dc.setName(pojo.getName());
		dc.setAlias(pojo.getAlias());
		dc.setAddress(pojo.getAddress());
		dc.setCategory(pojo.getCategory());
		corpDao.insert(dc);
		return dc;
	}

	/**
	 * 添加合同
	 * 
	 * @param id
	 * @param pojo
	 * @return
	 * @throws DataNotFoundException
	 */
	public DetectContract addContract(DetectContractPojo pojo) throws DataNotFoundException {
		// 根据rid和cid把名称查出来，填进合同里
		final int regionId = pojo.getRegion();
		final int corpId = pojo.getCorp();

		final Region r = regionService.findRegionById(regionId);
		final DetectCorp c = corpDao.selectCorpById(corpId);

		final DetectContract contract = new DetectContract();
		contract.setRid(pojo.getRegion()); // 辖区
		contract.setRname(r.getName());
		contract.setRalias(r.getAlias());
		contract.setCid(pojo.getCorp()); // 机构
		contract.setCname(c.getName());
		contract.setCalias(c.getAlias());
		contract.setStartTime(new Date(pojo.getFrom()));
		contract.setEndTime(new Date(pojo.getTo()));
		contractDao.addContract(contract);

		// 添加第一个送样人员（单独的表）
		final Account account = accountService.findById(pojo.getDelivery());
		final DetectExecutor executor = new DetectExecutor();
		executor.setCid(contract.getId());
		executor.setUid(pojo.getDelivery());
		executor.setUname(account.getName());
		executor.setUphone(account.getTelephone());
		contractDao.addExecutor(executor);

		return contract;

	}

	/**
	 * 添加监测机构工作人员
	 * 
	 * @param id
	 * @param pojo
	 * @return
	 * @throws DataNotFoundException
	 */
	public void addStaffInCorp(int corpId, DetectStaffPojo pojo) throws DataNotFoundException {
		final Account account = accountService.findById(pojo.getStaff());
		final DetectCorp corp = corpDao.selectCorpById(corpId);

		final DetectStaff entity = new DetectStaff();
		entity.setUid(pojo.getStaff());
		entity.setUname(account.getName());
		entity.setUphone(account.getTelephone());
		entity.setUtype(pojo.getType());
		entity.setCid(corpId);
		entity.setCname(corp.getName());
		entity.setCalias(corp.getAlias());
		corpDao.insertStaff(entity);

		accountService.updateRole(pojo.getStaff(), pojo.getType() == 1 ? "sample-delivery" : "sample-test");
	}

	/**
	 * 通过机构id查询采样送样人员
	 * 
	 * @param corpId
	 * @return
	 */
	public List<DetectExecutor> findDeliveriesByCorpId(Integer corpId) {
		return corpDao.selectDeliveriesInCorp(corpId);
	}

	/**
	 * 通过辖区id查询采样送样人员
	 * 
	 * @param corpId
	 * @return
	 */
	public List<DetectExecutorEx> findDeliveriesByRegionId(Integer regionId) {
		return corpDao.selectDeliveriesInRegion(regionId);
	}

	/**
	 * 修改检测机构经纬度
	 * 
	 * @param id
	 * @param pojo
	 * @return
	 */
	public int modifyCorpLocate(int id, Float lng, Float lat) {
		return corpDao.updateCorpLocate(id, lng, lat);
	}

	/**
	 * 修改检测机构基本信息
	 * 
	 * @param id
	 * @param pojo
	 * @return
	 */
	public int modifyCorpProfile(int id, DetectCorpPojo pojo) {
		return corpDao.updateCorpProfile(id, pojo);
	}

	/**
	 * 删除检测机构（伪删除，实际上是暂停其业务）
	 * 
	 * @param id
	 * @param pojo
	 * @return
	 */
	public int removeCorp(int id) {
		return corpDao.updateCorpStatus(id, 0);
	}

	/**
	 * 恢复检测机构
	 * 
	 * @param id
	 * @param pojo
	 * @return
	 */
	public int recoverCorp(int id) {
		return corpDao.updateCorpStatus(id, 1);
	}

	/**
	 * 删除检测机构下的执行人员
	 * 
	 * @param id
	 * @param pojo
	 * @return
	 */
	public void delectStaff(int corpId, int id) {
		corpDao.delectStaffFromCorp(corpId, id);
	}

	/**
	 * 通过id查询检测机构
	 * 
	 * @param id
	 * @return
	 * @throws DataNotFoundException
	 */
	public DetectCorp findCorpById(Integer id) throws DataNotFoundException {
		final DetectCorp dc = corpDao.selectCorpById(id);
		if (dc == null) {
			throw new DataNotFoundException("id", id);
		}
		return dc;
	}

	/**
	 * 根据人员id查询所在检测机构
	 * 
	 * @param id
	 * @param pojo
	 * @return
	 */
	public DetectCorp findCorpByStaff(int staffId) {
		return corpDao.selectCorpByStaff(staffId);
	}
	/**
	 * 通过送样人id查询检验人id
	 * @param staffId
	 * @return
	 */
	public List<DetectStaff> findTestByStaffId(Integer staffId) {
		return corpDao.selectTestByStaff(staffId);
	}

	/**
	 * 根据辖区id查询合同
	 * 
	 * @param
	 * @return
	 */
	public List<DetectContract> queryContractByRegionId(Integer id) throws DataNotFoundException {
		final List<DetectContract> dc = corpDao.selectContractsByRegionId(id);
		return dc;
	}

	/**
	 * 查询所有检测机构
	 * 
	 * @param
	 * @return
	 */
	public List<DetectCorp> findAllCorps() {
		List<DetectCorp> dc = corpDao.selectAllCorps();
		return dc;
	}

	/**
	 * 查询所有检测机构
	 * 
	 * @param
	 * @return
	 */
	public List<DetectStaff> queryByCorpId(Integer Corpid) {
		List<DetectStaff> dc = corpDao.selectStaffsByCorpId(Corpid);
		return dc;
	}



}
