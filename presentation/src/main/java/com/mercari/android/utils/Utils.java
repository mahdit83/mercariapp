package com.mercari.android.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.FileProvider;

import com.mercari.android.R;
import com.mercari.android.utils.date.DateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.mercari.android.utils.date.StringUtils.FARSI_LOCALE;

public class Utils {

  private static final String TAG = "Utils";

  private static final String[] persianNumbers =
      new String[]{"۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹"};

  private static final String[] englishNumbers =
      new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

  @NonNull
  public static String toPersianNumber(Long number) {
    return toPersianNumber(String.valueOf(number));
  }

  @NonNull
  public static String toPersianNumber(int number) {
    return toPersianNumber(String.valueOf(number));
  }

  @NonNull
  public static String toPersianNumber(String text) {
    if (TextUtils.isEmpty(text))
      return "";
    StringBuilder out = new StringBuilder();
    int length = text.length();
    for (int i = 0; i < length; i++) {
      char c = text.charAt(i);
      if ('0' <= c && c <= '9') {
        int number = Integer.parseInt(String.valueOf(c));
        out.append(persianNumbers[number]);
      } else if (c == '٫' || c == ',')
        out.append('،');
      else
        out.append(c);
    }
    return out.toString();
  }

  @NonNull
  public static String toEnglishNumber(Long number) {
    return toEnglishNumber(String.valueOf(number));
  }

  @NonNull
  public static String toEnglishNumber(int number) {
    return toEnglishNumber(String.valueOf(number));
  }

  @NonNull
  public static String toEnglishNumber(String text) {
    if (TextUtils.isEmpty(text))
      return "";
    StringBuilder out = new StringBuilder();
    int length = text.length();
    for (int i = 0; i < length; i++) {
      char c = text.charAt(i);
      if ('۰' <= c && c <= '۹') {
        int number = Integer.parseInt(String.valueOf(c));
        out.append(englishNumbers[number]);
      } else if (c == '٫' || c == '،')
        out.append(',');
      else
        out.append(c);
    }
    return out.toString();
  }

  public static void showToast(Context context, String message) {
    showToast(context, message, Toast.LENGTH_SHORT);
  }

  public static void showToast(Context context, String message, int length) {
    Toast toast = Toast.makeText(context, message, length);
    ViewGroup toastLayout = (ViewGroup) toast.getView();
    View firstChild = toastLayout.getChildAt(0);
    if (firstChild instanceof TextView) {
//      Typeface font = FontHolder.newInstance(context).getFont(FontHolder.TESHRIN);
//      ((TextView) firstChild).setTypeface(font);
    }
    toast.show();
  }

  public static void showToast(Context context, @StringRes int stringId) {
    showToast(context, context.getResources().getString(stringId), Toast.LENGTH_SHORT);
  }

