/**
 * 日期时间处理�?
 */
package com.zhw.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

/**
 * @author ZSL
 * 
 */
public class MyDateUtil {
	/**
	 * 时间格式 for oracle
	 */
	public static final String TIMEFORMAT_DB = "YYYY-MM-DD HH24:MI:SS";
	public static final String MINITIMEFORMAT_DB = "yyyyMMddHH24miss";

	public static final String DATEFMTSTR14 = "yyyyMMddHHmmss";
	public static final String DATEFMTSTR10 = "yyyy-MM-dd";
	public static final String DATEFMTSTR8 = "yyyyMMdd";
	public static final String DATEFMTSTR19 = "yyyy-MM-dd HH:mm:ss";
	// 含时区使用的时间格式
	public static final String DATEFMTSTR21 = "yyyy-MM-dd HH:mm:ssZZ";
	
	

    private static ThreadLocal<SimpleDateFormat> df8 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATEFMTSTR8);
        }
    };
    public static Date parse_df8(String dateStr) throws ParseException {
        return df8.get().parse(dateStr);
    }
    public static String format_df8(Date date) {
        return df8.get().format(date);
    }
    
    private static ThreadLocal<SimpleDateFormat> df10 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATEFMTSTR10);
        }
    };
    public static Date parse_df10(String dateStr) throws ParseException {
        return df10.get().parse(dateStr);
    }
    public static String format_df10(Date date) {
        return df10.get().format(date);
    }
    private static ThreadLocal<SimpleDateFormat> df14 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATEFMTSTR14);
        }
    };
    public static Date parse_df14(String dateStr) throws ParseException {
        return df14.get().parse(dateStr);
    }
    public static String format_df14(Date date) {
        return df14.get().format(date);
    }
    
    private static ThreadLocal<SimpleDateFormat> df19 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATEFMTSTR19);
        }
    };
    public static Date parse_df19(String dateStr) throws ParseException {
        return df19.get().parse(dateStr);
    }
    public static String format_df19(Date date) {
        return df19.get().format(date);
    }
    private static ThreadLocal<SimpleDateFormat> df21 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATEFMTSTR21);
        }
    };
    public static Date parse_df21(String dateStr) throws ParseException {
        return df21.get().parse(dateStr);
    }
    public static String format_df21(Date date) {
        return df21.get().format(date);
    }
	public static Date Str2Date(String DateTime, String Formater) {
		try {
			return DateUtils.parseDate(DateTime, Formater);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 字符串转日期
	 * 
	 * @param DateTime
	 * @return
	 */
	public static Date StrToDate(String DateTime, String Formater) {
		SimpleDateFormat sdf = new SimpleDateFormat(Formater);
		Date date = null;
		try {
			date = sdf.parse(DateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date, String Formater) {
		// "yyyy-MM-dd HH:mm:ss"
		SimpleDateFormat sdt = new SimpleDateFormat(Formater);
		String str = sdt.format(date);
		return str;
	}

	/**
	 * 通过变化的分钟数，得到新的日期
	 * 
	 * @param oldDT
	 *            旧日期
	 * @param IntervalMin
	 *            变化分钟数
	 * @return 新 Date
	 */
	public static Date addMinute(Date oldDT, int changeMinute) {
		Date newDT = null;
		Calendar ca = Calendar.getInstance();
		ca.setTime(oldDT);
		ca.add(Calendar.MINUTE, changeMinute);
		newDT = ca.getTime();
		return newDT;
	}

	/**
	 * 计算两个日期之间相差的时间
	 * 
	 * @param BeginDate
	 *            较小的时间
	 * @param EndDate
	 *            较大的时间
	 * @param Type
	 *            DAYS,HOURS,MINTUES,SECONDS
	 * @return 相差时间
	 * @throws ParseException
	 */
	public static int TimeBetween(Date BeginDate, Date EndDate, String Type) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		BeginDate = sdf.parse(sdf.format(BeginDate));
		EndDate = sdf.parse(sdf.format(EndDate));

		Calendar cal = Calendar.getInstance();

		cal.setTime(BeginDate);
		long time1 = cal.getTimeInMillis();

		cal.setTime(EndDate);
		long time2 = cal.getTimeInMillis();

		long lTime = 1;
		Type = Type.toUpperCase();
		if (Type.equals("DAYS")) {
			lTime = 1000 * 3600 * 24;
		} else if (Type.equals("HOURS")) {
			lTime = 1000 * 3600;
		} else if (Type.equals("MINTUES")) {
			lTime = 1000 * 60;
		} else if (Type.equals("SECONDS")) {
			lTime = 1000;
		}

		long between = (time2 - time1) / lTime;

		return Integer.parseInt(String.valueOf(between));
	}
	/**
	 * 计算两个时间的时间差(毫秒)
	 * @param data1
	 * @param date2
	 * @return
	 */
	public static long differ(Date date1,Date date2){
		if (date1!=null && date2!=null) {
			return date2.getTime()-date1.getTime();
		}
		return 0;
	}
}
