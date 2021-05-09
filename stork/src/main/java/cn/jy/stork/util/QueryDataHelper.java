package cn.jy.stork.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 查询输入条件中起止日期的处理
 * 
 * @author jsh
 *
 */
public class QueryDataHelper {
	private Date to;
	private Date from;

	public QueryDataHelper(Long froml, Long tol) {
		if (tol == null) {
			tol = System.currentTimeMillis();// to如果未输入，则取当前
		}
		to = new Date(tol);

		if (froml == null || froml >= tol) {// 如果from未输入或输入了但大于to，则取前24小时（以to为基准）
			Calendar b24h = Calendar.getInstance();
			b24h.setTimeInMillis(tol);
			b24h.add(Calendar.DAY_OF_YEAR, -1);
			from = b24h.getTime();
		} else {
			from = new Date(froml);
		}
	}

	public Date from() {
		return from;
	}

	public Date to() {
		return to;
	}
}
