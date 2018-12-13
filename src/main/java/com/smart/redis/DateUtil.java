package com.smart.redis;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;

public class DateUtil {
    //获取未来最近日期的周四
    public static String getFour(){
        Calendar c1 = Calendar.getInstance();
        //获得星期几（注意（这个与Date类是不同的）：1代表星期日、2代表星期1、3代表星期二，以此类推）
        int day = c1.get(Calendar.DAY_OF_WEEK);
        System.out.println(day);
        switch (day){
            case 1:c1.add(Calendar.DATE, 4);break;
            case 2:c1.add(Calendar.DATE, 3);break;
            case 3:c1.add(Calendar.DATE, 2);break;
            case 4:c1.add(Calendar.DATE, 1);break;
            case 5:c1.add(Calendar.DATE, 0);break;
            case 6:c1.add(Calendar.DATE, 6);break;
            case 7:c1.add(Calendar.DATE, 5);break;
        }
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
        Date date = c1.getTime();
        return ft.format(date);
    }

    public static String getDate(){
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
        return ft.format(dNow);
    }



    public static int dateNumber(String str)throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar aCalendar = Calendar.getInstance();
        //日期的天数差
        int flag = 0;
        //借书日期
        Date date = sdf.parse(str);
        aCalendar.setTime(date);
        int day = aCalendar.get(Calendar.DAY_OF_YEAR);
        int year = aCalendar.get(Calendar.YEAR);
        //System.out.println("YEAR:"+year+"day:"+day);

        //当前日期
        Date date1 = new Date();
        aCalendar.setTime(date1);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        int year1 = aCalendar.get(Calendar.YEAR);
        //System.out.println("YEAR1:"+year1+"day1:"+day1);

        //判断是否是同一年
        if(year != year1)   //同一年
        {
            for(int i = year ; i < year1 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    flag += 366;
                }
                else    //不是闰年
                {
                    flag += 365;
                }
            }
            flag =flag+(day1-day);
        }
        else    //不同年
        {
            flag = day1-day;
        }
        System.out.println(flag);
        return flag;
    }



    public static void main(String[] args){
        try {
            System.out.println(dateNumber("2018-11-18"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
