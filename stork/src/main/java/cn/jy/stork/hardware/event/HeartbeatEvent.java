package cn.jy.stork.hardware.event;

import org.springframework.context.ApplicationEvent;

import cn.jy.stork.hardware.packet.up.IUpPacket;
import io.netty.channel.Channel;

/**
 * 
 * @author jsh
 *
 */
final class HeartbeatEvent extends ApplicationEvent {

	private static final long serialVersionUID = -796887565402443764L;
	private IUpPacket.Heartbeat packet;
	private Channel channel;

	public HeartbeatEvent(Object source, Channel channel, IUpPacket.Heartbeat packet) {
		super(source);
		this.channel = channel;
		this.packet = packet;
	}

	public IUpPacket.Heartbeat getPacket() {
		return packet;
	}

	public Channel getChannel() {
		return channel;
	}

}
