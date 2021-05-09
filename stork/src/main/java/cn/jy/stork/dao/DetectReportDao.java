package cn.jy.stork.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
@Mapper
public interface DetectReportDao {
	/**
	 * 新增一个报告
	 * 
	 * @param account
	 * @return
	 */
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	@Insert({
			//
			"INSERT INTO tb_detect_report",
			//
			"(process_id,ptime,pname,pid,rid,sid,stime,charge_content) ",
			//
			"VALUES",
			//
			"( #{processId},#{ptime},#{pname},#{pid},#{rid},#{sid},#{stime},#{chargeContent})"
			//
	})	
	int insertReport(DetectReport dr);
}
