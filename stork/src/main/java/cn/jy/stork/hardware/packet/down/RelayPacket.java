package cn.jy.stork.hardware.packet.down;

/**
 * 传感器上电/下电的指令，实际上是启动或关闭继电器，还包括一个蓄水泵的启动和停止，所以类名叫RelayPacket
 * 
 * @author jsh
 *
 */
public class RelayPacket extends AControlPacket {

	/**
	 * 测量开关：总磷
	 */
	public static final int P = 0x12;

	/**
	 * 测量开关：多参数
	 */
	public static final int GENERAL = 0x13;

	/**
	 * 测量开关：流速
	 */
	public static final int STREAMSPEED = 0x14;

	/**
	 * 蓄水泵开关：给采样缓冲池换水的
	 */
	public static final int PUMP = 0x15;

	/**
	 * @param relay
	 * @param start
	 */
	private RelayPacket(String name, int relay, boolean start) {
		// 维护状态下可以随意执行关闭操作，但无法在维护状态下开启任何继电器（柜门锁有专门的报文，不受这个影响）
		// 所以当start是false则maintainIgnore就是true
		super(name, relay, !start, (byte) 0, (byte) (start ? 1 : 0));
	}

	/**
	 * 总磷测量开关
	 * 
	 * @param sn
	 * @param start
	 * @return
	 */
	public static RelayPacket p(boolean start) {
		return new RelayPacket(start ? "总磷上电" : "总磷下电", P, start);
	}

	/**
	 * 多参数测量开关
	 * 
	 * @param sn
	 * @param start
	 * @return
	 */
	public static RelayPacket general(boolean start) {
		return new RelayPacket(start ? "多参数上电" : "多参数下电", GENERAL, start);
	}

	/**
	 * 流速测量开关
	 * 
	 * @param sn
	 * @param start
	 * @return
	 */
	public static RelayPacket streamspeed(boolean start) {
		return new RelayPacket(start ? "流速上电" : "流速下电", STREAMSPEED, start);
	}

	/**
	 * 蓄水泵开关
	 * 
	 * @param sn
	 * @param start
	 * @return
	 */
	public static RelayPacket pump(boolean start) {
		return new RelayPacket(start ? "蓄水泵上电" : "蓄水泵下电", PUMP, start);
	}

}
