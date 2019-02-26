package com.desperado.teamjob.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateTimeUtil {
    private static Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);
    public static final String PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DAY = "yyyy-MM-dd";
    public static final String PATTERN_COMPACT = "yyyyMMdd";
    public static SimpleDateFormat formatDisplayDate = new SimpleDateFormat("M月d号");
    public static SimpleDateFormat formatDisplayTimeA = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public DateTimeUtil() {
    }

    public static Date today() {
        return new Date();
    }

    public static String now() {
        return formatDate(today());
    }

    public static String formatDate(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            throw new IllegalArgumentException("date is null");
        } else if (pattern == null) {
            throw new IllegalArgumentException("pattern is null");
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            return formatter.format(date);
        }
    }

    public static String businessAppDisplayTime(Timestamp timestamp) {
        if (timestamp == null) {
            return "";
        } else {
            Date date = new Date(timestamp.getTime());
            Date date1 = getMorningDate(date);
            long time = date1.getTime();
            long now = System.currentTimeMillis();
            int eclapseSec = (int)((now - time) / 1000L);
            int days = eclapseSec / 86400;
            StringBuffer sb = new StringBuffer();
            if (days > 0) {
                if (days == 1) {
                    sb.append("昨天 " + parseDateTime(timestamp, "HH:mm"));
                } else if (days == 2) {
                    sb.append("前天 " + parseDateTime(timestamp, "HH:mm"));
                } else {
                    sb.append(parseDateTime(timestamp, "yyyy/MM/dd"));
                }
            } else {
                sb.append("今天 " + parseDateTime(timestamp, "HH:mm"));
            }

            return sb.toString();
        }
    }

    public static String generateAppDisplayTime(Timestamp timestamp) {
        if (timestamp == null) {
            return "";
        } else {
            long time = timestamp.getTime();
            long now = System.currentTimeMillis();
            int eclapseSec = (int)((now - time) / 1000L);
            int days = eclapseSec / 86400;
            StringBuffer sb = new StringBuffer();
            if (days > 0) {
                if (days == 1) {
                    sb.append("昨天");
                } else if (days == 2) {
                    sb.append("前天");
                } else if (days >= 30) {
                    int month = days / 30;
                    if (month > 12) {
                        sb.append(normalizeTime(time));
                    } else {
                        sb.append(month).append("个月前");
                    }
                } else if (days < 30) {
                    sb.append(days).append("天前");
                }
            } else {
                sb.append(parseDateTime(timestamp, "HH:mm"));
            }

            return sb.toString();
        }
    }

    public static Date getDaysAgo(int days) {
        return new Date(System.currentTimeMillis() - (long)(days * 86400) * 1000L);
    }

    public static String normalizeTime(long time) {
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        return sm.format(date);
    }

    public static String normalizeTime(Timestamp timestamp) {
        if (timestamp == null) {
            return "";
        } else {
            Date date = new Date(timestamp.getTime());
            SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sm.format(date);
        }
    }

    public static String parseDateTime(Date date, String format) {
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat sm = new SimpleDateFormat(format);
            return sm.format(date);
        }
    }

    public static Date getTodayDate() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date getMorningDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date getYesterdayDate() {
        Date date = new Date(System.currentTimeMillis() - 86400000L);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static String getDateByFormat(Date date, String format) {
        if (date == null) {
            date = new Date();
        }

        SimpleDateFormat formatDate = new SimpleDateFormat(format);
        return formatDate.format(date);
    }

    public static long getDateDiff(Date dateStart, Date dateStop) {
        if (dateStart != null && dateStop != null) {
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy");
            formatDate.format(dateStart);
            formatDate.format(dateStop);
            long diff = (dateStart.getTime() - dateStop.getTime()) / 1000L / 3600L / 24L / 365L;
            return diff;
        } else {
            return 0L;
        }
    }

    public static Date parseStringToDate(String date, String format) {
        if (date == null) {
            return null;
        } else {
            SimpleDateFormat sm = new SimpleDateFormat(format);

            try {
                return sm.parse(date);
            } catch (ParseException var4) {
                logger.error(var4.getMessage(), var4);
                return null;
            }
        }
    }

    public static Date parseStringToDate(String time) {
        if (StringUtils.isEmpty(time)) {
            return null;
        } else {
            int year = 0;
            int month = 1;
            int day = 1;
            int hour = 0;
            int min = 0;
            int sec = 0;
            List<String> strList = new ArrayList();
            StringBuffer nsb = new StringBuffer();

            int i;
            for(i = 0; i < time.length(); ++i) {
                char c = time.charAt(i);
                if (Character.isDigit(c)) {
                    nsb.append(c);
                } else if (nsb.length() > 0) {
                    strList.add(nsb.toString());
                    nsb = new StringBuffer();
                }
            }

            if (nsb.length() > 0) {
                strList.add(nsb.toString());
            }

            for(i = 0; i < strList.size(); ++i) {
                int n = Integer.getInteger(strList.get(i));
                switch(i) {
                    case 0:
                        year = n;
                        break;
                    case 1:
                        month = n;
                        break;
                    case 2:
                        day = n;
                        break;
                    case 3:
                        hour = n;
                        break;
                    case 4:
                        min = n;
                        break;
                    case 5:
                        sec = n;
                }
            }

            StringBuffer sb = new StringBuffer();
            sb.append(StringUtil.formatString("%04d", new Object[]{year})).append("-").append(StringUtil.formatString("%02d", new Object[]{month})).append("-").append(StringUtil.formatString("%02d", new Object[]{day})).append(" ").append(StringUtil.formatString("%02d", new Object[]{hour})).append(":").append(StringUtil.formatString("%02d", new Object[]{min})).append(":").append(StringUtil.formatString("%02d", new Object[]{sec}));
            return parseStringToDate(sb.toString(), "yyyy-MM-dd HH:mm:ss");
        }
    }

    public static long diffMonth(Date end) {
        Date begin = new Date();
        if (begin != null && end != null) {
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM");
            formatDate.format(begin);
            formatDate.format(end);
            long diff = (begin.getTime() - end.getTime()) / 1000L / 3600L / 24L;
            return diff;
        } else {
            return 0L;
        }
    }

    public static long diffMonth(Date begin, Date end) {
        if (begin != null && end != null) {
            Calendar beginCal = Calendar.getInstance();
            beginCal.setTime(begin);
            Calendar endCal = Calendar.getInstance();
            endCal.setTime(end);
            long month = (long)((endCal.get(1) - beginCal.get(1)) * 12);
            return month + (long)(endCal.get(2) - beginCal.get(2));
        } else {
            return 0L;
        }
    }

    public static void main(String[] args) throws ParseException {
        long time = 1469495306000L;
        Date date = new Date(time);
        System.out.println(formatDate(date, "yyyy-MM-dd HH:mm:ss"));
    }

    public static Date getYear(String year) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy");

        try {
            return formatDate.parse(year);
        } catch (ParseException var3) {
            return null;
        }
    }

    public static Date ceiling(Date date) {
        if (date == null) {
            return null;
        } else {
            SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
            String day = sm.format(date) + " 23:59:59";
            return parseStringToDate(day, "yyyy-MM-dd HH:mm:ss");
        }
    }

    public static Date floor(Date date) {
        if (date == null) {
            return null;
        } else {
            SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
            String day = sm.format(date) + " 00:00:00";
            return parseStringToDate(day, "yyyy-MM-dd HH:mm:ss");
        }
    }

    public static Date maxDate(Date date) {
        if (date == null) {
            return null;
        } else {
            SimpleDateFormat sm = new SimpleDateFormat("yyyy");
            String day = sm.format(date) + "-12-31 00:00:00";
            return parseStringToDate(day, "yyyy-MM-dd HH:mm:ss");
        }
    }

    public static Timestamp toTimestamp(Date date) {
        return date == null ? null : new Timestamp(date.getTime());
    }

    public static Date delta(Date date, int daltaDays) {
        return date == null ? null : new Date(date.getTime() + (long)(daltaDays * 86400) * 1000L);
    }

    public static java.sql.Date toSqlDate(Date date) {
        return date == null ? null : new java.sql.Date(date.getTime());
    }

    public static Date firstDayInMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(5, 1);
        return cal.getTime();
    }

    public static Date lastDayInMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(5, cal.getActualMaximum(5));
        return cal.getTime();
    }
}
