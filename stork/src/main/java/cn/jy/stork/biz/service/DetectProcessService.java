package cn.jy.stork.biz.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jy.stork.biz.pojo.DetectProcessDispatchPojo;
import cn.jy.stork.biz.pojo.DetectReportPojo;
import cn.jy.stork.dao.Account;
import cn.jy.stork.dao.AccountDao;
import cn.jy.stork.dao.DetectCorp;
import cn.jy.stork.dao.DetectProcess;
import cn.jy.stork.dao.DetectProcessDao;
import cn.jy.stork.dao.DetectReport;
import cn.jy.stork.dao.DetectReportDao;
import cn.jy.stork.dao.DetectStaff;
import cn.jy.stork.dao.SampleRecord;
import cn.jy.stork.dao.SampleRecordDao;

@Service
public class DetectProcessService {

	@Autowired
	DetectProcessDao detectProcessDao;

	@Autowired
	SampleRecordDao sampleRecordDao;
	@Autowired
	AccountDao accountDao;
	@Autowired
	DetectService detectService;
	@Autowired
	DetectReportDao detectReportDao;

	private static int CODE_SEQ = 1;

	/**
	 * 派发送样任务
	 * 
	 * @param pojo
	 * @return
	 */
	public DetectProcess dispatch(DetectProcessDispatchPojo pojo) {

		final int recordId = pojo.getRecord();
		final int deliveryId = pojo.getDelivery();
		final int dispatcherId = pojo.getDispatcher();

		final SampleRecord record = sampleRecordDao.selectById(recordId);
		final Account dispatcher = accountDao.findById(dispatcherId);
		final Account delivery = accountDao.findById(deliveryId);
		final DetectCorp corp = detectService.findCorpByStaff(deliveryId);

		final DetectProcess process = new DetectProcess();

		process.setSampleRid(record.getRid());
		process.setSampleRname(record.getRname());
		process.setSampleRalias(record.getRalias());
		process.setSampleSid(record.getSid());
		process.setSampleSname(record.getSname());
		process.setSampleSalias(record.getSalias());
		process.setSampleTime(record.getStime());
		process.setSampleBottle(record.getBottle());

		process.setDispatchUid(dispatcherId);
		process.setDispatchUname(dispatcher.getName());
		process.setDispatchTime(new Date());

		process.setDeliveryUid(deliveryId);
		process.setDeliveryUname(delivery.getName());

		process.setChargeCid(corp.getId());
		process.setChargeCname(corp.getName());
		process.setChargeCalias(corp.getAlias());

		final String code = String.format("%s-%04X", new SimpleDateFormat("yyyyMMdd-HHmm").format(new Date()),
				(CODE_SEQ++) & 0xFFFF);
		process.setCode(code);
		process.setStep(DetectProcess.STEP_GETTING);

		detectProcessDao.insert(process);

		sampleRecordDao.updateDetectId(recordId, process.getId());

		return process;
	}

	/**
	 * 所有（实际不会使用，进开发期间用）
	 * 
	 * @param id
	 * @return
	 */
	public List<DetectProcess> finalAll() {
		return detectProcessDao.selectAll();
	}

	/**
	 * 根据送样人查询他的任务
	 * 
	 * @param id
	 * @return
	 */
	public List<DetectProcess> findByDelivery(Integer id) {
		return detectProcessDao.selectByDeliveryUid(id);
	}
	
	/**
	 * 根据检测人查询他的任务
	 * 
	 * @param id
	 * @return
	 */
	public List<DetectProcess> findByTestId(Integer id) {
		return detectProcessDao.selectByTestUid(id);
	}
	/**
	 * 根据任务id确认取样
	 * @param detectProcessId
	 */
	public void sampleSuccess(Integer detectProcessId) {
		DetectProcess process=new DetectProcess();
		process.setDeliveryTime(new Date());
		process.setStep(DetectProcess.STEP_SENDING);
		detectProcessDao.sampleSuccess(process,detectProcessId);
	}
	/**
	 * 根据任务id确认送样
	 * @param detectProcessId
	 * @param detectStaff 
	 */
	public void deliverySuccess(Integer detectProcessId, DetectStaff ds) {
		DetectProcess process=new DetectProcess();
		process.setDeliveryArrivedTime(new Date());
		
		process.setChargeCid(ds.getCid());
		process.setChargeCname(ds.getCname());
		process.setChargeCalias(ds.getCalias());
		process.setChargeUid(ds.getUid());
		process.setChargeUname(ds.getUname());
		detectProcessDao.deliverySuccess(process,detectProcessId);
	}
/**
 * 确认收到
 * @param detectProcessId
 */
	public void acceptSuccess(Integer detectProcessId) {
		
		DetectProcess process=new DetectProcess();
		process.setStep(DetectProcess.STEP_DETECTING);
		process.setChargeAcceptTime(new Date());
		detectProcessDao.acceptSuccess(process,detectProcessId);
	}
/**
 * 检验完成
 * @param detectProcessId
 * @param dr 
 */
public void chargeComplete(Integer detectProcessId, DetectReportPojo pojo) {
//	表tb_detect_process填入
	DetectProcess process=new DetectProcess();
	process.setStep(DetectProcess.STEP_COMPLETED);
	process.setChargeCompleteTime(new Date());
	detectProcessDao.chargeComplete(process,detectProcessId);
//	检测报告表填入
	DetectProcess dp=detectProcessDao.selecProcesstById(detectProcessId);
	final DetectReport dr=new DetectReport();
	dr.setProcessId(detectProcessId);
	dr.setPtime(new Date());
	dr.setPname(dp.getChargeUname());
	dr.setPid(dp.getChargeUid());
	dr.setRid(dp.getSampleRid());
	dr.setSid(dp.getSampleSid());
	dr.setStime(dp.getSampleTime());
	dr.setChargeContent(pojo.getContent());
	detectReportDao.insertReport(dr);
}
}
