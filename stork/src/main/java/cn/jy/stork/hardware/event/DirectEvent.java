package cn.jy.stork.hardware.event;

import org.springframework.context.ApplicationEvent;

import cn.jy.stork.hardware.packet.down.__IDownPacket;

public class DirectEvent extends ApplicationEvent {

	private static final long serialVersionUID = -336308379821262649L;
	private Integer sn;
	private __IDownPacket packet;

	/**
	 * 向指定水站发送指定报文
	 * 
	 * @param source
	 * @param sn      水站sn
	 * @param packets 要发送的报文
	 */
	public DirectEvent(Object source, Integer sn, __IDownPacket packet) {
		super(source);
		this.sn = sn;
		this.packet = packet;
	}

	/**
	 * 不指定水站，向全部在线水站发送报文
	 * 
	 * @param source
	 * @param packets
	 */
	public DirectEvent(Object source, __IDownPacket packet) {
		this(source, null, packet);
	}

	/**
	 * 要驱动的水站的sn，如果为null，则表示驱动全部在线水站
	 * 
	 * @return
	 */
	public Integer sn() {
		return sn;
	}

	/**
	 * 要下发的报文
	 * 
	 * @return
	 */
	public __IDownPacket getPacket() {
		return packet;
	}
}
