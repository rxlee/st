package cn.jy.stork.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RealtimeDataDao {

	/**
	 * 通用条件查询
	 * 
	 * @param stationId 水站id
	 * @param f         查询起始时间
	 * @param to        查询截止时间
	 * @param asc       是否正序
	 * @return
	 */
	@Select({
			//
			"<script>",
			//
			"SELECT * FROM tb_realtime_data WHERE ",
			//
			"	rtime&gt;=#{from} AND rtime&lt;=#{to} ",
			//
			" <when test='sid != null'> AND sid=#{sid}</when>",
			//
			"<when test='o == true'> ORDER BY rtime</when>",
			//
			"<when test='o == false'> ORDER BY rtime DESC</when>",
			//
			"<when test='pageSize != null'>LIMIT #{pageIndex},#{pageSize}</when>",
			//
			"</script>"
			//
	})
	List<RealtimeData> find(@Param("sid") Integer stationId, @Param("from") Date f, @Param("to") Date to,
			@Param("pageSize") Integer pageSize, @Param("pageIndex") Integer pageIndex, @Param("o") boolean asc);

	/**
	 * 插入实时数据
	 * 
	 * @param account
	 */
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	@Insert({
			//
			"INSERT INTO tb_realtime_data",
			//
			"(rtime,sid,",
			//
			"	t,d,s,p,cond,oxy,ph,nh,turb,cod",
			//
			"	year,month,day)",
			//
			"VALUES",
			//
			"	(#{r.rtime},#{r.sid}",
			//
			"	,#{r.t},#{r.d},#{r.s},#{r.p},#{r.cond},#{r.oxy},#{ph},#{nh},#{turb},#{cod}",
			//
			"	,#{r.year},#{r.month},#{r.day})"
			//
	})
	int insert(@Param("r") RealtimeData data);

	/**
	 * 批量插入实时数据
	 * 
	 * @param account
	 */
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	@Insert({
			//
			"<script>",
			//
			"INSERT INTO tb_realtime_data",
			//
			"	(rtime,sid,t,d,s,p,cond,oxy,ph,nh,turb,cod,year,month,day)",
			//
			"	VALUES",
			//
			"<foreach collection='list' item='r' separator=','>",
			//
			"(#{r.rtime},#{r.sid},#{r.t},#{r.d},#{r.s},#{r.p}",
			//
			",#{r.cond},#{r.oxy},#{r.ph},#{r.nh},#{r.turb},#{r.cod},#{r.year},#{r.month},#{r.day})",
			//
			"</foreach></script>"
			//
	})
	int insertBatch(@Param("list") List<RealtimeData> entities);

	/**
	 * 根据水站Id查询最新一组数据
	 * 
	 * @param id
	 * @return
	 */
	@Select("SELECT * FROM tb_realtime_data WHERE sid=#{stationId} ORDER BY rtime DESC LIMIT 1")
	RealtimeData selectNewestByStationId(int stationId);

	/**
	 * 查询所有站的最新数据
	 * 
	 * @return
	 */
	@Select({
			//
			"SELECT r.* FROM",
			//
			"  (SELECT sid,MAX(rtime) rtime FROM tb_realtime_data GROUP BY sid) g,",
			//
			"  tb_realtime_data r",
			//
			"WHERE r.sid=g.sid AND r.rtime=g.rtime"
			//
	})
	List<RealtimeData> selectAllNewest();

//	/**
//	 * 查询stationid
//	 * 
//	 * @return
//	 */
//	@Select({
//			//
//			"SELECT distinct sid FROM tb_realtime_data"
//			//
//	})
//	List<Integer> findId();
//
//	/**
//	 * 查询id最后两条数据
//	 * 
//	 * @param id
//	 * 
//	 * @return
//	 */
//	@Select({
//			//
//			"SELECT * from tb_realtime_data WHERE sid= #{stationId} ORDER BY rtime DESC LIMIT 2  "
//			//
//	})
//	List<RealtimeData> last2(Integer stationId);
}
