package cn.jy.stork.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RealtimeStreamspeedDao {

	/**
	 * 所有水站的流速上下限
	 * 
	 * @return
	 */
	@Select({
			//
			"SELECT MIN(s) bottom,MAX(s) top,station_id sid ",
			//
			"	FROM tb_realtime_streamspeed ",
			//
			"	 WHERE rtime>#{from} GROUP BY station_id"
			//
	})
	List<StreamspeedRange> selectAllRanges(Date from);

}
