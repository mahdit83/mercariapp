package com.mercari.android.utils;

public interface PatternStrings {

    String S_EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    String S_MOBILE_PATTERN = "^\\+98[9][0-9]{9}$";
    String S_MOBILE_PATTERN_BY_ZERO = "^[0][9][0-9]{9}";
    String S_USERNAME_PATTERN = "[._a-zA-Z0-9]{4,25}";
    String S_NATIONAL_CARD_PATTERN = "([1]{10}|[2]{10}|[3]{10}|[4]{10}|[5]{10}|[6]{10}|[7]{10}|[8]{10}|[9]{10})$";
    String S_PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-z]).{6,225}$";
    String S_WEBSITE_PATTERN = "(https?://)?([a-zA-Z]\\w*\\.)+[a-zA-Z]\\w*(\\:\\d+)?(/\\w+)*(\\.\\w+)?/?(\\?([a-zA-Z]\\w*\\=\\w*&)*([a-zA-Z]\\w*\\=\\w*))?";
    String S_VERIFICATION_CODE_PATTERN = "[0-9]{5,}";
    String S_NAME_PATTERN = ".{2,50}";

}
