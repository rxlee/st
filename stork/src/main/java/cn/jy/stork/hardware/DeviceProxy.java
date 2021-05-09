package cn.jy.stork.hardware;

import java.math.BigDecimal;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

public class DeviceProxy {

	private int id;// 水站id
	private int sn;
	private ChannelHandlerContext ctx;
	private boolean[] storages;
	private boolean[] switchers;
	private Float t, d, s;
	private Float threshold = 4f;

	/**
	 * 流速超标的判断指数。
	 * 
	 * 初始值是0。
	 * 
	 * 每当流量过大（超出历史中位数），此值+1，否则-1，最低为0。当此值达到最大限值，则判定为发生排放，触发排放处理机制，排放标志discharging置为true，此时此值不再增加（但会减少）
	 * 
	 * 当此值一直减少到0，且此前正在排放（discharging为true），则判定为排放结束，触发排放结束处理机制，顺便将排放标志discharging置为false
	 */
	private int streamOverspeedIndex = 0;
	private boolean discharging = false;

	public DeviceProxy(int id, int sn) {
		this.id = id;
		this.sn = sn;
	}

	/**
	 * 状态刷新，根据心跳包刷新
	 * 
	 * @param ctx
	 * @param storages
	 * @param switchers
	 */
	public void update(ChannelHandlerContext ctx, boolean[] storages, boolean[] switchers) {
		this.ctx = ctx;
		this.storages = storages;
		this.switchers = switchers;
	}

	/**
	 * 流速刷新，根据流速数据刷新
	 * 
	 * @param t
	 * @param d
	 * @param s
	 */
	public void update(Float t, Float d, Float s) {
		this.t = t;
		this.d = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		this.s = new BigDecimal(s).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

		final int GUARD = 30;
		final float threshold = 2f;// TODO
		if (s >= threshold) {
			// 流量较大
			if (streamOverspeedIndex < GUARD) {
				// 还没达到上限，指数增加
				streamOverspeedIndex++;
			} else {
				// 达到上限，分为在排放和不在排放2种情况
				if (!discharging) {
					// 达到上限且不在排放，触发排放处理机制
					// TODO
					discharging = true;
				} else {
					// 达到上限且正在排放，无需处理
				}
			}
		} else {
			// 流量较小
			if (streamOverspeedIndex > 0) {
				// 还没达到下限
				streamOverspeedIndex--;
			} else {
				// 达到下限，分为在排放和不在排放2种情况
				if (discharging) {
					// 达到下限且正在排放，触发排放结束处理机制
					// TODO
					discharging = false;
				} else {
					// 达到下限且不在排放，无需处理
				}
			}
		}
	}

	public void updateThreshold(Float bottom, Float top) {
		if (bottom != null && top != null && top - bottom > 1) {
			threshold = (bottom - top) / 2 + bottom;
		}
	}

	public void sendPacket(byte[] bytes) {
		ctx.writeAndFlush(Unpooled.copiedBuffer(bytes));
	}

	public Integer getId() {
		return id;
	}

	public Integer getSn() {
		return sn;
	}

	public Float getT() {
		return t;
	}

	public Float getD() {
		return d;
	}

	public Float getS() {
		return s;
	}

}
