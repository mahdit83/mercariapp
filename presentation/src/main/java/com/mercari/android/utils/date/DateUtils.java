package com.mercari.android.utils.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


public class DateUtils {
    /**
     *
     */
    public static final String ENGLISH_LANGUAGE = "en";

    /**
     *
     */
    public static final String PERSIAN_DATE_FORMAT = "yyyy/MM/dd";

    /**
     *
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd";


    /**
     *
     */
    public static final String FARSI_LANGUAGE = "fa";

    /**
     *
     */
    public static final Locale FARSI_LOCALE = new Locale(FARSI_LANGUAGE);

    /**
     *
     */
    public static final Locale ENGLISH_LOCALE = new Locale(ENGLISH_LANGUAGE);


    /**
     * @param date
     * @return a Date
     */
    public static Date roundDate(Date date) {
        GregorianCalendar calendar;

        calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(GregorianCalendar.HOUR, 0);
        calendar.set(GregorianCalendar.MINUTE, 0);
        calendar.set(GregorianCalendar.SECOND, 0);
        calendar.set(GregorianCalendar.MILLISECOND, 0);
        calendar.set(GregorianCalendar.AM_PM, GregorianCalendar.AM);

        return calendar.getTime();
    }


    /**
     * <p>Checks if the field is a valid date.  The pattern is used with
     * <code>java.text.SimpleDateFormat</code>.  If strict is true, then the
     * length will be checked so '2/12/1999' will not pass validation with
     * the format 'MM/dd/yyyy' because the month isn't two digits.
     * The setLenient method is set to <code>false</code> for all.</p>
     *
     * @param value       The value validation is being performed on.
     * @param datePattern The pattern passed to <code>SimpleDateFormat</code>.
     * @param strict      Whether or not to have an exact match of the datePattern.
     */
    public static Date parse(String value, String datePattern, boolean strict, Locale locale) {
        Date date;
        DateFormat formatter;


        if (value == null || datePattern == null || datePattern.length() == 0) {
            return null;
        }

        try {

            formatter = null;

            if (locale != null) {
                if (FARSI_LOCALE.getLanguage().equals(locale.getLanguage())) {
                    formatter = new PersianDateFormat(datePattern);
                }
            }

            if (formatter == null) {
                formatter = new SimpleDateFormat(datePattern);
            }

            formatter.setLenient(false);

            date = formatter.parse(value);

            if (strict) {
                if (datePattern.length() != value.length()) {
                    date = null;
                }
            }
        } catch (ParseException ex) {
            return null;
        }

        return date;
    }

    /**
     * <p>Checks if the field is a valid date.  The <code>Locale</code> is
     * used with <code>java.text.DateFormat</code>.  The setLenient method
     * is set to <code>false</code> for all.</p>
     *
     * @param value  The value validation is being performed on.
     * @param locale The Locale to use to parse the date (system default if null)
     */
    public static Date parse(String value, Locale locale) {
        Date date;
        DateFormat formatter;


        if (value == null) {
            return null;
        }

        try {

            formatter = null;
            if (locale != null) {
                if (FARSI_LOCALE.getLanguage().equals(locale.getLanguage())) {
                    formatter = new PersianDateFormat(PERSIAN_DATE_FORMAT);
                }
            }

            if (formatter == null) {
                formatter = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            }

            formatter.setLenient(false);

            date = formatter.parse(value);
        } catch (ParseException ex) {
            return null;
        }

        return date;
    }

    /**
     * @param locale
     * @param date
     * @param format
     * @param translate
     * @return a String
     */
    public static String getLocaleDate(Locale locale, Date date, String format, boolean translate) {
        DateFormat dateFormat;

        if (locale != null && FARSI_LOCALE.getLanguage().equals(locale.getLanguage())) {
            dateFormat = new PersianDateFormat((format == null) ? PERSIAN_DATE_FORMAT : format);
        } else {
            dateFormat = new SimpleDateFormat((format == null) ? DEFAULT_DATE_FORMAT : format, Locale.getDefault());
        }

        return translate ? StringUtils.getLocaleText(locale, dateFormat.format(date)) : dateFormat.format(date);
    }

