package cn.jy.stork.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.jy.stork.biz.pojo.DetectCorpPojo;

@Mapper
public interface DetectCorpDao {

	/**
	 * 新增检测机构
	 * 
	 * @param dc
	 */
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	@Insert({
			//
			"INSERT INTO tb_detect_corp",
			//
			"(name,alias,address,category,status) ",
			//
			"VALUES",
			//
			"(#{name},#{alias},#{address},#{category},#{status})"
			//
	})
	int insert(DetectCorp dc);

	/**
	 * 新增检测机构人员
	 * 
	 * @param entity
	 */
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	@Insert({
			//
			"INSERT INTO tb_detect_staff",
			//
			"(uid,uname,uphone,utype,cid,cname,calias) ",
			//
			"VALUES",
			//
			"(#{uid},#{uname},#{uphone},#{utype},#{cid},#{cname},#{calias})"
			//
	})
	int insertStaff(DetectStaff ds);

	/**
	 * 查询所有检测机构
	 * 
	 * @param id
	 * @return
	 */
	@Select("SELECT * FROM tb_detect_corp ")
	List<DetectCorp> selectAllCorps();

	/**
	 * 根据id查询检测机构
	 * 
	 * @param id
	 * @return
	 */
	@Select("SELECT * FROM tb_detect_corp WHERE id=#{id} limit 1")
	DetectCorp selectCorpById(Integer id);

	/**
	 * 根据辖区id查询合同
	 * 
	 * @param id
	 * @return
	 */
	@Select("SELECT * FROM tb_detect_contract WHERE rid=#{regionId} ")
	List<DetectContract> selectContractsByRegionId(Integer regionId);

	/**
	 * 根据机构id查询人员
	 * 
	 * @param id
	 * @return
	 */
	@Select("SELECT * FROM tb_detect_staff WHERE cid=#{id} ")
	List<DetectStaff> selectStaffsByCorpId(Integer CorpId);

	/**
	 * 根据检测机构id查询取样送样人员
	 * 
	 * @param id
	 * @return
	 */
	@Select("SELECT * FROM tb_detect_staff WHERE cid=#{corpId} AND utype=1")
	List<DetectExecutor> selectDeliveriesInCorp(Integer corpId);

	/**
	 * 根据人员id查询所在检测机构
	 * 
	 * @param id
	 * @return
	 */
	@Select({
			//
			"SELECT * FROM tb_detect_corp ",
			//
			"WHERE id=(SELECT cid FROM tb_detect_staff WHERE uid=#{staffId}) ",
			//
			"LIMIT 1"
			//
	})
	DetectCorp selectCorpByStaff(@Param("staffId") Integer staffId);

	/**
	 * 通过送样人id查询检验人id
	 * @param staffId
	 * @return
	 */
	@Select({
		"SELECT * FROM tb_detect_staff"
		+ " WHERE cid=(SELECT cid FROM tb_detect_staff WHERE uid=#{staffId})  AND utype=2"
		+ " LIMIT 1"
	})
	List<DetectStaff> selectTestByStaff(Integer staffId);
	
	/**
	 * 根据辖区id查询取样送样人员
	 * 
	 * @param id
	 * @return
	 */
	@Select({
			//
			"SELECT staff.* FROM ",
			//
			"	(SELECT ex.uid uid,ex.uname uname,ex.uphone uphone,con.rid rid,con.cid cid,con.cname cname ",
			//
			"	FROM tb_detect_executor ex ",
			//
			"	LEFT JOIN tb_detect_contract con ON ex.cid=con.id) staff ",
			//
			"WHERE rid=#{regionId}"
			//
	})
	List<DetectExecutorEx> selectDeliveriesInRegion(@Param("regionId") Integer regionId);

	/**
	 * 删除检测机构人员
	 * 
	 * @param staffId
	 * 
	 */
	@Select("DELETE FROM tb_detect_staff WHERE cid=#{corpId} AND uid=#{staffId}")
	void delectStaffFromCorp(Integer corpId, Integer staffId);

	/**
	 * 修改检测机构位置
	 * 
	 * @param id
	 * @param pojo
	 */
	@Update(
	//
	{ "<script> UPDATE tb_detect_corp SET id=id ",
			//
			"<when test='lat!=null'>,lat=#{lat}</when>",
			//
			"<when test='lng!=null'>,lng=#{lng}</when>",
			//
			"WHERE id=#{id}</script>"
			//
	})
	int updateCorpLocate(int id, Float lng, Float lat);

	/**
	 * 修改检测机构基本信息
	 * 
	 * @param id
	 * @param pojo
	 */
	@Update({
			//
			"<script> UPDATE tb_detect_corp SET id=id ",
			//
			"<when test='pojo.name!=null'>,name=#{pojo.name}</when>",
			//
			"<when test='pojo.alias!=null'>,alias=#{pojo.alias}</when>",
			//
			"<when test='pojo.address!=null'>,address=#{pojo.address}</when>",
			//
			"<when test='pojo.category!=null'>,category=#{pojo.category}</when>",
			//
			"WHERE id=#{id}</script>"
			//
	})
	int updateCorpProfile(int id, DetectCorpPojo pojo);

	/**
	 * 修改检测机构状态
	 * 
	 * @param id
	 * @param pojo
	 */
	@Update({
			//
			"<script> UPDATE tb_detect_corp SET id=id ",
			//
			"<when test='status!=null'>,status=#{status}</when>",
			//
			"WHERE id=#{id}</script>"
			//
	})
	int updateCorpStatus(int id, Integer status);



}
