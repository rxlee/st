package cn.jy.stork.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface DetectContractDao {

	/**
	 * 新增一份合同
	 * @param c
	 */
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	@Insert({
			//
			"INSERT INTO tb_detect_contract",
			//
			"(rid,rname,ralias,cid,cname,calias,start_time,end_time) ",
			//
			"VALUES",
			//
			"(#{rid},#{rname},#{ralias},#{cid},#{cname},#{calias},#{startTime},#{endTime})"
			//
	})
	void addContract(DetectContract c);

	/**
	 * 新增一个合同执行人（送样人员）
	 * @param cs
	 */
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	@Insert({
			//
			"INSERT INTO tb_detect_executor",
			//
			"(uid,cid,uname,uphone) ",
			//
			"VALUES",
			//
			"(#{uid},#{cid},#{uname},#{uphone})"
			//
	})
	void addExecutor(DetectExecutor cs);

}
