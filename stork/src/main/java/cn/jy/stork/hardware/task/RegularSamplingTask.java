package cn.jy.stork.hardware.task;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Scheduled;

import cn.jy.stork.hardware.event.DirectEvent;
import cn.jy.stork.hardware.event.ManualSamplingEvent;
import cn.jy.stork.hardware.packet.down.ReadDataPacket;
import cn.jy.stork.hardware.packet.down.RelayPacket;
import cn.jy.stork.hardware.packet.down.__IDownPacket;

/**
 * 定时任务自动采样
 * 
 * @author jsh
 *
 */
//@Component
//@Configuration
//@EnableScheduling
public class RegularSamplingTask implements ApplicationListener<ManualSamplingEvent> {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	private Long pause;
	@Autowired
	private ApplicationContext dispatcher;

	@Override
	public void onApplicationEvent(ManualSamplingEvent event) {
		// 收到手动采样（暂停自动采样）事件通知
		if (event.is()) {
			this.pause = System.currentTimeMillis() + (1000L * 3600 * 6);// 默认暂停6小时
		} else {
			this.pause = null;
		}
	}

	/**
	 * 检查是否暂停。若是暂停，则停止自动采样
	 * 
	 * @return
	 */
	private boolean paused() {
		return pause != null && (pause > System.currentTimeMillis());
	}

	/**
	 * 【多参数】开始换水
	 * 
	 * @throws InterruptedException
	 */
	@Scheduled(cron = "0 55 0/2 * * ?") // 双数小时的第55分钟开始，如00:55:00, 02:55:00, 04:55:00...
	void generalFlush() throws InterruptedException {
		if (paused()) {
			return;
		}
		_generalFlush();
	}

	void _generalFlush() throws InterruptedException {
		// 打开换水
		this.pump(true);
	}

	/**
	 * 【多参数】关闭换水，上电
	 * 
	 * @throws InterruptedException
	 */
	@Scheduled(cron = "30 57 0/2 * * ?") // 双数小时的第57分半开始，如00:57:30, 02:57:30, 04:57:30...
	void generalOpen() throws InterruptedException {
		if (paused()) {
			return;
		}
		_generalOpen();
	}

	void _generalOpen() throws InterruptedException {
		// 关闭换水
		this.pump(false);
		Thread.sleep(100);// 间隔一下
		// 开始测量
		this.publish(RelayPacket.general(true));
	}

	/**
	 * 【多参数】取数据
	 * 
	 * @throws InterruptedException
	 */
	@Scheduled(cron = "0 58 0/2 * * ?") // 双数小时的第58分钟开始，如00:58:00, 02:58:00, 04:58:00...
	void generalFetchData() throws InterruptedException {
		if (paused()) {
			return;
		}
		_generalFetchData();
	}

	void _generalFetchData() throws InterruptedException {
		this.publish(ReadDataPacket.general());
	}

	/**
	 * 【多参数】下电
	 * 
	 * @throws InterruptedException
	 */
	@Scheduled(cron = "55 58 0/2 * * ?") // 双数小时的第58分半开始，如00:58:30, 02:58:30, 04:58:30...
	void generalOff() throws InterruptedException {
		if (paused()) {
			return;
		}
		_generalOff();
	}

	void _generalOff() throws InterruptedException {
		this.publish(RelayPacket.general(false));
	}

	/**
	 * 【总磷】开始换水
	 * 
	 * @throws InterruptedException
	 */
	@Scheduled(cron = "0 5 3 ? * MON") // 每周一的03:05:00开始
	void phosFlush() throws InterruptedException {
		if (paused()) {
			return;
		}
		this.pump(true);
	}

	/**
	 * 【总磷】关闭换水，上电
	 * 
	 * @throws InterruptedException
	 */
	@Scheduled(cron = "30 7 3 ? * MON") // 每周一的03:07:30关闭换水，总磷传感器上电
	void phosOpen() throws InterruptedException {
		if (paused()) {
			return;
		}
		this.pump(false);
		Thread.sleep(100);// 间隔一下
		this.publish(RelayPacket.p(true));
	}

	/**
	 * 【总磷】开始测量
	 */
	@Scheduled(cron = "0 10 3 ? * MON") // 每周一的03:10:00总磷开始测量（需要持续40多分钟）
	void phosMeasure() throws InterruptedException {
		if (paused()) {
			return;
		}
		this.publish(ReadDataPacket.phosphorusPrepared());
	}

	/**
	 * 【总磷】取数据
	 * 
	 * @throws InterruptedException
	 */
	@Scheduled(cron = "0 52 3 ? * MON") // 每周一的03:52:00总磷取数据
	void phosFetchData() throws InterruptedException {
		if (paused()) {
			return;
		}
		this.publish(ReadDataPacket.phosphorus());
	}

	/**
	 * 【总磷】下电
	 * 
	 * @throws InterruptedException
	 */
	@Scheduled(cron = "30 53 3 ? * MON") // 每周一的03:53:30总磷下电
	void phosOff() throws InterruptedException {
		if (paused()) {
			return;
		}
		this.publish(RelayPacket.p(false));
	}

	/**
	 * 看门定时任务，在每个整10分钟倍数时间点，强制执行一些关闭操作，如关闭泵、传感器下电等
	 * 
	 * @throws InterruptedException
	 */

	@Scheduled(cron = "1 0/10 * * * ?")
	void guard() throws InterruptedException {
		if (paused()) {
			return;
		}

		this.pump(false);
		Thread.sleep(100);// 间隔一下
		this.publish(RelayPacket.general(false));

		// 总磷在每周一3点到4点间采样，所以要避开这段时间，不要下电
		Calendar now = Calendar.getInstance();
		if (now.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY || now.get(Calendar.HOUR_OF_DAY) != 3) {
			Thread.sleep(100);// 间隔一下
			this.publish(RelayPacket.p(false));
		}

	}

	/**
	 * 蓄水泵全开或全关（遍历所有水站）
	 * 
	 * @param start 开或关
	 */
	private void pump(boolean start) {
		this.publish(RelayPacket.pump(start));
	}

	private void publish(__IDownPacket packet) {
		dispatcher.publishEvent(new DirectEvent(this, packet));
	}
}
