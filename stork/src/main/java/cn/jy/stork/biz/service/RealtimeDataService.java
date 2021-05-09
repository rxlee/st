package cn.jy.stork.biz.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jy.stork.dao.RealtimeData;
import cn.jy.stork.dao.RealtimeDataDao;
import cn.jy.stork.exception.DataNotFoundException;

@Service
public class RealtimeDataService {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	RealtimeDataDao RealtimeDataDao;

	public List<RealtimeData> findNewestByStation(int stationId) {
		if (stationId < 0) {
			return RealtimeDataDao.selectAllNewest();
		}
		return Arrays.asList(RealtimeDataDao.selectNewestByStationId(stationId));
	}

	/**
	 * 新增一批数据。实时数据基本都是批量产生
	 * 
	 * @param pojo 输入的数据
	 * @return
	 */
	public List<RealtimeData> addBatch(List<RealtimeData> datas) {
		RealtimeDataDao.insertBatch(datas);
		return datas;
	}

	/**
	 * 通用条件查询
	 * 
	 * @param stationId
	 * @param from
	 * @param to
	 * @return
	 * @throws DataNotFoundException
	 */
	public List<RealtimeData> find(Integer stationId, Date from, Date to, Integer pageSize, Integer pageIndex,
			boolean asc) {
		return RealtimeDataDao.find(stationId, from, to, pageSize, pageIndex, asc);
	}

}
