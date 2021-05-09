package cn.jy.stork.hardware.packet.down;

/**
 * 开锁的指令，不需要停止指令，会延时自动关闭
 * 
 * @author jsh
 *
 */
public class LockReleasePacket extends AControlPacket {

	/**
	 * 开锁报文
	 */
	public LockReleasePacket() {
		super("开锁", 0x11, true, (byte) 0, (byte) 1);
	}

}
