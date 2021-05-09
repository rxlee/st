package cn.jy.stork.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SampleRecordDao {

	/**
	 * 新增
	 * 
	 * @param r
	 */
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	@Insert({
			//
			"INSERT INTO tb_sample_record "
			//
			, "	(stime,rid,rname,ralias,sid,sname,salias,bottle,detect_id,",
			//
			"	scode,discharge_start,discharge_end,photo)"
			//
			, " VALUES"
			//
			, " (#{stime},#{rid},#{rname},#{ralias},#{sid},#{sname},#{salias},#{bottle},#{detectId},",
			//
			"	#{scode},#{dischargeStart},#{dischargeEnd},#{photo})"
			//
	})
	public int insert(SampleRecord r);

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	@Select("SELECT * FROM tb_sample_record WHERE id=#{id} limit 1")
	SampleRecord selectById(@Param("id") Integer id);


	/**
	 * 查询全部。后续需要升级为条件查询
	 * 
	 * @param regionId
	 * @return
	 */
	@Select("SELECT * FROM tb_sample_record")
	List<SampleRecord> select();
	
	/**
	 * 根据辖区id查询（全部，包括已送检和未送检）
	 * 
	 * @param regionId
	 * @return
	 */
	@Select("SELECT * FROM tb_sample_record WHERE rid=#{regionId}")
	List<SampleRecord> selectByRegionId(@Param("regionId") Integer regionId);

	/**
	 * 根据辖区id查询未送检的
	 * 
	 * @param regionId
	 * @return
	 */
	@Select("SELECT * FROM tb_sample_record WHERE rid=#{regionId} AND detect_id IS NULL")
	List<SampleRecord> selectUndetectedByRegionId(@Param("regionId") Integer regionId);

	/**
	 * 根据辖区id查询已送检的
	 * 
	 * @param regionId
	 * @return
	 */
	@Select("SELECT * FROM tb_sample_record WHERE rid=#{regionId} AND detect_id IS NOT NULL")
	List<SampleRecord> selectDetectedByRegionId(@Param("regionId") Integer regionId);

	/**
	 * 修改采样记录中的送检记录字段（表示已经送检）
	 * 
	 * @param id
	 * @param detectId
	 * @return
	 */
	@Update({
			//
			"<script> UPDATE tb_sample_record SET detect_id=#{detectId} ",
			//
			"WHERE id=#{id}</script>"
			//
	})
	public Integer updateDetectId(@Param("id") Integer id, @Param("detectId") Integer detectId);

}
