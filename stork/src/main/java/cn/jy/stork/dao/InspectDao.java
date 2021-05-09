package cn.jy.stork.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface InspectDao {
	/**
	 * 新增巡查情况
	 * 
	 * @param i
	 */
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	@Insert({ "INSERT INTO tb_inspection (utime,uid,umemo,rid,step) VALUES (#{utime},#{uid},#{umemo},#{rid},#{step})" })
	public int addInspect(Inspect i);

	/**
	 * 通过辖区id查询巡查情况
	 * @param regionId
	 * @return
	 */
	@Select("SELECT * FROM tb_inspection WHERE rid=#{regionId}")
	public List<Inspect> selectInspectByRegionId(Integer regionId);

	/**
	 * 河长审批
	 * @param id
	 * @param i
	 * @return
	 */
	@Update({
		"<script> UPDATE tb_inspection SET id=id"
		+ "<when test='i.atime!=null'>,atime=#{i.atime}</when>"
		+ "<when test='i.aid!=null'>,aid=#{i.aid}</when>"
		+ "<when test='i.amemo!=null'>,amemo=#{i.amemo}</when>"
		+ "<when test='i.step!=null'>,step=#{i.step}</when>"
		+ "WHERE id=#{id}</script>"
	})
	public int approveInspect(int id, Inspect i);

	/**
	 * 处理结果
	 * @param id
	 * @param i
	 * @return
	 */
	@Update({
		"<script> UPDATE tb_inspection SET id=id"
		+ "<when test='i.result!=null'>,result=#{i.result}</when>"
		+ "<when test='i.step!=null'>,step=#{i.step}</when>"
		+ "WHERE id=#{id}</script>"
	})	
	public int inspectResult(int id, Inspect i);
	
	
}
