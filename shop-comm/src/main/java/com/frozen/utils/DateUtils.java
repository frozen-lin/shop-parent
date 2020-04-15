package com.frozen.utils;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Objects;

/**
 * <program> shop-parent </program>
 * <description> 日期工具类 </description>
 *
 * @author : lw
 * @date : 2020-04-07 14:44
 **/
public class DateUtils {
    private static final String SHORT_DATE_PATTERN = "yyyyMMdd";
    private static final DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern(DateUtils.SHORT_DATE_PATTERN);
    /**
     * <description> 将传入日起对象格式化为yyyyMMdd的字符串 </description>
     * @param temporal : LocalDate 或 LocalDateTime
     * @return : java.lang.String
     * @author : lw
     * @date : 2020/4/7 14:51
     */
    public static String shortDateFormat(TemporalAccessor temporal){
        if(Objects.isNull(temporal)){
            return "";
        }
        return SHORT_DATE_FORMATTER.format(temporal);
    }
}
