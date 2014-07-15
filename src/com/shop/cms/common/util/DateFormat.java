package com.shop.cms.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateFormat {
	
	public static String getDate(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date currentTime = new Date();
		return formatter.format(currentTime);
	}

	
	public static String genQuestNum() {
		SimpleDateFormat formatter = new SimpleDateFormat("ddhhssSSSS");
		Date currentTime = new Date();
		return formatter.format(currentTime);

	}

	
	/*public static int isQuestTimeOut(String strDateStart, String strDateEnd) {
		int workDays = 0;
		try {
			// String strDateStart = "2012-08-10";

			// String strDateEnd = "2012-08-31";

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date_start = sdf.parse(strDateStart);
			Date date_end = sdf.parse(strDateEnd);
			Calendar cal_start = Calendar.getInstance();
			Calendar cal_end = Calendar.getInstance();
			cal_start.setTime(date_start);
			cal_end.setTime(date_end);
			DateCal app = new DateCal();

			workDays = app.getWorkingDay(cal_start, cal_end);
		} catch (Exception e) {
			// TODO: handle exception


		return workDays;
	}*/

	private int getDaysBetween(java.util.Calendar d1, java.util.Calendar d2) {
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end

			java.util.Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int days = d2.get(java.util.Calendar.DAY_OF_YEAR)
				- d1.get(java.util.Calendar.DAY_OF_YEAR);
		int y2 = d2.get(java.util.Calendar.YEAR);
		if (d1.get(java.util.Calendar.YEAR) != y2) {
			d1 = (java.util.Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
				d1.add(java.util.Calendar.YEAR, 1);
			} while (d1.get(java.util.Calendar.YEAR) != y2);
		}
		return days;
	}

	/**
	 * 
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	private int getWorkingDay(java.util.Calendar d1, java.util.Calendar d2) {
		int result = -1;
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end

			java.util.Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int betweendays = getDaysBetween(d1, d2);

		int charge_date = 0;
		int charge_start_date = 0;
		int charge_end_date = 0;
		//

		int stmp;
		int etmp;
		stmp = 7 - d1.get(Calendar.DAY_OF_WEEK);
		etmp = 7 - d2.get(Calendar.DAY_OF_WEEK);
		if (stmp != 0 && stmp != 6) {//

			charge_start_date = stmp - 1;
		}
		if (etmp != 0 && etmp != 6) {// 

			charge_end_date = etmp - 1;
		}
		result = (getDaysBetween(this.getNextMonday(d1), this.getNextMonday(d2)) / 7)
				* 5 + charge_start_date - charge_end_date; //

		// System.out.println("charge_start_date>" + charge_start_date);

		// System.out.println("charge_end_date>" + charge_end_date);

		// System.out.println("between day is-->" + betweendays);

		return result;
	}

	public String getChineseWeek(Calendar date) {
		final String dayNames[] = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六",
				"星期日" };
		int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
		// System.out.println(dayNames[dayOfWeek - 1]);

		return dayNames[dayOfWeek - 1];
	}

	/**
	 * 
	 * 
	 * @param date
	 * @return
	 */
	public Calendar getNextMonday(Calendar date) {
		Calendar result = null;
		result = date;
		do {
			result = (Calendar) result.clone();
			result.add(Calendar.DATE, 1);
		} while (result.get(Calendar.DAY_OF_WEEK) != 2);
		return result;
	}

	/**
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public int getHolidays(Calendar d1, Calendar d2) {
		return this.getDaysBetween(d1, d2) - this.getWorkingDay(d1, d2);
	}

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return 
	 */
	public static long minSpace(String startDate, String endDate) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d2 = null;
		Date d1 = null;
		try {
			d2 = df.parse(startDate);
			d1 = df.parse(endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
		long diff = d1.getTime() - d2.getTime();
		return diff;
	}

	public static void main(String args[]) {
		System.out.print(minSpace("2012-09-12 12:12:12", "2012-09-12 12:22:12"));
	}
}