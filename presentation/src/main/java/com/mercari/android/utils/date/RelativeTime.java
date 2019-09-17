package com.mercari.android.utils.date;

import android.content.Context;

import com.mercari.android.R;
import com.mercari.android.utils.Utils;

import java.util.Calendar;
import java.util.Date;

public class RelativeTime {

    private static final long SECOND = 1000;
    private static final long MINUTE = 60L * SECOND;
    private static final long HOUR = 60L * MINUTE;
    private static final long DAY = 24L * HOUR;
    private static final long WEEK = 7L * DAY;

    private static final int MAX_HOUR_DIFFERENCE = 8;
    private static final int MIN_WEEK_DIFFERENCE = 3;

    public static String getRelativeTime(long passedTime, Context context) {
        return getRelativeTime(System.currentTimeMillis(), passedTime, context);
    }

    public static String getRelativeTime(long present, long passedTime, Context context) {
        return Utils.toPersianNumber(relativeTime(present, passedTime, context));
    }

    private static String relativeTime(long present, long passedTime, Context context) {
        long difference = present - passedTime;

        if (difference < SECOND) {
            return context.getString(R.string.relativetime_momentsago);
        }

        if (difference < MINUTE) {
            return context.getString(R.string.relativetime_secondsago);
        }

        if (difference < HOUR) {
            return context.getString(R.string.relativetime_minutesago, difference / MINUTE);
        }

        long hourDifference = difference / HOUR;

        if (hourDifference <= MAX_HOUR_DIFFERENCE) {
            return context.getString(R.string.relativetime_hoursago, hourDifference);
        }

        if (difference < DAY) {
            Calendar presentCalendar = Calendar.getInstance();
            presentCalendar.setTimeInMillis(present);

            Calendar passedTimeCalendar = Calendar.getInstance();
            passedTimeCalendar.setTimeInMillis(passedTime);

            if (presentCalendar.get(Calendar.DAY_OF_MONTH) == passedTimeCalendar.get(Calendar.DAY_OF_MONTH)) {
                return context.getString(R.string.relativetime_hoursago, hourDifference);
            } else {
                return context.getString(R.string.relativetime_yesterday);
            }
        }

        long dayDifference = difference / DAY;

        if (dayDifference <= 1) {
            return context.getString(R.string.relativetime_yesterday);
        }

        long weekDifference = difference / WEEK;

        if (weekDifference < MIN_WEEK_DIFFERENCE) {
            return context.getString(R.string.relativetime_daysago, dayDifference);
        }

        return context.getString(R.string.relativetime_weeksago, weekDifference);
    }

    public static long setHourOfDate(long date, int hour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(date));
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime().getTime();
    }

    public static boolean isOneDayPassed(long firstTime, long secondTime) {
        long difference = firstTime - secondTime;
        return difference > DAY;
    }

    public static String getRelativeDayTime(long passedTime, Context context) {
        long present = System.currentTimeMillis();
        long difference = present - passedTime;
        if (difference < DAY) {
            return "امروز";
        }

        long dayDifference = difference / DAY;
        if (dayDifference < 2)
            return context.getString(R.string.relativetime_yesterday);
        else
            return context.getString(R.string.relativetime_daysago, dayDifference);
    }
}
