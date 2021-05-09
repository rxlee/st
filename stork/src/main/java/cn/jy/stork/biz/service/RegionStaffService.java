package cn.jy.stork.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jy.stork.biz.pojo.RegionStaffPojo;
import cn.jy.stork.dao.Account;
import cn.jy.stork.dao.AccountDao;
import cn.jy.stork.dao.RegionDao;
import cn.jy.stork.dao.RegionStaff;

@Service
public class RegionStaffService {

	@Autowired
	RegionDao regionDao;
	@Autowired
	AccountDao accountDao;
	@Autowired
	AccountService accountService;

	/**
	 * 向辖区添加人员
	 * 
	 * @param id
	 * @param entity
	 * @return
	 */
	public RegionStaff addStaff(Integer regionId, RegionStaffPojo pojo) {
		final Account a = accountDao.findById(pojo.getStaff());
		final RegionStaff entity = new RegionStaff();
		entity.setRid(regionId);
		entity.setUtype(pojo.getType());
		entity.setUid(pojo.getStaff());
		entity.setUname(a.getName());
		entity.setUphone(a.getTelephone());
		regionDao.insertStaff(entity);

		// 根据utype改变账号角色
		accountService.updateRole(entity.getUid(), entity.type2role());

		return entity;
	}

	/**
	 * 通过辖区id查询辖区内的所有人员
	 * 
	 * @param regionId
	 * @return
	 */
	public List<RegionStaff> findStaffsByRegionId(Integer regionId) {
		return regionDao.selectStaffsInRegion(regionId);
	}

	/**
	 * 删除辖区内任职人员（不会删除人员账号）
	 * 
	 * @param id
	 * @param pojo
	 * @return
	 */
	public void removeStaffInRegion(Integer regionId, Integer staffId) {
		regionDao.deleteStaffInRegion(regionId, staffId);
		accountService.updateRole(staffId, "");
	}

}
