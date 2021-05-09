package cn.jy.stork.biz.task;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.jy.stork.dao.RealtimeData;
import cn.jy.stork.dao.RealtimeDataDao;
import cn.jy.stork.dao.RealtimeStreamspeedDao;
import cn.jy.stork.hardware.DevicePool;
import cn.jy.stork.hardware.DevicePool.StreamspeedIterator;
import cn.jy.stork.util.DatePeriod;

/**
 * 定时取流速数据保存。流速数据周期很短（每个心跳采一次），但持久化保存的周期不需要这么短
 * 
 * @author jsh
 *
 */
@Component
@Configuration
@EnableScheduling
public class RealtimeDataPicker {
	@Autowired
	DevicePool pool;

	@Autowired
	RealtimeDataDao realtimeDataDao;
	@Autowired
	RealtimeStreamspeedDao realtimeStreamspeedDao;

	private boolean firstRun = true;

	/**
	 * 每2分钟保存流速实时数据
	 */
	@Scheduled(cron = "0 0/1 * * * ?")
	void realtime() {
		final DatePeriod period = new DatePeriod();
		final Date year = period.getYear();
		final Date month = period.getMonth();
		final Date day = period.getDay();
		final Date time = period.clipped();

		final List<RealtimeData> list = new LinkedList<>();
		pool.iterateStreamspeed(new StreamspeedIterator() {
			@Override
			public void iterate(int id, int sn, Float t, Float d, Float s) {
				if (s == null) {
					return;
				}
				final RealtimeData entity = new RealtimeData();
				entity.setSid(id);
				entity.setS(s);
				entity.setD(d);
				entity.setT(t);
				entity.setYear(year);
				entity.setMonth(month);
				entity.setDay(day);
				entity.setRtime(time);
				list.add(entity);
			}
		});

		if (list.size() > 0) {
			realtimeDataDao.insertBatch(list);
			// 如果刚启动，保存流速数据马上生成上下限
			if (firstRun) {
				updateRange();
				firstRun = false;
			}
		}
	}

	/**
	 * 每小时刷新流速上下限
	 */
	@Scheduled(cron = "5 0 * * * ?")
	void updateRange() {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONDAY, -1);
		// 取最近一个月的上下限，减轻季节影响
		pool.streamspeedRange(realtimeStreamspeedDao.selectAllRanges(now.getTime()));
	}
}
