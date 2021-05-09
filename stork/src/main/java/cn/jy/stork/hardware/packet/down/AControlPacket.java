package cn.jy.stork.hardware.packet.down;

/**
 * 上位机向下位机发送的终端控制（包括储水和传感器的开闭）报文的骨架，具体需要扩展实现
 * 
 * @author jsh
 *
 */
abstract class AControlPacket implements __IDownPacket {
	private String name;

	private boolean maintainIgnore;

	private final byte[] raw = {
			/* 0: 帧头1，固定 */
			(byte) 0x55,
			/* 1: 帧头2，固定 */
			(byte) 0xAA,
			/* 2: 数据长，固定 */
			(byte) 0x05,
			/* 3: 主指令，功能码，固定 */
			(byte) 0xA0,
			/* 4: 辅助指令，设备号，待设定... */
			(byte) 0xFF,
			/* 5: 动作类型，待设定... */
			(byte) 0xFF,
			/* 6: 目标储水瓶 */
			(byte) 0x00,
			/* 7: 传感器动作 */
			(byte) 0x00,
			/* 8: 校验字节 */
			(byte) 0x00 };

	/**
	 * @param name           报文名称
	 * @param op             动作类型
	 * @param maintainIgnore 是否无视维护状态（true则维护状态也能下发，否则维护状态不可下发）
	 * @param storage        储水瓶控制
	 * @param switcher       继电器开关控制
	 */
	protected AControlPacket(String name, int op, boolean maintainIgnore, byte storage, byte switcher) {
		raw[5] = (byte) op;
		raw[6] = storage;
		raw[7] = switcher;
	}

	@Override
	public final String name() {
		return name;
	}

	@Override
	public final boolean maintainIgnore() {
		return maintainIgnore;
	}

	@Override
	public byte[] raw(int sn) {
		raw[4] = (byte) (0xFF & sn);
		raw[8] = (byte) ((raw[3] + raw[4] + raw[5] + raw[6] + raw[7]) & 0xFF);
		return raw;
	}

}
