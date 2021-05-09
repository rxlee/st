package cn.jy.stork.hardware.packet.down;

/**
 * 储水瓶开始储水的指令，不需要停止指令，满了会自动停止
 * 
 * @author jsh
 *
 */
public class StorageFillPacket extends AControlPacket {

	/**
	 * @param bottle 瓶号1~6
	 */
	public StorageFillPacket(int bottle) {
		// 维护状态下无法执行， maintainIgnore=false
		super("储水瓶进水", 0x10, false, (byte) (1 << (bottle - 1)), (byte) 0);
	}

}
