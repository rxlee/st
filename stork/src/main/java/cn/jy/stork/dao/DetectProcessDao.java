package cn.jy.stork.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface DetectProcessDao {

	/**
	 * 新增一个送检任务
	 * 
	 * @param account
	 * @return
	 */
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	@Insert({
			//
			"INSERT INTO tb_detect_process",
			//
			"(code",
			//
			"	,sample_rid,sample_rname,sample_ralias,sample_sid,sample_sname,sample_salias,sample_time",
			//
			"	,dispatch_uid,dispatch_uname,dispatch_time",
			//
			"	,delivery_uid,delivery_uname",
			//
			"	,charge_cid,charge_cname,charge_calias",
			//
			",step) ",
			//
			"VALUES",
			//
			"( #{code}",
			//
			"	,#{sampleRid},#{sampleRname},#{sampleRalias},#{sampleSid},#{sampleSname},#{sampleSalias},#{sampleTime}",
			//
			"	,#{dispatchUid},#{dispatchUname},#{dispatchTime}",
			//
			"	,#{deliveryUid},#{deliveryUname}",
			//
			"	,#{chargeCid},#{chargeCname},#{chargeCalias}",
			//
			",#{step})"
			//
	})
	int insert(DetectProcess d);

	

	/**
	 * 所有（实际不会使用，进开发期间用）
	 * 
	 * @param telephone
	 * @return
	 */
	@Select("SELECT * FROM tb_detect_process")
	List<DetectProcess> selectAll();

	/**
	 * 通过id查询process
	 * @param detectProcessId 
	 * 
	 * @param 
	 * @return
	 */
	@Select("SELECT * FROM tb_detect_process WHERE id=#{detectProcessId}")
	DetectProcess selecProcesstById(Integer detectProcessId);
	
	/**
	 * 用送样人id查找任务
	 * 
	 * @param telephone
	 * @return
	 */
	@Select("SELECT * FROM tb_detect_process WHERE delivery_uid=#{id}")
	List<DetectProcess> selectByDeliveryUid(Integer id);

	/**
	 * 用检测人id查找任务
	 * 
	 * @param telephone
	 * @return
	 */
	@Select("SELECT * FROM tb_detect_process WHERE charge_uid=#{id}")
	List<DetectProcess> selectByTestUid(Integer id);
	/**
	 * 根据任务id确认取样
	 * @param process
	 * @param detectProcessId
	 */
	@Update({
		"<script> UPDATE tb_detect_process SET id=id "
		+ "<when test='process.step!=null'>,step=#{process.step}</when>"
		+ "<when test='process.deliveryTime!=null'>,delivery_time=#{process.deliveryTime}</when>",
		//
		"WHERE id=#{detectProcessId}</script>"
		//
	})
	void sampleSuccess(DetectProcess process,Integer detectProcessId);
	/**
	 * 根据任务id确认送样
	 * @param process
	 * @param detectProcessId
	 */
	@Update({
		"<script> UPDATE tb_detect_process SET id=id "

		+ "<when test='process.deliveryArrivedTime!=null'>,delivery_arrived_time=#{process.deliveryArrivedTime}</when>"
		+ "<when test='process.chargeCid!=null'>,charge_cid=#{process.chargeCid}</when>"
		+ "<when test='process.chargeCname!=null'>,charge_cname=#{process.chargeCname}</when>"
		+ "<when test='process.chargeCalias!=null'>,charge_calias=#{process.chargeCalias}</when>"
		+ "<when test='process.chargeUid!=null'>,charge_uid=#{process.chargeUid}</when>"
		+ "<when test='process.chargeUname!=null'>,charge_uname=#{process.chargeUname}</when>"
				+" WHERE id=#{detectProcessId}</script>"
		//
	})
	void deliverySuccess(DetectProcess process, Integer detectProcessId);

	/**
	 * 确认收到
	 * @param process
	 * @param detectProcessId 
	 */
	@Update({
		"<script> UPDATE tb_detect_process SET id=id "
				+ "<when test='process.step!=null'>,step=#{process.step}</when>"
		+ " <when test='process.chargeAcceptTime!=null'>,charge_accept_time=#{process.chargeAcceptTime}</when>"
		+ " WHERE id=#{detectProcessId}</script>"
	})
	void acceptSuccess(DetectProcess process, Integer detectProcessId);
	
	/**
	 * 检验完成
	 * @param process
	 * @param detectProcessId
	 */
	@Update({
		"<script> UPDATE tb_detect_process SET id=id "
				+ "<when test='process.step!=null'>,step=#{process.step}</when>"
		+ " <when test='process.chargeCompleteTime!=null'>,charge_complete_time=#{process.chargeCompleteTime}</when>"
		+ " WHERE id=#{detectProcessId}</script>"
	})
	void chargeComplete(DetectProcess process, Integer detectProcessId);

	



}
