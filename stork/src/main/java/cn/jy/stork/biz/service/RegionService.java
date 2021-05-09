package cn.jy.stork.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jy.stork.biz.pojo.RegionPojo;
import cn.jy.stork.dao.Region;
import cn.jy.stork.dao.RegionDao;
import cn.jy.stork.dao.RegionWithOutline;
import cn.jy.stork.exception.DataNotFoundException;

@Service
public class RegionService {

	@Autowired
	RegionDao regionDao;

	/**
	 * 添加辖区
	 */
	public Region addRegion(RegionPojo pojo) {
		final Region r = new Region();
		r.setName(pojo.getName());
		r.setAlias(pojo.getAlias());
		regionDao.insertRegion(r);

		// 顺便创建辖区地理范围记录，留白
		regionDao.insertRegionOutline(r.getId(), "");

		return r;
	}

	/**
	 * 通过id查询辖区
	 * 
	 * @param
	 * @return
	 */
	public Region findRegionById(Integer id) throws DataNotFoundException {
		final Region r = regionDao.selectRegionById(id);
		if (r == null) {
			throw new DataNotFoundException("id", id);
		}
		return r;
	}

	/**
	 * 通过人员id查询所在辖区
	 * 
	 * @param staffId
	 * @return
	 */
	public Region findRegionByStaffId(Integer staffId) throws DataNotFoundException {
		final Region r = regionDao.selectRegionByStaffId(staffId);
		if (r == null) {
			throw new DataNotFoundException("人员id", staffId);
		}
		return r;
	}

	/**
	 * 通过id查询辖区地理范围
	 * 
	 * @param staffId
	 * @return
	 */
	public RegionWithOutline findOutlineByRegionId(Integer regionId) throws DataNotFoundException {
		final RegionWithOutline outline = regionDao.selectOutlineOfRegion(regionId);
		if (outline == null) {
			throw new DataNotFoundException("辖区id", regionId);
		}
		return outline;
	}

	/**
	 * 查询所有辖区
	 * 
	 * @param
	 * @return
	 */
	public List<Region> findAllRegions() {
		List<Region> dc = regionDao.selectAllRegions();
		return dc;
	}

	/**
	 * 修改辖区基本信息
	 * 
	 * @param id
	 * @param pojo
	 * @return
	 */

	public int modifyRegionProfile(int id, String name, String alias) {
		return regionDao.updateProfile(id, name, alias);
	}

	/**
	 * 修改辖区范围
	 * 
	 * @param id
	 * @param pojo
	 * @return
	 */
	public int modifyRegionOutline(int regionId, String outline) {
		return regionDao.updateRegionOutline(regionId, outline);
	}

}
