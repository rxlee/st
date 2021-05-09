package cn.jy.stork.hardware.packet.down;

/**
 * 上位机向下位机发送的读取测量数据的报文的骨架，具体需要扩展实现
 * 
 * @author jsh
 *
 */
public class ReadDataPacket implements __IDownPacket {

	private String name;
	private boolean maintainIgnore;
	/**
	 * 测量开关：总磷
	 */
	public static final int PHOSPHORUS = 0xA4;

	/**
	 * 测量开关：多参数
	 */
	public static final int GENERAL = 0xA1;

	/**
	 * 测量开关：流速
	 */
	public static final int STREAMSPEED = 0xA2;

	private final byte[] raw = {
			/* 0: 帧头1，固定 */
			(byte) 0x55,
			/* 1: 帧头2，固定 */
			(byte) 0xAA,
			/* 2: 数据长，固定 */
			(byte) 0x03,
			/* 3: 主指令，功能码，待设定... */
			(byte) 0xFF,
			/* 4: 辅助指令，设备号，待设定... */
			(byte) 0xFF,
			/* 5: 备用或附加信息，待设定... */
			(byte) 0x00,
			/* 6: 校验字节 */
			(byte) 0x00 };

	/**
	 * @param func   功能码
	 * @param attach 备用字段
	 */
	protected ReadDataPacket(String name, int func, boolean maintainIgnore, int attach) {
		this.name = name;
		this.maintainIgnore = maintainIgnore;
		raw[3] = (byte) (0xFF & func);
		raw[5] = (byte) (0xFF & attach);
	}

	@Override
	public final String name() {
		return name;
	}

	@Override
	public final byte[] raw(int sn) {
		raw[4] = (byte) (0xFF & sn);
		raw[6] = (byte) ((raw[3] + raw[4] + raw[5]) & 0xFF);
		return raw;
	}

	@Override
	public final boolean maintainIgnore() {
		return maintainIgnore;
	}

	/**
	 * 取数据：多参数
	 * 
	 * @param sn
	 * @return
	 */
	public static ReadDataPacket general() {
		return new ReadDataPacket("读取多参数", GENERAL, false, 0);
	}

	/**
	 * 取数据：流速
	 * 
	 * @param sn
	 * @return
	 */
	public static ReadDataPacket streamspeed() {
		return new ReadDataPacket("读取流速", STREAMSPEED, false, 0);
	}

	/**
	 * 取数据：总磷。注意总磷先要预启动之后等待40分钟才能取数据，参见phosphorusPrepared()方法
	 * 
	 * @param sn
	 * @return
	 */
	public static ReadDataPacket phosphorus() {
		return new ReadDataPacket("读取总磷", PHOSPHORUS, false, 0x30);
	}

	/**
	 * 取数据（预备）：总磷。总磷先要启动测量之后等待40分钟才能取数据
	 * 
	 * @param sn
	 * @return
	 */
	public static ReadDataPacket phosphorusPrepared() {
		return new ReadDataPacket("预备读取总磷", PHOSPHORUS, false, 0x02);
	}

}
