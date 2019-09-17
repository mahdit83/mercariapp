package com.mercari.android.utils;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.IntRange;

import com.mercari.android.utils.date.DateUtils;

import java.util.Date;
import java.util.Locale;

public class ExpDateValue implements Parcelable {

    public static final int VALIDATION_CORRECT = 0;
    public static final int VALIDATION_INCORRECT = 1;
    public static final int VALIDATION_EXPIRED = 2;
    public static final Creator<ExpDateValue> CREATOR = new Creator<ExpDateValue>() {
        @Override
        public ExpDateValue createFromParcel(Parcel in) {
            return new ExpDateValue(in);
        }

        @Override
        public ExpDateValue[] newArray(int size) {
            return new ExpDateValue[size];
        }
    };
    private static final int LIMIT_YEAR = 10;
    private int mMonth;
    private int mYear;

    public ExpDateValue() {
    }

    public ExpDateValue(int year, @IntRange(from = 1, to = 12) int month) {
        this.mMonth = month;
        this.mYear = year;
    }

    protected ExpDateValue(Parcel in) {
        mMonth = in.readInt();
        mYear = in.readInt();
    }

    public static int validate(String expDate) {
        expDate = expDate.replaceAll("[^0-9۰-۹]", "");

        if (null == expDate || expDate.length() != 4) {
            return VALIDATION_INCORRECT;
        }

        int year = Integer.parseInt(expDate.substring(0, 2));
        int month = Integer.parseInt(expDate.substring(2, 4));

        return validate(year, month);
    }

    public static int validate(int year, int month) {
        year = getFullYear(year);

        if (month < 1 || month > 12) {
            return VALIDATION_INCORRECT;
        }

        int day = month < 7
                ? 31
                : month < 12
                ? 30
                : (year - 1395) % 4 == 0
                ? 30
                : 29;

        Date date = DateUtils.getDate(year, month, day, DateUtils.FARSI_LOCALE);
        long limitYear = LIMIT_YEAR * 365 * 24 * 60 * 60 * 1000L;
        Date limitDate = new Date(System.currentTimeMillis() + limitYear);

        if (date.after(new Date()) && date.before(limitDate)) {
            return VALIDATION_CORRECT;
        } else {
            return VALIDATION_EXPIRED;
        }
    }

    public static ExpDateValue parseExpDate(String expDate) {
        if (null == expDate || expDate.length() != 4) {
            return null;
        } else {
            int year = Integer.parseInt(expDate.substring(0, 2));
            int month = Integer.parseInt(expDate.substring(2, 4));
            return new ExpDateValue(year, month);
        }
    }

    public static int getFullYear(int year) {
        if (year <= 99) {
            if (year > 95) {
                return year + 1300;
            } else {
                return year + 1400;
            }
        }
        if (year == 0) {
            return 1400;
        }
        return year;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mMonth);
        dest.writeInt(mYear);
    }

    public int validate() {
        return validate(mYear, mMonth);
    }

    private String validateYearLength(int year) {

        String result = String.valueOf(year);
        if (result.length() == 0) {
            result = "00";
        }
        if (result.length() == 1) {
            result = "0" + result;
        }
        return result;
    }

    public int getMonth() {
        return mMonth;
    }

    public void setMonth(int month) {
        this.mMonth = month;
    }

    public int getYear() {
        return mYear;
    }

    public void setYear(int year) {
        this.mYear = year;
    }

    public int getFullYear() {
        return getFullYear(mYear);
    }

    public boolean isValid() {
        return validate(mYear, mMonth) == VALIDATION_CORRECT;
    }

    public String getStringValue() {

        String stringYear = validateYearLength(mYear);

        String year = stringYear.substring(stringYear.length() - 2, stringYear.length());
        return year + String.format(Locale.getDefault(), "%02d", mMonth);
    }
}