  public static void showToast(Context context, int messageRes, int length) {
    try {
      String message = context.getResources().getString(messageRes);
      showToast(context, message, length);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static int parseColor(String color) {
    if (color == null) {
      return 0;
    }

    try {
      return Color.parseColor(color);
    } catch (Throwable t) {
      if (!color.startsWith("#")) {
        try {
          return Color.parseColor("#" + color);
        } catch (Throwable t1) {
          return 0;
        }
      }

      return 0;
    }
  }

  @Nullable
  public static String formatDate(String date, boolean embedLTR) {
    if (TextUtils.isEmpty(date) || date.length() < 6) {
      return "";
    }

    String formatted = date.substring(0, 2) + "/" + date.substring(2, 4) + "/" + date
        .substring(4, 6);

    if (embedLTR)
      return Utils.embedLTR(Utils.toPersianNumber(formatted));
    else
      return Utils.toPersianNumber(formatted);
  }

  public static String formatExpDate(ExpDateValue value) {
    return String.format(Locale.getDefault(), "%02d/%02d", value.getYear(), value.getMonth());
  }

  public static String formatExpDateForServer(String expDate) {
    if (!TextUtils.isEmpty(expDate)) {
      if (expDate.length() == 7) {
      return expDate.replace("/","").substring(2,6);
      } else {
        return expDate.replace("/","").substring(0,4);
      }
    } else {
      return null;
    }
  }

  public static String embedRTL(String text) {
    if (TextUtils.isEmpty(text)) {
      return "";
    }
    return '\u202B' + text + '\u202B';
  }

  public static String embedLTR(String text) {
    if (TextUtils.isEmpty(text)) {
      return "";
    }
    return '\u202A' + text + '\u202A';
  }

  public static String formatPan(String pan) {
    String separator = "   ";
    return formatPan(pan, separator);
  }

  public static String formatPan(String pan, String separator) {
    StringBuilder sb = new StringBuilder();

    if (pan.length() <= 4) {
      return pan;
    }

    sb.append(pan.substring(0, 4)).append(separator);

    if (pan.length() <= 8) {
      sb.append(pan.substring(4, pan.length()));

      return sb.toString();
    }

    sb.append(pan.substring(4, 8)).append(separator);

    if (pan.length() <= 12) {
      sb.append(pan.substring(8, pan.length()));

      return sb.toString();
    }

    sb.append(pan.substring(8, 12)).append(separator).append(pan.substring(12, pan.length()));

    return sb.toString();
  }

  public static String formatPanWithMask(String pan) {
    String separator = "   ";
    return formatPanWithMask(pan, separator);
  }

  public static String formatPanWithMask(String pan, String separator) {
    pan = embedLTR(pan.substring(0, 6) + "******" + pan.substring(12));

    return pan.substring(0, 5)
        .concat(separator)
        .concat(pan.substring(5, 9))
        .concat(separator)
        .concat(pan.substring(9, 13))
        .concat(separator)
        .concat(pan.substring(13));
  }

  public static ArrayList<String> getNumbers(String s) {
    ArrayList<String> numbers = new ArrayList<>(12);

    StringBuilder sb = null;

    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);

      boolean characterIsDigit = c >= '0' && c <= '9';

      if (sb == null) {
        if (!characterIsDigit) {
          continue;
        }

        sb = new StringBuilder();
        sb.append(c);
      } else {
        if (characterIsDigit) {
          sb.append(c);
        } else {
          numbers.add(sb.toString());
          sb = null;
        }
      }
    }

    if (sb != null) {
      numbers.add(sb.toString());
    }

    return numbers;
  }



  @NonNull
  public static String addThousandSeparator(long s) {
    return addThousandSeparator(String.valueOf(s));
  }

  @NonNull
  public static String addThousandSeparator(String s) {
    if (s == null) return "";
    StringBuilder f = new StringBuilder();
    String temp = s.replaceAll("[^0-9۰-۹]+", "");
    for (int i = 0; i < temp.length(); ++i) {
      f.append(temp.charAt(i));
      if (((temp.length() - 1) - i) % 3 == 0 && ((temp.length() - 1) - i) != 0) {
        f.append("٫");
      }
    }
    return toPersianNumber(f.toString());
  }

  @NonNull
  public static String addThousandSeparator(long s, String separator) {
    return addThousandSeparator(String.valueOf(s), separator);
  }

  @NonNull
  public static String addThousandSeparator(String s, String separator) {
    if (s == null) return "";
    StringBuilder f = new StringBuilder();
    String temp = s.replaceAll("[^0-9۰-۹]+", "");
    for (int i = 0; i < temp.length(); ++i) {
      f.append(temp.charAt(i));
      if (((temp.length() - 1) - i) % 3 == 0 && ((temp.length() - 1) - i) != 0) {
        f.append(separator);
      }
    }
    return f.toString();
  }

  public static File createImageFile(Context context) throws IOException {
    // Create an image file name
    final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd_HHmmss");
    String timeStamp = DATE_FORMAT.format(new Date());
    String imageFileName = "JPEG_" + toEnglishNumber(timeStamp) + "_";
    File cacheDir = context.getFilesDir();
    return File.createTempFile(
            imageFileName,
            ".jpg",
            cacheDir
    );
  }

  public static Bitmap scaleBitmap(Bitmap image, int maxWidth, int maxHeight) {
    if (image != null && maxHeight > 0 && maxWidth > 0) {
      int width = image.getWidth();
      int height = image.getHeight();
      double ratio = (double) Math.max(maxHeight, maxWidth) / (double) Math.max(width, height);
      int finalWidth = (int) (width * ratio);
      int finalHeight = (int) (height * ratio);
      Log.d(TAG, "final width: " + finalWidth + " & final height: " + finalHeight);
      image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
      return image;
    } else {
      return image;
    }
  }

