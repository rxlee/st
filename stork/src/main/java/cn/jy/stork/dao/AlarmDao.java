package cn.jy.stork.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AlarmDao {

	/**
	 * 插入一条报警
	 * 
	 * @param stationId
	 * @param data
	 * @param indicator
	 * @param summary
	 * @param date
	 * @return
	 */
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	@Insert({
			//
			"INSERT INTO tb_alarm",
			//
			"(station_id,main_type,sub_type,summary,alarm_time) ",
			//
			"VALUES",
			//
			"(#{stationId},#{mainType},#{subType},#{summary},#{alarmTime});"
			//
	})
	int insert(Integer stationId, String mainType, String subType, String summary, Date alarmTime);

	/**
	 * 综合条件查询
	 * 
	 * @param sid       非必填。水站id，若未null则查询所有
	 * @param from      必填。起始日期，调用者必须设置值
	 * @param to        必填。截止日期，调用者必须设置值
	 * @param pageIndex 必填。页码
	 * @param pageSize  必填。每页条数
	 * @return
	 */
	@Select({
			//
			"<script> SELECT * FROM tb_alarm WHERE",
			//
			" alarm_time&gt;#{from} AND alarm_time&lt;#{to}",
			//
			"<when test='sid!=null'>",
			//
			" AND station_id=#{sid}",
			//
			"</when>",
			//
			" ORDER BY alarm_time DESC",
			//
			" LIMIT #{pi},#{ps} </script>",
			//
	})
	List<Alarm> select(@Param("sid") Integer sid, @Param("from") Date from, @Param("to") Date to,
			@Param("pi") Integer pageIndex, @Param("ps") Integer pageSize);
}
