package cn.jy.stork.hardware.packet.down;

/**
 * 储水瓶全部重置的指令，表示人工更换了全新的瓶子
 * 
 * @author jsh
 *
 */
public class StorageResetPacket extends AControlPacket {

	public StorageResetPacket() {
		// 无视维护状态 maintainIgnore=true
		super("储水瓶重置", 0x10, true, (byte) 0x80, (byte) 0);
	}

}
