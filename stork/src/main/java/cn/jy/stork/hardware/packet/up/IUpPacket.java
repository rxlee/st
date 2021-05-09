package cn.jy.stork.hardware.packet.up;

import cn.jy.stork.hardware.util.BytesUtil;

/**
 * 收到的下位机上送报文的封装类
 * 
 * @author jsh
 *
 */
public interface IUpPacket {
	/**
	 * 报文最大长度
	 */
	public static final int MAX_FRAME_LENGTH = 1024;

	/**
	 * 报文长度字段的偏移（按字节）
	 */
	public static final int LENGTH_FIELD_OFFSET = 2;

	/**
	 * 报文长度字段的长度（字节数）
	 */
	public static final int LENGTH_FIELD_LENGTH = 1;

	/**
	 * 报文长度的调整量
	 */
	public static final int LENGTH_ADJUSTMENT = 1;

	/**
	 * 初始跳过的字节数（无）
	 */
	public static final int INITIAL_BYTES_TO_STRIP = 0;

	/**
	 * 报文中数据长的字节索引位
	 */
	public static final int INDEX_OF_LEN = 2;
	/**
	 * 报文中功能码的字节索引位
	 */
	public static final int INDEX_OF_FUNC = 3;

	/**
	 * 报文中设备水站号的字节索引位
	 */
	public static final int INDED_OF_STATION = 4;

	/**
	 * 功能码：定时心跳，同时携带设备及储水瓶状态等信息
	 */
	static final int FUNC_HEARTBEAT = 0xA0;
	/**
	 * 功能码：数据上送：多参数
	 */
	static final int FUNC_DATA_GENERAL = 0xA1;
	/**
	 * 功能码：数据上送：流速仪
	 */
	static final int FUNC_DATA_STREAMSPEED = 0xA2;
	/**
	 * 功能码：数据上送：总磷
	 */
	static final int FUNC_DATA_PHOSPHORUS = 0xA4;

	public int sn();

	/**
	 * 心跳报文的封装类
	 * 
	 * @author jsh
	 *
	 */
	public final static class Heartbeat implements IUpPacket {
		private int sn;
		private boolean[] storages;
		private boolean[] switchers;

		Heartbeat(int sn, byte[] b) {
			this.sn = sn;
			storages = new boolean[] { (0x01 & b[5]) > 0, (0x02 & b[5]) > 0, (0x04 & b[5]) > 0, (0x08 & b[5]) > 0,
					(0x10 & b[5]) > 0, (0x20 & b[5]) > 0 };
			switchers = new boolean[] { (0x01 & b[6]) > 0, (0x02 & b[6]) > 0, (0x04 & b[6]) > 0, (0x08 & b[6]) > 0,
					(0x10 & b[6]) > 0, (0x20 & b[6]) > 0 };
		}

		public boolean[] getStorages() {
			return storages;
		}

		public boolean[] getSwitchers() {
			return switchers;
		}

		@Override
		public int sn() {
			return sn;
		}
	}

	/**
	 * 采样数据报文的封装类
	 * 
	 * @author jsh
	 *
	 */
	public static class SampleData implements IUpPacket {
		private Float[] datas;
		private int sn;

		protected SampleData(int sn, byte[] bytes, int dataLength) {
			this.sn = sn;
			this.datas = new Float[dataLength];
			for (int i = 0; i < datas.length; i++) {
				datas[i] = BytesUtil.floatInBytes(bytes, 5 + (i * 4));
			}
		}

		public final Float[] getDatas() {
			return datas;
		}

		@Override
		public int sn() {
			return sn;
		}

	}

	/**
	 * 其他报文的封装类
	 * 
	 * @author jsh
	 *
	 */
	public final static class Other implements IUpPacket {

		private int sn;
		private int func;
		private byte[] bytes;

		Other(int func, int sn, byte[] bytes) {
			this.func = func;
			this.sn = sn;
			this.bytes = bytes;
		}

		public byte[] getBytes() {
			return bytes;
		}

		public int getFunc() {
			return func;
		}

		@Override
		public int sn() {
			return sn;
		}

	}

}
