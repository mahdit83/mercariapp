/**
 * $HeadURL: http://faramarz:faramarz123@192.168.50.33/svn/en/trunk/mbs/implementation/adpdigital/utils/src/main/java/com/adpdigital/commons/utils/StringUtils.java $
 */
package com.mercari.android.utils.date;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;


public class StringUtils {
    /**
     *
     */
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,###");


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
    public static final Locale ENGLISH_LOCALE = Locale.ENGLISH;


    /**
     * Converts numbers in the locale specific notations
     *
     * @param locale
     * @param text
     * @return modified version of the input text
     */
    public static String getLocaleText(Locale locale, String text) {

        if (text != null && locale != null && FARSI_LANGUAGE.equalsIgnoreCase(locale.getLanguage())) {
            StringBuffer sb;
            char c;

            sb = new StringBuffer();

            for (int i = 0; i < text.length(); i++) {
                c = text.charAt(i);
                if (c >= '0' && c <= '9') {
                    c = (char) (c + 0x6C0);
                }
                sb.append(c);
            }

            text = sb.toString();
        }

        return text;
    }


    /**
     * removes all character ch in the begining of given str
     *
     * @param str
     * @param ch
     * @return trimmed string
     */
    public static String trimLeadingChars(String str, char ch) {
        String prunedString;
        int i;

        if (str == null) {
            return null;
        }

        prunedString = str;

        for (i = 0; i < str.length(); ++i) {
            if (str.charAt(i) != ch) {
                prunedString = str.substring(i, str.length());
                break;
            }
        }

        if (i == str.length()) {
            prunedString = String.valueOf(ch);
        }

        return prunedString;
    }


    /**
     *
     * @param str
     * @return true if string containg a number
     */
    public static boolean isNumber(String str) {

        if (str == null || "".equalsIgnoreCase(str)) {
            return false;
        }

        for (int i = 0; i < str.length(); i++) {

            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     *
     * @param src
     * @return true if given string contains at least one letter
     */
    public static boolean hasText(String src) {
        if (src == null) {
            return false;
        }

        src = src.trim();

        return src.length() > 0;
    }

    /**
     *
     * @param str
     * @return true if given string contains only persian letter
     */
    public static boolean isPersianString(String str) {
        if (str == null || "".equalsIgnoreCase(str)) {
            return false;
        }

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) > 127) {
                // this is a persian char
                return true;
            }
        }

        return false;
    }

    /**
     * append zero to a String created from a number
     *
     * @param length
     * @return a String
     */
    public static String format(String value, int length) {
        StringBuffer result;

        result = new StringBuffer(value);

        while(result.length() < length) {
            result.insert(0,"0");
        }

        return result.toString();
    }

    /**
     *
     * @param str
     * @param ch
     * @return trimized string
     */
    public static String trim(String str, char ch) {
        String prunedString;
        int i;

        if (str == null) {
           return null;
        }


        prunedString = str;


        for (i = 0; i < str.length(); ++i) {
            if (str.charAt(i) != ch) {
                prunedString = str.substring(i, str.length());
                break;
            }
        }


        if (i == str.length()) {
            prunedString = String.valueOf(ch);
        }

        return prunedString;
    }


    /**
     * replace a partial text with its equalant
     * @param aMatcher
     * @return
     */
    protected static String getReplacement(Map model, Matcher aMatcher) throws Exception {
        String s;
        String beanName;
        Object value;


        s = aMatcher.group(0);
        s = s.substring(2, s.length() - 1);


        if (s.indexOf('.') == 0) {
            s = " " + s;
        }

        beanName = s;

        return ((value = model.get(beanName)) != null) ? value.toString() : "";
    }
    /**
     *
     * @param items
     * @param value
     * @return true if all items equals given value
     */
    public static boolean equals(String[] items, String value) {
        for (String item : items) {
            if (!item.equals(value)) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param s
     * @param t
     * @return true if s and t are same
     */
    public  static boolean equals(String s, String t) {

        if (s == null) {
            return t == null;
        }

        if (t == null) {
            return false;
        }

        s = s.trim();

        t = t.trim();


        return s.equals(t);
    }

    public static String replaceLongString(String mainStr){
    	String[] serchStrs =   { "شرکت","شركت", "شرﮐت","شرﻛت","سهم فروشنده", "کارمزد فروش", "کارمزد خريد", "کارمزد خرید", "کارمزد خرﻳد", "کارمزد خرﻳد" };
		String[] replaceStrs = { "", "", "", "", "س.ف", "ک.ف", "ک.خ", "ک.خ", "ک.خ", "ک.خ"};

        for (int i= 0; i < serchStrs.length ; i++){
        	if (mainStr.contains(serchStrs[i])){
                mainStr = mainStr.replaceAll(serchStrs[i], replaceStrs[i]);
            }
        }

        System.out.println(mainStr);

    	return mainStr;

   }

   public static void main(String[] args) {

		String url = "%D8%AC%D9%84%D8%A7%D9%84" ;
		String url2 = "sara" ;
		String result = "" ;
		try {
			result = URLDecoder.decode(url, "UTF-8");
			System.out.println("result is :" + result);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
