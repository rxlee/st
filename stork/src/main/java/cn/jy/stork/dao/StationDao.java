package cn.jy.stork.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.jy.stork.biz.pojo.StationLocateModifyPojo;
import cn.jy.stork.biz.pojo.StationProfilePojo;

@Mapper
public interface StationDao {

	/**
	 * 新增水站
	 * 
	 * @param station
	 */
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	@Insert({
			//
			"INSERT INTO tb_station (name,alias,sn,memo,code,rid,rname,vsn,vtoken) ",
			//
			" VALUES (#{s.name},#{s.alias},#{s.sn},#{s.memo},#{s.code},#{s.rid},#{s.rname},#{s.vsn},#{s.vtoken})"
			//
	})
	int insert(@Param("s") Station station);

	/**
	 * 查询所有
	 *
	 * @param
	 * @return
	 */
	@Select({ "SELECT * FROM tb_station" })
	List<Station> selectAll();

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	@Select("SELECT * FROM tb_station WHERE id=#{id}")
	Station selectById(Integer id);

	/**
	 * 根据sn查询
	 * 
	 * @param id
	 * @return
	 */
	@Select("SELECT * FROM tb_station WHERE sn=#{sn} limit 1")
	Station selectBySn(Integer sn);

	/**
	 * 根据sn判断数据存在
	 * 
	 * @param sn
	 * @return
	 */
	@Select("SELECT COUNT(id)>0 FROM tb_station WHERE sn=#{sn}")
	Boolean existsBySn(Integer sn);

	/**
	 * 根据辖区id查询
	 * 
	 * @param id
	 * @return
	 */
	@Select("SELECT * FROM tb_station WHERE rid=#{regionId}")
	List<Station> selectByRegionId(@Param("regionId") Integer regionId);

	/**
	 * 根据id删除一个水站
	 * 
	 * @param id
	 */
	@Delete("DELETE FROM tb_station WHERE id=#{id}")
	void deleteById(int id);

	/**
	 * 更新水站基本信息
	 *
	 * @param id
	 * @param pojo
	 * @return
	 */
	@Update({
			//
			"<script> UPDATE tb_station SET id=id ",
			// 如果输入了name，则刷新name
			"<when test='s.name != null'> ,name=#{s.name} </when>",
			// 如果输入了alias，则刷新alias
			"<when test='s.alias != null'> ,alias=#{s.alias} </when>",
			// 如果输入了memo，则刷新memo
			"<when test='s.memo != null'> ,memo=#{s.memo} </when>",
			// 如果输入了code，则刷新code
			"<when test='s.code != null'> ,code=#{s.code} </when>",
			//
			",rid=#{s.rid},rname=#{s.rname}",
			//
			"<when test='s.sn != null'> ,sn=#{s.sn} </when>",
			//
			"<when test='s.vsn != null'> ,vsn=#{s.vsn} </when>",
			//
			"<when test='s.vtoken != null'> ,vtoken=#{s.vtoken} </when>",
			//
			" WHERE id=#{id} </script>" })
	int updateProfile(@Param("id") int id, @Param("s") StationProfilePojo pojo);

	/**
	 * 修改定位
	 * 
	 * @param id
	 * @param longitude
	 * @param latitude
	 */
	@Update({ "UPDATE tb_station SET lng=#{lng}, lat=#{lat} WHERE id=#{id}" })
	int updateLocate(@Param("id") int id, @Param("lng") Float lng, @Param("lat") Float lat);

	/**
	 * 批量修改定位
	 * 
	 */
	@Update({
			//
			"<script><foreach collection='stations' item='s' separator=';'>",
			//
			"UPDATE tb_station SET ",
			//
			" lng=#{s.lng},lat=#{s.lat}",
			//
			" WHERE id=#{s.station}",
			//
			"</foreach></script>" })
	int updateLocateBatch(@Param("stations") List<StationLocateModifyPojo> stations);

	/**
	 * 
	 * 修改某个站的维护时间
	 * 
	 * @param id
	 * @param time
	 */
	@Update("UPDATE tb_station SET maintain_time=#{time} WHERE id=#{id}")
	void maintain(@Param("id") int id, @Param("time") Date time);

	
	/**
	 * 修改水站辖区
	 * @param id
	 * @param pojo
	 */
	@Update("UPDATE tb_station SET rid=#{r.id},rname=#{r.name} WHERE id=#{id}")
	void updateRegion(int id, Region r);

}
