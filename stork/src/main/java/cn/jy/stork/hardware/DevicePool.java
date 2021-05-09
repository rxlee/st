package cn.jy.stork.hardware;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import cn.jy.stork.biz.service.StationService;
import cn.jy.stork.dao.Station;
import cn.jy.stork.dao.StreamspeedRange;
import cn.jy.stork.hardware.event.DirectEvent;
import cn.jy.stork.hardware.packet.down.ReadDataPacket;
import cn.jy.stork.hardware.packet.down.__IDownPacket;
import cn.jy.stork.hardware.packet.up.IUpPacket;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * @author jsh
 *
 */
@Component
public class DevicePool implements ApplicationListener<DirectEvent> {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final DeviceProxy[] DEVICES = new DeviceProxy[0xFF];

	@Autowired
	StationService stationService;

	@PostConstruct
	void init() {
		// 初始化时加载所有水站的信息
		// 如果业务模块里面没有加过水站，则这个水站不进入池，后续接到报文不处理、也不下发报文
		final List<Station> all = stationService.all();
		for (Station station : all) {
			final Integer sn = station.getSn();
			if (sn != null) {
				DEVICES[sn] = new DeviceProxy(station.getId(), sn);
			}
		}
	}

	@Override
	public void onApplicationEvent(DirectEvent event) {
		final Integer sn = event.sn();
		final __IDownPacket packet = event.getPacket();
		if (sn == null) {
			this.sendDirectToAllStations(packet);
		} else {
			this.sendDeirectToSingleStation(sn, packet);
		}
	}

	public void onStationRelationChanged(int station, Integer[] sn) {
		Integer old = sn[0], newest = sn[1];
		if (old == newest) {
			return;
		}
		// 接下来肯定是前后sn不同
		if (old != null) {
			// 如果原来sn不为空，池里面一定已经有了，而且换了新的sn，那么把原来的删除
			DEVICES[old] = null;
		}
		// 最后一并把新的写进去
		if (newest != null) {
			DEVICES[newest] = new DeviceProxy(station, newest);
		}
	}

	/**
	 * 向所有水站发报文，主要用于定时采样
	 * 
	 * @param packet
	 */
	private void sendDirectToAllStations(__IDownPacket packet) {
		for (int sn = 0; sn < DEVICES.length; sn++) {
			sendDeirectToSingleStation(sn, packet);
		}
	}

	/**
	 * 向单个水站发报文，用于单个控制等
	 * 
	 * @param sn
	 * @param packet
	 */
	private void sendDeirectToSingleStation(int sn, __IDownPacket packet) {
		final DeviceProxy dp = DEVICES[sn];
		if (dp != null) {
			dp.sendPacket(packet.raw(sn));
		}
	}

	public void iterateStreamspeed(StreamspeedIterator it) {
		for (int i = 0; i < DEVICES.length; i++) {
			final DeviceProxy dp = DEVICES[i];
			if (dp != null) {
				it.iterate(dp.getId(), dp.getSn(), dp.getT(), dp.getD(), dp.getS());
			}
		}
	}

	public static interface StreamspeedIterator {
		public void iterate(int id, int sn, Float t, Float d, Float s);
	}

	/**
	 * 心跳
	 * 
	 * @param ctx
	 * @param heartbeat
	 */
	void heartbeat(ChannelHandlerContext ctx, IUpPacket.Heartbeat heartbeat) {
//		logger.debug("心跳");
		final int sn = heartbeat.sn();
		final DeviceProxy dp = DEVICES[sn];
		if (dp == null) {
//			logger.debug("没有找到水站sn={}", sn);
			// 如果池里没有水站信息，那么就跳过
			return;
		}
		// 记录最新的状态值
		dp.update(ctx, heartbeat.getStorages(), heartbeat.getSwitchers());
		// 紧跟着下发一次取流速
		final ReadDataPacket packet = ReadDataPacket.streamspeed();
		ctx.writeAndFlush(Unpooled.copiedBuffer(packet.raw(sn)));
	}

	/**
	 * 流速刷新，当收到流速报文时触发
	 * 
	 * @param streamspeed
	 */
	void streamspeed(IUpPacket.SampleData streamspeed) {
//		logger.debug("收到流速数据：{}", streamspeed.getDatas());
		final int sn = streamspeed.sn();
		final DeviceProxy dp = DEVICES[sn];
		if (dp == null) {
			// 如果池里没有水站信息，那么就跳过
			return;
		}
		final Float[] data = streamspeed.getDatas();
		dp.update(data[0], data[1], data[2]);
	}

	/**
	 * 刷新流速上下限
	 * 
	 * @param ranges
	 */
	public void streamspeedRange(List<StreamspeedRange> ranges) {
		final HashMap<Integer, StreamspeedRange> map = new HashMap<Integer, StreamspeedRange>();
		for (StreamspeedRange r : ranges) {
			map.put(r.getSid(), r);
		}
		for (int i = 0; i < DEVICES.length; i++) {
			final DeviceProxy dp = DEVICES[i];
			if (dp != null) {
				final StreamspeedRange r = map.get(dp.getId());
				if (r == null)
					continue;
				dp.updateThreshold(r.getBottom(), r.getTop());
			}
		}
	}

}
