package cn.jy.stork.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import cn.jy.stork.biz.service.AccountService;
import cn.jy.stork.dao.Account;
import cn.jy.stork.dao.DetectProcess;
import cn.jy.stork.dao.SampleRecord;
import cn.jy.stork.dao.Station;
import cn.jy.stork.exception.DataNotFoundException;

public class DetectReportPdf {

	@Autowired
	AccountService accountService;
	
	static BaseFont BASE_SONG;
	static BaseFont BASE_HEI;
	static Font SONG_FONT10;
	static Font SONG_FONT10b;
	static Font SONG_FONT12;
	static Font SONG_FONT12b;
	static Font SONG_FONT14;
	static Font SONG_FONT14b;
	static Font HEI_FONT10;
	static Font HEI_FONT10b;
	static Font HEI_FONT12;
	static Font HEI_FONT12b;
	static Font HEI_FONT14;
	static Font HEI_FONT14b;
	static {
		try {
			BASE_HEI = BaseFont.createFont("MHei-Medium", "UniCNS-UTF32-H", BaseFont.NOT_EMBEDDED);
			BASE_SONG = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		SONG_FONT10 = new Font(BASE_SONG, 10, Font.NORMAL);
		SONG_FONT10b = new Font(BASE_SONG, 10, Font.BOLD);
		SONG_FONT12 = new Font(BASE_SONG, 12, Font.NORMAL);
		SONG_FONT12b = new Font(BASE_SONG, 12, Font.BOLD);
		SONG_FONT14 = new Font(BASE_SONG, 14, Font.NORMAL);
		SONG_FONT14b = new Font(BASE_SONG, 14, Font.BOLD);
		HEI_FONT10 = new Font(BASE_HEI, 10, Font.NORMAL);
		HEI_FONT10b = new Font(BASE_HEI, 10, Font.BOLD);
		HEI_FONT12 = new Font(BASE_HEI, 12, Font.NORMAL);
		HEI_FONT12b = new Font(BASE_HEI, 12, Font.BOLD);
		HEI_FONT14 = new Font(BASE_HEI, 14, Font.NORMAL);
		HEI_FONT14b = new Font(BASE_HEI, 14, Font.BOLD);
	}

	private Station station;
	private SampleRecord record;
	private DetectProcess process;
	private Document document;
	private int tick = 0;

	public DetectReportPdf(Station station, SampleRecord record, DetectProcess process) {
		this.process = process;
		this.station = station;
		this.record = record;
	}

	/**
	 * 生成报告
	 * 
	 * @return
	 */
	public String generate() {
		try {
			final File file = new File("1.pdf");
			file.createNewFile();

			document = new Document();// 默认PageSize.A4
			PdfWriter.getInstance(document, new FileOutputStream(file));

			document.open();
			// 调用方法生成标题
			generateTitle();
			// 调用方法生成报告摘要
			generateSummary();
			// 调用方法生成标题：：第一部分：排水事件组块
			generateSubTitle("一  排水事件组块");
			generateContent1();
			// 调用方法生成标题：：第二部分：入河通量核算模块
			generateSubTitle("二  入河通量核算模块");
			generateContent2();
			// 调用方法生成标题：：第三部分：精细溯源模块
			generateSubTitle("三  精细溯源模块:");
			generateSubTitle("(一)常规监测分析");
			generateContent31();
			generateSubTitle("(二)特征污染物分析");
			generateContent32();
			generateSubTitle("(三)初步结论建议");
			generateContent33();
			// 调用方法生成标题：：第四部分： 核定提交模板
			generateSubTitle("四  核定提交模板:");
			generateContent4();
			// TODO 继续生成后续内容，尽量都写到单独的方法里面，便于阅读和修改

			// 关闭文档
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;// TODO 最后要返回文档在磁盘上的相对路径
	}



	/**
	 * 生成报告标题
	 * 
	 * @throws DocumentException
	 */
	private void generateTitle() throws DocumentException {
		final Paragraph p = new Paragraph("天津市静海区团泊七排干河道入河污染负荷智慧管控报表", SONG_FONT14b);
		p.setAlignment(Element.ALIGN_CENTER);
//		p.setLeading(50f);//行间距
		document.add(p);
	}

	/**
	 * 生成报告摘要
	 * 
	 * @throws DocumentException
	 */
	private void generateSummary() throws DocumentException {
		final Date NOW = new Date();
		final StringBuilder buf = new StringBuilder();
		buf.append("报表代码：").append(new SimpleDateFormat("yyyyMMdd").format(NOW))
		.append(station.getCode().substring(12, 14)).append("-").append(String.format("%03d", (tick++) % 1000));
		buf.append("\n");
		buf.append("口门代码：").append(station.getCode());
		buf.append("\n");
		buf.append("提交终表时间：").append(new SimpleDateFormat("yyyy 年 MM 月 dd 日").format(NOW));

		final Paragraph p = new Paragraph(buf.toString(), SONG_FONT10);
		p.setSpacingBefore(12f);
		document.add(p);
	}
	/**
	 * 生成排水事件组块
	 * @throws DocumentException
	 * @throws DataNotFoundException 
	 */
	private void generateContent1()throws DocumentException, DataNotFoundException  {
		final StringBuilder buf = new StringBuilder();
		final float v=0;
		//TODO v 排水量
		buf.append(record.getSname()).append("口门自").append(new SimpleDateFormat("yyyy 年 MM 月 dd 日 HH 点 mm 分~").format(record.getDischargeStart()))
		.append(new SimpleDateFormat("yyyy 年 MM 月 dd 日 HH 点 mm 分").format(record.getDischargeEnd()))
		.append("累计排水，连续排水时间").append(v).append("立方米").append("").append("小时。").append("\n");

		
		//图片
		
		Account a=accountService.findById(process.getChargeUid());
		buf.append("排水组快数据上传人员:").append(process.getChargeUname());
		buf.append("联系方式:").append(a.getTelephone())
		.append("\n");
		buf.append("单位:").append(process.getChargeCname());
		buf.append("填报时间:").append(new SimpleDateFormat("yyyy 年 MM 月 dd 日").format(process.getChargeCompleteTime()))
		.append("\n");
		
		final Paragraph p = new Paragraph(buf.toString(), SONG_FONT10);
		p.setSpacingBefore(12f);
		document.add(p);
	}
	/**
	 * 入河通量核算模块
	 * @throws DocumentException
	 * @throws DataNotFoundException
	 */
	private void generateContent2()throws DocumentException, DataNotFoundException  {
		final StringBuilder buf = new StringBuilder();
		//TODO 三个排放参数
		buf.append(record.getSname()).append("口门自").append(new SimpleDateFormat("yyyy 年 MM 月 dd 日 HH 点 mm 分~").format(record.getDischargeStart()))
		.append(new SimpleDateFormat("yyyy 年 MM 月 dd 日 HH 点 mm 分").format(record.getDischargeEnd()))
		.append("累计排放CODcr").append("").
		append(",氨氮").append("")
		.append("kg,总磷").append("")
		.append("kg。").
		append("\n");
		
		Account a=accountService.findById(process.getDispatchUid());
		buf.append("核算模块数据上传人员:").append(process.getDispatchUname());
		buf.append("联系方式:").append(a.getTelephone()).append("\n");
		buf.append("单位:").append(process.getSampleSname());
		buf.append("填报时间:").append(new SimpleDateFormat("yyyy 年 MM 月 dd 日").format(process.getDispatchTime())).append("\n");

		
		final Paragraph p = new Paragraph(buf.toString(), SONG_FONT10);
		p.setSpacingBefore(12f);
		document.add(p);
	}
	/**
	 * 常规监测分析
	 * @throws DataNotFoundException 
	 */
	private void generateContent31()throws DocumentException, DataNotFoundException  {
		final StringBuilder buf = new StringBuilder();
		
		//TODO 检测内容
		buf.append("").append("\n");
		Account a=accountService.findById(process.getChargeUid());
		buf.append("常规分析数据上传人员:").append(process.getChargeUname());
		buf.append("联系方式:").append(a.getTelephone())
		.append("\n");
		buf.append("单位:").append(process.getChargeCname());
		buf.append("填报时间:").append(new SimpleDateFormat("yyyy 年 MM 月 dd 日").format(process.getChargeCompleteTime()))
		.append("\n");
		
		final Paragraph p = new Paragraph(buf.toString(), SONG_FONT10);
		p.setSpacingBefore(12f);
		document.add(p);
	}
	/**
	 * 特征污染物分析
	 * @throws DocumentException
	 * @throws DataNotFoundException 
	 */
	private void generateContent32()throws DocumentException, DataNotFoundException  {
		final StringBuilder buf = new StringBuilder();
		//TODO 检测内容
		buf.append("").append("结果见图3-2");	
		
		Account a=accountService.findById(process.getChargeUid());
		buf.append("常规分析数据上传人员:").append(process.getChargeUname());
		buf.append("联系方式:").append(a.getTelephone())
		.append("\n");
		buf.append("单位:").append(process.getChargeCname());
		buf.append("填报时间:").append(new SimpleDateFormat("yyyy 年 MM 月 dd 日").format(process.getChargeCompleteTime()))
		.append("\n");

		
		final Paragraph p = new Paragraph(buf.toString(), SONG_FONT10);
		p.setSpacingBefore(12f);
		document.add(p);
	}
	/**
	 * 初步结论建议
	 * @throws DocumentException
	 * @throws DataNotFoundException 
	 */
	private void generateContent33()throws DocumentException, DataNotFoundException  {
		final StringBuilder buf = new StringBuilder();
		//TODO 检测内容
		buf.append("").append("\n");	
		Account a=accountService.findById(process.getChargeUid());
		buf.append("特征分析数据上传人员:").append(process.getChargeUname());
		buf.append("联系方式:").append(a.getTelephone())
		.append("\n");
		buf.append("单位:").append(process.getChargeCname());
		buf.append("填报时间:").append(new SimpleDateFormat("yyyy 年 MM 月 dd 日").format(process.getChargeCompleteTime()))
		.append("\n");

		
		final Paragraph p = new Paragraph(buf.toString(), SONG_FONT10);
		p.setSpacingBefore(12f);
		document.add(p);
	}
	/**
	 * 四、核定提交模块
	 * @throws DocumentException
	 */
	private void generateContent4()throws DocumentException  {
		final StringBuilder buf = new StringBuilder();
		
		//TODO 核定人员平台
		buf.append("报告综合核定人:").append("").append("单位").append("").append("\n")
		.append("报告抄送提交人:").append("").append("单位").append("").append("\n")
		.append("平台技术支撑单位:").append("").append("\n")
		.append("平台供应维护单位:").append("").append("\n");
		
		final Paragraph p = new Paragraph(buf.toString(), SONG_FONT10);
		p.setSpacingBefore(12f);
		document.add(p);
	}
	/**
	 * 通用方法，生成子标题，有多个子标题要生成
	 * 
	 * @param title
	 * @throws DocumentException
	 */
	private void generateSubTitle(String title) throws DocumentException {
		final Paragraph p = new Paragraph(title, SONG_FONT12b);
		p.setSpacingBefore(12f);
		document.add(p);
		p.setSpacingAfter(12f);
	}

}