  public static Bitmap rotate(Bitmap bm, int rotation) {
    if (rotation != 0) {
      Matrix matrix = new Matrix();
      matrix.postRotate(rotation);
      Bitmap bmOut = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
      return bmOut;
    }
    return bm;
  }

  public static int getRotationFromGallery(Context context, Uri imageUri) {
    String[] columns = {MediaStore.Images.Media.ORIENTATION};
    Cursor cursor = context.getContentResolver().query(imageUri, columns, null, null, null);
    if (cursor == null) return 0;

    cursor.moveToFirst();

    int orientationColumnIndex = cursor.getColumnIndex(columns[0]);
    int anInt = cursor.getInt(orientationColumnIndex);
    cursor.close();
    return anInt;
  }

  public static int getRotationFromCamera(Context context, Uri imageFile) {
    int rotate = 0;
    try {

      context.getContentResolver().notifyChange(imageFile, null);
      ExifInterface exif = new ExifInterface(imageFile.getPath());
      int orientation = exif.getAttributeInt(
              ExifInterface.TAG_ORIENTATION,
              ExifInterface.ORIENTATION_NORMAL);

      switch (orientation) {
        case ExifInterface.ORIENTATION_ROTATE_270:
          rotate = 270;
          break;
        case ExifInterface.ORIENTATION_ROTATE_180:
          rotate = 180;
          break;
        case ExifInterface.ORIENTATION_ROTATE_90:
          rotate = 90;
          break;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rotate;
  }

  public static String getJalaliFormattedDate(Long date, boolean withTime, boolean embedLTR) {
    return getJalaliFormattedDate(new Date(date), withTime, embedLTR);
  }

  public static String getJalaliFormattedDate(Date date, boolean withTime, boolean embedLTR) {
    String format = withTime ? "yyyy/MM - HH:mm" : "yyyy/MM";
//        String format = withTime ? "yy/MM-HH:mm" : "yy/MM";
    return getJalaliFormattedDate(date, format, embedLTR);
  }

  public static String getJalaliFormattedDateWithDay(Long date, boolean withTime, boolean embedLTR) {
    return getJalaliFormattedDateWithDay(new Date(date), withTime, embedLTR);
  }

  public static String getJalaliFormattedDateWithDay(Date date, boolean withTime, boolean embedLTR) {
//        String format = withTime ? "yyyy/MM/dd-HH:mm" : "yyyy/MM/dd";
    String format = withTime ? "yyyy/MM/dd - HH:mm" : "yy/MM/dd";
    return getJalaliFormattedDate(date, format, embedLTR);
  }

  public static String getJalaliFormattedDate(Long date, String format, boolean embedLTR) {
    return getJalaliFormattedDate(new Date(date), format, embedLTR);
  }

  public static String getJalaliFormattedDate(Date date, String format, boolean embedLTR) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);

    String jalaliDate = DateUtils.getLocaleDate(FARSI_LOCALE, date, format, true);

//        JalaliCalendar.YearMonthDate gregorian = new JalaliCalendar.YearMonthDate(
//                cal.getInstance(Calendar.YEAR),
//                cal.getInstance(Calendar.MONTH) + 1,
//                cal.getInstance(Calendar.DATE) - 1 // TODO
//        );
//
//        JalaliCalendar.YearMonthDate jalaliYearMonthDate = JalaliCalendar.gregorianToJalali(gregorian);
//        String jalaliDate = String.format(Locale.getDefault(), "%1$04d/%2$02d/%3$02d",
//                jalaliYearMonthDate.getYear(), jalaliYearMonthDate.getMonth(), jalaliYearMonthDate.getDate());
//
//        if (withTime) {
//            String time = String.format(Locale.getDefault(), "%1$02d:%2$02d",
//                    cal.getInstance(Calendar.HOUR_OF_DAY), cal.getInstance(Calendar.MINUTE));
//            jalaliDate = jalaliDate + " - " + time;
//        }
//        String formatted = toPersianNumber(jalaliDate);
    if (embedLTR) {
      return embedLTR(jalaliDate);
    } else {
      return jalaliDate;
    }
  }

}
