package cn.jy.stork.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StationStatusDao {

	/**
	 * 新增水站状态
	 * 
	 * @param station
	 */
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	@Insert({
			//
			"INSERT INTO tb_station_status (ssn) ",
			//
			" VALUES (#{s.ssn})"
			//
	})
	int insert(@Param("s") StationStatus station);

	/**
	 * 查询所有
	 *
	 * @param
	 * @return
	 */
	@Select({ "SELECT * FROM tb_station_status" })
	List<StationStatus> all();

	/**
	 * 根据sn判断数据存在
	 * 
	 * @param sn
	 * @return
	 */
	@Select("SELECT COUNT(id)>0 FROM tb_station_status WHERE ssn=#{ssn}")
	Boolean existsBySn(Integer ssn);

	/**
	 * 
	 * 整体刷新水站状态字节，包括储水瓶状态、开关状态和最近活动时间。主要用于硬件定时上报状态时调用。
	 * 
	 * switchers依次是智能锁、总磷、多参数、流速仪、蠕动泵
	 * 
	 * @param id
	 * @param recentActive
	 * @param storages
	 * @param switchers
	 */
	@Update({
			//
			"UPDATE tb_station_status SET recent_active=#{recentActive}",
			//
			",storage1=#{storages[0]},storage2=#{storages[1]},storage3=#{storages[2]}",
			//
			",storage4=#{storages[3]},storage5=#{storages[4]},storage6=#{storages[5]}",
			//
			",lock_open=#{switchers[0]}",
			//
			",general_open=#{switchers[1]}",
			//
			",phosphorus_open=#{switchers[2]}",
			//
			",stream_speed_open=#{switchers[3]}",
			//
			",pump_open=#{switchers[4]}",
			//
			" WHERE ssn=#{ssn}"
			//
	})
	int updateAllStatus(@Param("ssn") int ssn, @Param("recentActive") Date recentActive,
			@Param("storages") boolean[] storages, @Param("switchers") boolean[] switchers);

	/**
	 * 更新某站某一个储水瓶状态为满
	 *
	 * @param sn
	 * @param index
	 * @return
	 */
	@Update({
			//
			"<script>UPDATE tb_station_status SET ssn=ssn",
			//
			"<when test='index==1'> ,storage1=1 </when>",
			//
			"<when test='index==2'> ,storage2=1 </when>",
			//
			"<when test='index==3'> ,storage3=1 </when>",
			//
			"<when test='index==4'> ,storage4=1 </when>",
			//
			"<when test='index==5'> ,storage5=1 </when>",
			//
			"<when test='index==6'> ,storage6=1 </when>",
			//
			" WHERE ssn=#{ssn}",
			//
			" </script>"
			//
	})
	int fillStorage(@Param("ssn") int ssn, @Param("index") int index);

	/**
	 * 更新某站某一个储水瓶状态为空。
	 *
	 * @param sn    水站硬件号
	 * @param index
	 * @return
	 */
	@Update({ "<script>UPDATE tb_station_status SET id=id",
			//
			"<when test='index==1'> ,storage1=0 </when>",
			//
			"<when test='index==2'> ,storage2=0 </when>",
			//
			"<when test='index==3'> ,storage3=0 </when>",
			//
			"<when test='index==4'> ,storage4=0 </when>",
			//
			"<when test='index==5'> ,storage5=0 </when>",
			//
			"<when test='index==6'> ,storage6=0 </when>",
			//
			" WHERE ssn=#{ssn}" + " </script>" })
	int emptyStorage(@Param("ssn") int ssn, @Param("index") int index);

	/**
	 * 更新全部储水瓶状态为空
	 *
	 * @param index sn
	 * @return
	 */
	@Update("UPDATE tb_station_status SET storage1=0,storage2=0,storage3=0,storage4=0,storage5=0,storage6=0 WHERE ssn=#{ssn}")
	int emptyAllStorages(@Param("ssn") int ssn);

	/**
	 * 批量更新储水瓶状态（状态不全一样）
	 * 
	 * @param sn
	 * @param storages
	 */
	@Update({ "<script>UPDATE tb_station_status SET",
			//
			" storage1=#{storages[0]}",
			//
			",storage2=#{storages[1]}",
			//
			",storage3=#{storages[2]}",
			//
			",storage4=#{storages[3]}",
			//
			",storage5=#{storages[4]}",
			//
			",storage6=#{storages[5]}",
			//
			" WHERE ssn=#{ssn}" + " </script>" })
	int updateStorages(@Param("ssn") int ssn, @Param("storages") boolean[] storages);

}
