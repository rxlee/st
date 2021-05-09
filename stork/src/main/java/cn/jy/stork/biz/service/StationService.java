package cn.jy.stork.biz.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import cn.jy.stork.biz.pojo.StationLocateModifyPojo;
import cn.jy.stork.biz.pojo.StationProfilePojo;
import cn.jy.stork.dao.AccountDao;
import cn.jy.stork.dao.Region;
import cn.jy.stork.dao.Station;
import cn.jy.stork.dao.StationDao;
import cn.jy.stork.dao.StationStatus;
import cn.jy.stork.dao.StationStatusDao;
import cn.jy.stork.exception.DataDuplicatedException;
import cn.jy.stork.exception.DataNotFoundException;
import cn.jy.stork.hardware.DevicePool;
import cn.jy.stork.hardware.event.DirectEvent;
import cn.jy.stork.hardware.packet.down.LockReleasePacket;

@Service
public class StationService {

	@Autowired
	private ApplicationContext dispatcher;

	@Autowired
	StationDao stationDao;
	@Autowired
	StationStatusDao stationStatusDao;

	@Autowired
	AccountDao accountDao;

	@Autowired
	RegionService regionService;

	@Autowired
	DevicePool devicePool;

	/**
	 * 新增一个水站
	 * 
	 * @param pojo
	 * @return
	 * @throws DataNotFoundException
	 */
	public Station add(StationProfilePojo pojo) throws DataDuplicatedException, DataNotFoundException {

		final Integer sn = pojo.getSn();

		// 检查sn碰撞，sn不可以重复
		if (sn != null && stationDao.existsBySn(sn)) {
			throw new DataDuplicatedException("设备号", sn);
		}

		// 生成水站数据
		final Station station = new Station();
		station.setName(pojo.getName());
		station.setCode(pojo.getCode());
		station.setAlias(pojo.getAlias());
		station.setMemo(pojo.getMemo());
		station.setSn(sn);
		station.setVsn(pojo.getVsn());
		station.setVtoken(pojo.getVtoken());
		if (pojo.getRid() != null) {
			final Region region = regionService.findRegionById(pojo.getRid());
			station.setRname(region.getName());
			station.setRid(pojo.getRid());
		}
		stationDao.insert(station);

		// 通知设备关联水站
		if (sn != null) {
			devicePool.onStationRelationChanged(station.getId(), new Integer[] { null, sn });
		}

		return station;
	}

	/**
	 * 查询所有水站信息
	 * 
	 * @return
	 */
	public List<Station> all() {
		return stationDao.selectAll();
	}

	/**
	 * 按id查找水站
	 * 
	 * @param id
	 * @return
	 * @throws DataNotFoundException
	 */
	public Station findById(int id) throws DataNotFoundException {
		final Station station = stationDao.selectById(id);
		if (station == null) {
			throw new DataNotFoundException("id", id);
		}
		return station;
	}

	/**
	 * 按sn查找水站
	 * 
	 * @param sn
	 * @return
	 * @throws DataNotFoundException
	 */
	public Station findBySn(int sn) throws DataNotFoundException {
		final Station station = stationDao.selectBySn(sn);
		if (station == null) {
			throw new DataNotFoundException("设备号", sn);
		}
		return station;
	}

	/**
	 * 按辖区id查找水站
	 * 
	 * @param regionId
	 * @return
	 */
	public List<Station> findByRegionId(int regionId) {
		return stationDao.selectByRegionId(regionId);
	}

	/**
	 * @param id
	 */
	public void removeById(int id) {
		stationDao.deleteById(id);
		// TODO 还要处理关联数据
	}

