package cn.jy.stork.biz.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jy.stork.dao.SampleRecord;
import cn.jy.stork.dao.SampleRecordDao;
import cn.jy.stork.dao.Station;
import cn.jy.stork.exception.DataNotFoundException;

/**
 * 处理突发排放的业务组件
 * 
 * @author jsh
 *
 */
@Service
public class DischargeService {

	@Autowired
	SampleRecordDao sampleRecordDao;
	@Autowired
	StationService stationService;

	@Autowired
	HikPhotoService hikPhotoService;

	/**
	 * 排放开始。要生成排放记录，要拍照片保存并记录uri。
	 * 
	 * @throws DataNotFoundException
	 */
	public SampleRecord start(int stationId) throws DataNotFoundException {

		final Station station = stationService.findById(stationId);

		if (station.getVsn() == null || station.getVtoken() == null) {
			throw new DataNotFoundException("监控信息", "");
		}
		if (station.getRid() == null) {
			throw new DataNotFoundException("辖区", "");
		}

		final String uri = hikPhotoService.take(station.getVsn(), station.getVtoken());

		final SampleRecord record = new SampleRecord();
		record.setStime(new Date());
		record.setSid(stationId);
		record.setSname(station.getName());
		record.setSalias(station.getAlias());
		record.setRid(station.getRid());
		record.setRname(station.getRname());
		record.setBottle((int) (Math.random() * 6 + 1));// TODO
		record.setPhoto(uri);
		record.setDischargeStart(new Date());
		record.setScode(station.getCode());
		sampleRecordDao.insert(record);

		return record;
	}
}