    /**
     * returns the difference between two dates in day scale
     *
     * @param fromDate
     * @param untilDate
     * @return an int val+ue
     */
    public static long getDifferenceInDay(Date startDate, Date endDate) {


//    	long startTime = startDate.getTime();
//    	long endTime = endDate.getTime();
//    	long diffTime = endTime - startTime;
//    	long diffDays = diffTime / (1000 * 60 * 60 * 24);
//    	DateFormat dateFormat = DateFormat.getDateInstance();
//
//    	return diffDays;

//        return ChronoUnit.DAYS.between(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        return 0;
    }


    /**
     * returns the date time of a day before given current date
     *
     * @param currentDate
     * @return a Date object
     */
    public static Date getPreviousDay(Date currentDate) {
        GregorianCalendar calendar;
        int day;

        calendar = new GregorianCalendar();
        calendar.setTime(currentDate);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.set(Calendar.DAY_OF_MONTH, day - 1);

        return calendar.getTime();
    }

    /**
     * returns the date time of a day before given current date
     *
     * @param currentDate
     * @return a Date object
     */
    public static Date getNextDay(Date currentDate) {
        GregorianCalendar calendar;
        int day;

        calendar = new GregorianCalendar();
        calendar.setTime(currentDate);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.set(Calendar.DAY_OF_MONTH, day + 1);

        return calendar.getTime();
    }

