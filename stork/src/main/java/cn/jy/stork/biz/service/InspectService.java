package cn.jy.stork.biz.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jy.stork.biz.pojo.InspectPojo;
import cn.jy.stork.dao.Inspect;
import cn.jy.stork.dao.InspectDao;

@Service
public class InspectService {
	@Autowired
	InspectDao inspectDao;
	/**
	 * 添加巡查
	 * @param pojo
	 * @return
	 */
	public Inspect addInspect(InspectPojo pojo) {
		final Inspect i=new Inspect();
		i.setRid(pojo.getRid());
		i.setUid(pojo.getUid());
		i.setUtime(new Date());
		i.setUmemo(pojo.getUmemo());
		i.setStep(0);
		inspectDao.addInspect(i);
		return i;
	}
	/**
	 * 通过辖区id查询巡查
	 * @param regionId
	 * @return
	 */
	public List<Inspect> findByRegionId(Integer regionId) {
		final List<Inspect> i=inspectDao.selectInspectByRegionId(regionId);
		return i;
	}
	/**
	 * 河长审批巡查
	 * @param id
	 * @param pojo 
	 * @return
	 */
	public int approveInspect(int id, InspectPojo pojo) {
		final Inspect i=new Inspect();
		i.setAtime(new Date());
		i.setAid(pojo.getAid());
		i.setAmemo(pojo.getAmemo());
		if(i.getAmemo().equals("忽略")) {
			i.setStep(100);
		}
		else if(i.getAmemo().equals("追踪处理")) {
			i.setStep(50);
		}
		return inspectDao.approveInspect(id,i);
	}
	
	public int inspectResult(int id, InspectPojo pojo) {
		final Inspect i=new Inspect();
		i.setResult(pojo.getResult());
		i.setStep(100);
		return inspectDao.inspectResult(id,i);
	}

}
