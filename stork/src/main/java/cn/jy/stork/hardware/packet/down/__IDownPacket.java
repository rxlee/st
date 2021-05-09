package cn.jy.stork.hardware.packet.down;

/**
 * 上位机下发给下位机的指令报文。实际上是报文模板，其中的设备号sn在发送前才确定，也会因此影响校验字段的值。
 * 
 * 因为是报文模板，所以拼装报文时无需指定水站的sn设备号。
 * 
 * @author jsh
 *
 */
public interface __IDownPacket {

	/**
	 * 报文的名称，不会作为下发字段，仅供业务逻辑识别
	 * 
	 * @return
	 */
	public String name();

	/**
	 * 报文字节内容
	 * 
	 * @param sn
	 * @return
	 */
	public byte[] raw(int sn);

	/**
	 * 是否无视维护状态（true则维护状态也能下发，否则维护状态不可下发）
	 * 
	 * @return
	 */
	public boolean maintainIgnore();
}
