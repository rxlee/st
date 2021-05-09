package cn.jy.stork.web.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.jy.stork.web.Resp;

@RestController
@RequestMapping("aggregated")
public class AggregatedController {

	/**
	 * 统计所有最新数据并给出可视化列表
	 * 
	 * @param aggTime  起始时间，若没有输入则直接给定。
	 * @param aggTimex 结束时间，若没有输入则定为24时
	 * @param category 区分不同设备号sn传过来的数据类型
	 * @param period   区分聚合时段的类型，默认为day
	 * @return
	 */
	final long time = System.currentTimeMillis();

	@GetMapping("")
	public Resp list(@RequestParam(required = false, defaultValue = "01-11-2020 00：00：00") Date aggTime,
			@RequestParam(required = false, defaultValue = "time") String aggTimex,
			@RequestParam(required = false) String category,
			@RequestParam(required = false, defaultValue = "day") String period) {
		throw new UnsupportedOperationException();
	}

//	/**
//	 * 根据给定好的时间查询取出可视化数据
//	 * 
//	 * @param aggCount 聚合次数
//	 * @param aggSum   聚合累加和
//	 * @param aggAvg   均值
//	 * @param aggMax   最大值
//	 * @param aggMin   最小值
//	 * @param pojo
//	 * @return
//	 * @throws DataNotFoundException
//	 */
//	@GetMapping("")
//	public Resp showimage(@RequestBody AggregatedDataQueryPojo pojo) {
////		AggregatedData img = AggregatedDataService.add(pojo);
////		return Resp.okWithData(img);
//		throw new UnsupportedOperationException();
//	}

}