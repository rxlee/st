package cn.jy.stork.util;

import java.util.Calendar;
import java.util.Date;

public class DatePeriod {

	public static final String DAY = "day";
	public static final String MONTH = "month";
	public static final String YEAR = "year";

	private Date clipped;
	private Date year;
	private Date month;
	private Date day;

	public DatePeriod() {
		this(new Date());
	}

	public DatePeriod(Date date) {

		final Calendar time = Calendar.getInstance();
		time.setTime(date);

		time.set(Calendar.MILLISECOND, 0);
		time.set(Calendar.SECOND, 0);
		this.clipped = time.getTime();

		time.set(Calendar.MINUTE, 0);
		time.set(Calendar.HOUR_OF_DAY, 0);
		this.day = time.getTime();

		time.set(Calendar.DAY_OF_MONTH, 1);
		this.month = time.getTime();

		time.set(Calendar.MONTH, 0);
		this.year = time.getTime();
	}

	public Date getYear() {
		return this.year;
	}

	public Date getMonth() {
		return this.month;
	}

	public Date getDay() {
		return this.day;
	}

	/**
	 * 把时间修剪为整分钟值然后返回（秒和毫秒为0），形如2020-11-09 14:05:00.000
	 * 
	 * @return
	 */
	public Date clipped() {
		return clipped;
	}

}