	/**
	 * 修改水站基本信息（不修改状态，基本信息通常是人为修改，而状态是由硬件通讯模块修改）
	 * 
	 * @param id   水站id，注意这是水站的数据库id，不是硬件设备号
	 * @param pojo 修改的信息
	 * @return 修改后的水站最新信息
	 * @throws DataDuplicatedException sn已存在时抛出
	 * @throws DataNotFoundException   id找不到时抛出
	 */
	public Station modifyProfile(int id, StationProfilePojo pojo)
			throws DataDuplicatedException, DataNotFoundException {
		if (pojo.getRid() != null) {
			final Region region = regionService.findRegionById(pojo.getRid());
			pojo.setRname(region.getName());
		} else {
			pojo.setRname("");
		}

		final Station old = stationDao.selectById(id);

		stationDao.updateProfile(id, pojo);

		// 发关联变化的通知
		devicePool.onStationRelationChanged(id, new Integer[] { old.getSn(), pojo.getSn() });

		return this.findById(id);
	}

	/**
	 * 修改水站辖区
	 * 
	 * @param id
	 * @param pojo
	 * @return
	 * @throws DataNotFoundException
	 */
	public Station updateRegion(int id, Region pojo) throws DataNotFoundException {
		stationDao.updateRegion(id, pojo);
		return this.findById(id);
	}

	/**
	 * 刷新水站经纬度定位
	 * 
	 * @param id
	 * @param pojo
	 * @return
	 * @throws DataDuplicatedException
	 * @throws DataNotFoundException
	 */
	public void locate(int id, StationLocateModifyPojo pojo) {
		stationDao.updateLocate(id, pojo.getLng(), pojo.getLat());
	}

	/**
	 * 批量刷新水站经纬度定位
	 * 
	 * @param pojo
	 * @return
	 */
	public void locateBatch(List<StationLocateModifyPojo> pojo) {
		stationDao.updateLocateBatch(pojo);
	}

	/**
	 * 查询正在维护的水站sn集合
	 * 
	 * @return
	 */
	public Set<Integer> maintaining() {
		final Date now = new Date();
		Set<Integer> sns = new HashSet<>();
		for (Station station : stationDao.selectAll()) {
			final Date mt = station.getMaintainTime();
			if (mt != null && mt.after(now)) {
				// 如果设置了维护时间且维护时间在now之后，那么现在就处于维护时间内
				sns.add(station.getSn());
			}
		}
		return sns;
	}

	/**
	 * 开锁
	 * 
	 * @param sn
	 */
	public void lockRelease(int sn) {
		final LockReleasePacket packet = new LockReleasePacket();
		final DirectEvent e = new DirectEvent(this, sn, packet);
		dispatcher.publishEvent(e);
	}

	/**
	 * 指定水站进入维护状态
	 * 
	 * @param id          水站id
	 * @param maintaining 是否正在维护
	 */
	public void maintain(final int id, boolean maintaining) {
//		Date time = null;
//		if (maintaining) {
//			// 1、如果是要进入维护状态，先下发一系列停止报文
//			try {
//				dispatcher.publishEvent(new DirectEvent(this, id,
//						// 泵下电
//						RelayPacket.pump(false),
//						// 流速下电
//						RelayPacket.streamspeed(false),
//						// 多参数下电
//						RelayPacket.general(false),
//						// 总磷下电
//						RelayPacket.p(false)));
//				// 停顿
//				Thread.sleep(50);
//			} catch (InterruptedException e) {
//			}
//			time = new Date(System.currentTimeMillis() + 60 * 60 * 1000L);// 时间设置为1小时以后
//		}
//		// 2、状态修改
//		stationDao.maintain(id, time);// 如果是要维护，时间改为1小时以后，否则是要退出维护，则时间改为null
	}

	/**
	 * 修改水站的状态（储水瓶、开关和最近活动时间），给硬件通信模块用，业务模块不要调用
	 * 
	 * @param sn        注意这里的station是水站的硬件设备号，不是数据库id
	 * @param storages  储水瓶状态
	 * @param switchers 开关或运行状态
	 * @return
	 */
	public void modifyStatus(int sn, boolean[] storages, boolean[] switchers) {
		final int updated = stationStatusDao.updateAllStatus(sn, new Date(), storages, switchers);
		if (updated == 0) {
			// 没刷新任何水站状态，表示这个水站还没有创建状态记录，那么补一下
			final StationStatus entity = new StationStatus(sn);
			stationStatusDao.insert(entity);
			stationStatusDao.updateAllStatus(sn, new Date(), storages, switchers);
		}
	}

}
