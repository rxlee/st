package cn.jy.stork.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface RegionDao {

	/**
	 * 新增辖区
	 * 
	 * @param r
	 */
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	@Insert({ "INSERT INTO tb_region (name,alias) VALUES (#{name},#{alias})" })
	public int insertRegion(Region r);

	/**
	 * 新增辖区地理范围，操作的是 tb_region_outline 表，一般新增辖区时就同时新增对应的记录（outline字段空白）
	 * 
	 * @param id
	 * @param outline
	 */
	@Update("INSERT INTO tb_region_outline (rid,outline) VALUES (#{rid},#{outline})")
	public Integer insertRegionOutline(int rid, String outline);

	/**
	 * 新增辖区负责人员
	 * 
	 * @param dc
	 */
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	@Insert({
			//
			"INSERT INTO tb_region_staff (uid,uname,uphone,utype,rid) ",
			//
			"VALUES (#{uid},#{uname},#{uphone},#{utype},#{rid})"
			//
	})
	public int insertStaff(RegionStaff dc);

	/**
	 * 根据id查询辖区
	 * 
	 * @param id
	 * @return
	 */
	@Select("SELECT * FROM tb_region WHERE id=#{id} limit 1")
	Region selectRegionById(Integer id);

	/**
	 * 根据人员id查询其所在辖区
	 * 
	 * @param staffId
	 * @return
	 */
	@Select("SELECT * FROM tb_region WHERE id=(SELECT rid FROM tb_region_staff WHERE uid=#{staffId}) limit 1")
	Region selectRegionByStaffId(Integer staffId);

	/**
	 * 根据账号id查询该账号在辖区中的任职信息（可知其任职于哪个辖区、什么职责）
	 * 
	 * @param id
	 * @return
	 */
	@Select("SELECT * FROM tb_region_staff WHERE uid=#{id} limit 1")
	RegionStaff selectStaffByUid(Integer uid);

	/**
	 * 根据辖区id查询辖区内的人员
	 * 
	 * @param regionId
	 * @return
	 */
	@Select("SELECT * FROM tb_region_staff WHERE rid=#{regionId}")
	List<RegionStaff> selectStaffsInRegion(Integer regionId);

	/**
	 * 根据辖区id查询辖区地理范围
	 * 
	 * @param regionId
	 * @return
	 */
	@Select({
			//
			"SELECT r.id,r.name,r.alias,o.outline ",
			//
			"	FROM tb_region r LEFT JOIN tb_region_outline o ON r.id=o.rid",
			//
			"	WHERE r.id=#{regionId}"
			//
	})
	RegionWithOutline selectOutlineOfRegion(Integer regionId);

	/**
	 * 查询所有辖区
	 * 
	 * @param
	 * @return
	 */
	@Select("SELECT * FROM tb_region")
	List<Region> selectAllRegions();

	/**
	 * 修改辖区基本信息
	 * 
	 * @param id
	 * @param name
	 * @param alias
	 */
	@Update({
			//
			"<script> UPDATE tb_region SET id=id ",
			//
			"<when test='name!=null'>,name=#{name}</when>",
			//
			"<when test='alias!=null'>,alias=#{alias}</when>",
			//
			"WHERE id=#{id}</script>"
			//
	})
	public Integer updateProfile(int id, String name, String alias);

	/**
	 * 修改辖区地理范围，操作的是 tb_region_outline 表
	 * 
	 * @param id
	 * @param outline
	 */
	@Update({
			//
			"UPDATE tb_region_outline SET ",
			//
			"	outline=#{outline}",
			//
			"WHERE rid=#{rid}"
			//
	})
	public Integer updateRegionOutline(int rid, String outline);

	/**
	 * 删除辖区内的人员（不会删除人员账号，只是取消人员在辖区内的任职）
	 * 
	 * @param id
	 * 
	 */
	@Select("DELETE FROM tb_region_staff WHERE rid=#{regionId} AND uid=#{staffId}")
	public Integer deleteStaffInRegion(Integer regionId, Integer staffId);
}
