package com.sykean.hmhc.util;

import com.sykean.hmhc.enums.DateTypeEnum;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil extends org.apache.commons.lang3.time.DateUtils {

    /**
     * 两个date的天数差
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int offDay(Date date1, Date date2) {
        Calendar instance1 = Calendar.getInstance();
        Calendar instance2 = Calendar.getInstance();
        instance1.setTime(date1);
        instance2.setTime(date2);
        return instance2.get(Calendar.DAY_OF_YEAR) - instance1.get(Calendar.DAY_OF_YEAR);
    }

    public static String format(Date date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static boolean isValidDate(String str) {
        boolean isDate = true;
        try {
            DateUtils.parseDate(str, DateUtil.Format.YYYYMMDD);
        } catch (ParseException e) {
            isDate = false;
        }
        return isDate;
    }

    public static String getTimeSimpleStr() {
        SimpleDateFormat df = new SimpleDateFormat(Format.YYYYMMDDHHMMSS);
        return df.format(new Date());
    }

    public static class Format {
        public static final String YYYYMMDD = "yyyy-MM-dd";

        public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    }

    /**
     * 获取day之后的日期
     *
     * @param date   yyyy-MM-dd
     * @param offset 几天之后
     *               dateTypeEnum  YEAR, MONTH, DAY, HOUR, MINUTE, SECOND
     * @return
     */
    public static Date getDayAftertDay(Date date, Integer offset, DateTypeEnum dateTypeEnum) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (dateTypeEnum) {
            case YEAR:
                calendar.add(Calendar.YEAR, offset);
                break;
            case MONTH:
                calendar.add(Calendar.MONTH, offset);
                break;
            case DAY:
                calendar.add(Calendar.DAY_OF_MONTH, offset);
                break;
            case HOUR:
                calendar.add(Calendar.HOUR, offset);
                break;
            case MINUTE:
                calendar.add(Calendar.MINUTE, offset);
                break;
            case SECOND:
                calendar.add(Calendar.SECOND, offset);
                break;
            default:
                break;
        }
        return calendar.getTime();
    }

    /**
     * @description 根据出生日期计算年龄
     * @author litong
     * @date 2019/6/21
     * @param birthDay
     * @return int
     */
    public static int getAgeByBirth(Date birthDay) throws ParseException {
        int age = 0;
        Calendar cal = Calendar.getInstance();
        //出生日期晚于当前时间，无法计算
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        //当前年份
        int yearNow = cal.get(Calendar.YEAR);
        //当前月份
        int monthNow = cal.get(Calendar.MONTH);
        //当前日期
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        //计算整岁数
        age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                //当前日期在生日之前，年龄减一
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
                //当前月份在生日之前，年龄减一
            } else {
                age--;
            }
        }
        return age;
    }
    
    public static String DateToString(Date dDate, int nFormat) {
        String resString = "";
        try {
            String sFormat = "";
            if (nFormat == 0) {
                sFormat = "yyyyMMdd";
            }else if (nFormat == 1) {
                sFormat = "yyyy-MM-dd";
            } else if (nFormat == 2) {
                sFormat = "yyyy-MM-dd HH:mm:ss";
            } else if (nFormat == 3) {
                sFormat = "yyyy-MM-dd EEE";
            } else if (nFormat == 4) {
                sFormat = "yyyy-MM-dd HH:mm";
            } else if (nFormat == 5) {
                sFormat = "yyyy-MM-dd HH:mm:ss.sss";
            } else {
                sFormat = "yyyy-MM-dd";
            }

            SimpleDateFormat simDateFormat = new SimpleDateFormat(sFormat);
            resString = simDateFormat.format(dDate);
        } catch (Exception e) {
            return resString;
        }
        return resString;
    }
    
    /**
     * 格式化日期
     * 
     * @author hsshao
     * @param date
     * @param formater
     * @return
     */
    public static Date parse(String date, String formater) {
        SimpleDateFormat sdf = new SimpleDateFormat(formater);
        Date result = null;
        try {
            result = sdf.parse(date.trim());
        } catch (ParseException e) {
        	e.printStackTrace();
        }
        return result;
    }


    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }

}

