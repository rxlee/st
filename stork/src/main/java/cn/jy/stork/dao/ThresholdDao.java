package cn.jy.stork.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ThresholdDao {

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	@Select("SELECT * FROM tb_threshold")
	List<Threshold> findAll();

	/**
	 * 新增记录
	 * 
	 * @param account
	 */
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	@Insert({
			//
			"INSERT INTO tb_threshold",
			//
			"(station_id,indicator,bottom,top) ",
			//
			"VALUES",
			//
			"(#{t.stationId},#{t.indicator},#{t.bottom},#{t.top})"
			//
	})
	int insert(@Param("t") Threshold threshold);

	/**
	 * 修改
	 * 
	 * @param id
	 * @param deal
	 * @param time
	 */
	@Update({
			//
			"UPDATE tb_threshold SET ",
			//
			"	bottom=#{bottom},",
			//
			"	top=#{top},",
			//
			" WHERE id=#{id}"
			//
	})
	int update(@Param("id") int id, @Param("bottom") Float bottom, @Param("top") Float top);

}