    /**
     * checks whether given date is between start date and end date or not
     *
     * @param date
     * @param startDate
     * @param endDate
     * @return true if given date occurred between startDate and endDate otherwise false
     */
    public static boolean checkInterval(Date date, Date startDate, Date endDate) {

        if (startDate != null) {
            if (date.compareTo(startDate) < 0) {
                return false;
            }
        }

        if (endDate != null) {
            if (date.compareTo(endDate) > 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * provides a Date Time range started from beging of specified day date 00:00:00.000 until the next day 00:00:00.000
     *
     * @param dayDate
     * @return a Date array
     */
    public static Date[] getDayInterval(Date dayDate) {
        GregorianCalendar calendar;
        Date[] range;


        range = new Date[2];
        calendar = new GregorianCalendar();
        calendar.setTime(dayDate);

        calendar.set(GregorianCalendar.HOUR_OF_DAY, 0);
        calendar.set(GregorianCalendar.MINUTE, 0);
        calendar.set(GregorianCalendar.SECOND, 0);
        calendar.set(GregorianCalendar.MILLISECOND, 0);

        range[0] = calendar.getTime();

        calendar.set(GregorianCalendar.DAY_OF_MONTH, calendar.get(GregorianCalendar.DAY_OF_MONTH) + 1);

        range[1] = calendar.getTime();

        return range;
    }


    /**
     * provides a Date Time range started from beging of specified month date 00:00:00.000 until the month date 00:00:00.000
     *
     * @param dayDate the current day date.
     * @param locale;
     * @return a Date array
     */
    public static Date[] getMonthDateInterval(Date dayDate, Locale locale) {
        Calendar calendar;
        Date[] range;
        int month;


        range = new Date[2];

        if (FARSI_LANGUAGE.equals(locale.getLanguage())) {
            calendar = new PersianCalendar();
        } else {
            calendar = new GregorianCalendar();
        }

        calendar.setTime(dayDate);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        range[0] = calendar.getTime();


        month = calendar.get(GregorianCalendar.MONTH);

        if (month < calendar.getMaximum(Calendar.MONTH)) {
            calendar.set(Calendar.MONTH, month + 1);
        } else {
            calendar.set(Calendar.MONTH, calendar.getMinimum(Calendar.MONTH));
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
        }


        range[1] = calendar.getTime();

        return range;
    }

    /**
     * creates a calendar based on given date and given locale.
     *
     * @param date
     * @param locale
     * @return a Calendar initate by given date.
     */
    public static Calendar getCalendar(Date date, Locale locale) {
        Calendar calendar;
        if (locale != null && FARSI_LANGUAGE.equals(locale.getLanguage())) {
            calendar = new PersianCalendar();
        } else {
            calendar = new GregorianCalendar();
        }

        calendar.setTime(date);

        return calendar;
    }


    /**
     * converts year, month and day to a Date based on specified Locale.
     *
     * @param year
     * @param month
     * @param day
     * @param locale
     * @return a Date object
     */
    public static Date getDate(int year, int month, int day, Locale locale) {
        Calendar calendar;

        if (locale != null && FARSI_LANGUAGE.equals(locale.getLanguage())) {
            calendar = new PersianCalendar();
        } else {
            calendar = new GregorianCalendar();
        }

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        return calendar.getTime();
    }

    public static Date getNPreviousDay(Date currentDate, int n) {
        GregorianCalendar calendar;
        int day;

        calendar = new GregorianCalendar();
        calendar.setTime(currentDate);
        day = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.set(Calendar.DAY_OF_YEAR, day - n);

        return calendar.getTime();
    }

    public static Date getNNextDay(Date currentDate, int n) {
        GregorianCalendar calendar;
        int day;

        calendar = new GregorianCalendar();
        calendar.setTime(currentDate);
        day = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.set(Calendar.DAY_OF_YEAR, day + n);

        return calendar.getTime();
    }


    public static Date getDate(XMLGregorianCalendar requestTime) {
        return (requestTime != null) ? requestTime.toGregorianCalendar().getTime() : null;
    }

    public static XMLGregorianCalendar getDate(Date requestTime) throws Exception {

        if (requestTime == null) {
            return null;
        }

        GregorianCalendar c = new GregorianCalendar();
        c.setTime(requestTime);
        XMLGregorianCalendar xmlDate;
        xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        return xmlDate;
    }

    public static Date formatDate(Date start, String format, Locale locale) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String date1 = simpleDateFormat.format(start);
        return DateUtils.parse(date1, format, true, locale);
    }

    public static void main(String[] args) {

        //DateFormat inputFormatter = new SimpleDateFormat("yyyy/MM/dd");
        String tempDate = "2017/03/22";
        Date myDate = DateUtils.parse(tempDate, "yyyy/MM/dd", true, DateUtils.ENGLISH_LOCALE);
        //LocalDate myDateWithoutTime = myDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        System.out.println("myDateWithoutTime is: " + getPreviousDay(myDate));


//		System.out.println("******* date is :" + DateUtils.parse("13950117" , "yyyyMMdd",true, DateUtils.FARSI_LOCALE));
//		Date today = new Date();
//
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
//		String date1 = simpleDateFormat.format(new Date());
//		System.out.println(date1);
//
//		System.out.println(formatDate(new Date(), "yyyyMMdd", ENGLISH_LOCALE));

//
//		Date date1 = DateUtils.parse("13950116" , "yyyyMMdd",true, DateUtils.FARSI_LOCALE);
//		Date date2 = DateUtils.parse("13950117" , "yyyyMMdd",true, DateUtils.FARSI_LOCALE);
//		Date date3 = DateUtils.parse("13950118" , "yyyyMMdd",true, DateUtils.FARSI_LOCALE);
//
//		System.out.println(date1.compareTo(today));
//		System.out.println(date2.compareTo(today));
//		System.out.println(date3.compareTo(today));

        //Date date1 =  DateUtils.parse("2016/04/09", "yyyy/MM/dd",true, DateUtils.ENGLISH_LOCALE);
        //Date date2 =  DateUtils.parse("2017/05/21" , "yyyy/MM/dd",true, DateUtils.ENGLISH_LOCALE);


//	    long differenceDayCount1 = DateUtils.getDifferenceInDay(date1, date2);
//	    //long differenceDayCount2 = DateUtils.getDifference(date2, date3);
//
//	    for (int i = 0; i <= differenceDayCount1; i++) {
//	    	System.out.println(date1);
//	    	date1 = DateUtils.getNNextDay(date1, 1);
//
//	    }


    }

    public static Date getDateWithoutTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

}
