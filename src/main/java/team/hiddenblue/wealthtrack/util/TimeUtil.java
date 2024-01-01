package team.hiddenblue.wealthtrack.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    /**
     * @param date 日期类
     * @return 格式化的时间字符串
     * @Desc 传入日期类生成格式化的时间字符串
     */
    public static String getFormattedTime(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    /**
     * @param date 日期类
     * @return 格式化的日期字符串
     * @Desc 传入日期类生成格式化的日期字符串
     */
    public static String getFormattedDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    public static String getISO8601Timestamp(Date date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return format.format(date);
    }

    public static String getISO8601Timestamp(Long timestamp) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return format.format(timestamp);
    }

    /**
     * @param dateStr 日期字符串
     * @return 日期类
     * @throws ParseException 日期转化异常
     * @Desc 传入日期字符串生成日期类
     */

    public static Date tranStringToDate(String dateStr) throws ParseException {
        // 如果月份或日期为1位数，则在其前面添加零
        String[] parts = dateStr.split("-");
        if (parts.length == 3) {
            parts[1] = addLeadingZero(parts[1]); // 处理月份
            parts[2] = addLeadingZero(parts[2]); // 处理日期
            dateStr = String.join("-", parts);
        }
        System.out.println(dateStr);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(dateStr);
    }

    private static String addLeadingZero(String part) {
        // 如果长度为1，则在前面添加零
        if (part.length() == 1) {
            return "0" + part;
        }
        return part;
    }


    public static Date now() {
        return new Date();
    }
}
