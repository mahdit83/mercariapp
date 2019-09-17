package com.mercari.android.MVVM.ui.components;


public class PersianNumberHelper {


    static String[] farsinumber = {"۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹", "۰"};
    static String[] englishNumber = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};

    static public String persianToEnglish(String str) {


        String temp = "", input = "";
        if (str != null) {
            input = str;

            for (int i = 0; i < farsinumber.length; i++) {

                while (input.contains(farsinumber[i])) {
                    input = input.replace(farsinumber[i], englishNumber[i]);
                    temp = temp + englishNumber[i];
                }
            }
        }
        return input;

    }
    static public String englishToPersian(String str) {


        String temp = "", input = "";
        if (str != null) {
            input = str;

            for (int i = 0; i < englishNumber.length; i++) {

                while (input.contains(englishNumber[i])) {
                    input = input.replace(englishNumber[i], farsinumber[i]);
                    temp = temp + farsinumber[i];
                }
            }
        }
        return input;
    }
}
