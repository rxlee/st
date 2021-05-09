package cn.jy.stork.hardware.packet.up;

import io.netty.channel.ChannelHandlerContext;

public class UpPacketWrapper {

	public UpPacketWrapper() {
	}

	public void exec(ChannelHandlerContext context, byte[] bytes) {
		// 检查帧头
		if (bytes[0] != 0xAA && bytes[1] != 0x55) {
			this.illegalPacket(bytes);
		}
		// 检查校验字节
		final int len = 0xFF & bytes[IUpPacket.INDEX_OF_LEN];
		int checksum = 0;
		for (int i = 0; i < len; i++) {// 从主指令（索引固定为3）开始累加len个，求和，取低8位
			checksum += bytes[i + 3];
		}
		if ((0xFF & checksum) != (0xFF & bytes[IUpPacket.INDEX_OF_LEN + len + 1])) {
			this.illegalPacket(bytes);
		}
		final int func = 0xFF & bytes[IUpPacket.INDEX_OF_FUNC];
		final int sn = 0xFF & bytes[IUpPacket.INDED_OF_STATION];

		switch (func) {
		case IUpPacket.FUNC_HEARTBEAT:
			this.heartbeat(context, new IUpPacket.Heartbeat(sn, bytes));
			break;
		// 心跳
		case IUpPacket.FUNC_DATA_GENERAL:
			// 数据：常规多参数
			this.general(context, new IUpPacket.SampleData(sn, bytes, 12));
			break;
		case IUpPacket.FUNC_DATA_STREAMSPEED:
			// 数据：流速
			this.streamspeed(context, new IUpPacket.SampleData(sn, bytes, 3));
			break;
		case IUpPacket.FUNC_DATA_PHOSPHORUS:
			// 数据：总磷
			this.streamspeed(context, new IUpPacket.SampleData(sn, bytes, 1));
			break;
		default:
			this.other(context, new IUpPacket.Other(func, sn, bytes));
			break;
		}
	}

	protected void heartbeat(ChannelHandlerContext ctx, IUpPacket.Heartbeat packet) {

	}

	protected void streamspeed(ChannelHandlerContext ctx, IUpPacket.SampleData packet) {

	}

	protected void general(ChannelHandlerContext ctx, IUpPacket.SampleData packet) {

	}

	protected void phosphorus(ChannelHandlerContext ctx, IUpPacket.SampleData packet) {

	}

	protected void other(ChannelHandlerContext ctx, IUpPacket.Other packet) {

	}

	protected void illegalPacket(byte[] bytes) {
	}
}
