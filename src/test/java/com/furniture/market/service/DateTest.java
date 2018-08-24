package com.furniture.market.service;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static org.apache.commons.lang3.time.DateUtils.MILLIS_PER_DAY;

/**
 * @author Lijq
 * @date 2018/4/26 9:32
 * @description
 */
public class DateTest {

    @Test
    public void calenderTest() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        System.out.println(date);
        calendar.setTime(date);
        //calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        System.out.println(calendar.getTime());
    }


    @Test
    public void math() throws Exception {

        Date date1 = DateUtils.parseDate("2018-06-28 00:11:22", "yyyy-MM-dd HH:mm:ss");
        Date date2 = DateUtils.parseDate("2018-05-26 00:11:22", "yyyy-MM-dd HH:mm:ss");

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        System.out.println(" 月 ->" + (calendar2.get(Calendar.MONTH) - calendar1.get(Calendar.MONTH)));
        System.out.println(" 年 ->" + (calendar2.get(Calendar.YEAR) - calendar1.get(Calendar.YEAR)));


    }

    @Test
    public  void divid(){
        System.out.println(DateUtils.MILLIS_PER_DAY);
        System.out.println(Math.ceil(((1000 * 60 * 60 * 24)*2+3000)/MILLIS_PER_DAY));
    }


 /*   public int getCountOfLate() {
        // 当前日期
        Date date1 = new Date();

        if (lastReceivedDate == null) {
            return 0;
        }

        // 租金最后日期
        Date date2 = lastReceivedDate;

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        int count = (calendar2.get(Calendar.MONTH) - calendar1.get(Calendar.MONTH)) >= 0 ?
                (calendar2.get(Calendar.MONTH) - calendar1.get(Calendar.MONTH)) :
                Math.abs((calendar2.get(Calendar.MONTH) - calendar1.get(Calendar.MONTH))) + 1;
        count += Math.abs((calendar2.get(Calendar.YEAR) - calendar1.get(Calendar.YEAR)) * 12);

        if (count == 0) {
            if ((calendar2.get(Calendar.MONTH) - calendar1.get(Calendar.MONTH)) == 0) {
                count = 1;
            }
        }

        return count;
    }*/
}
