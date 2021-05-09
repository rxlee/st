package cn.jy.stork.util;

public interface StorkConst {
	public static interface RegionStaffType {
		int RIVER_CHIEF = 1;
		int ADMINISTRA = 2;
		int PATROL = 3;
		int OFFICER = 4;
	}

	public static interface RegionStaffRole {
		String RIVER_CHIEF = "river-chief";
		String ADMINISTRA = "region-administra";
		String PATROL = "region-patrol";
		String OFFICER = "region-officer";
	}

	public static interface CommonRole {
		String ADMIN = "admin";
		String MANAGER = "manager";
		String GUEST = "guest";
	}

	public static interface DetectStaffRole {
		String DELIVERY = "sample-delivery";
		String TEST = "sample-test";
	}
}
