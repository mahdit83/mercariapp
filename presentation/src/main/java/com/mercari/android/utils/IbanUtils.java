package com.mercari.android.utils;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.math.BigInteger;

/**
 * These utils works on iranian type of IBAN which has this form:
 * IRkk 0bb0 nnnn nnnn nnnn nnnn nn
 * Where IR is the prefix, kk represents the IBAN checksum, 0 represents a 0 character,
 * bb represents a bank code and 18 n represents 18 digit format bank deposit number.
 */
public class IbanUtils {

    public static final int IBAN_LENGTH = 26;
    public static final int IBAN_LENGTH_PLAIN = 24;
    public static final int IBAN_LENGTH_FORMATTED = 32;
    public static final int CHECKSUM_LENGTH = 2;
    public static final String IRAN_IBAN_PREFIX = "IR";
    public static final String IRAN_IBAN_PREFIX_NUMERIC_EQUIVALENCE = "1827";
    private static final String IBAN_DEFAULT_SEPARATOR = " ";

    @NonNull
    public static String toPlain(String iban) {
        if (null == iban) {
            return "";
        }

        iban = iban.replaceAll("[^\\d]", "");

        return iban;
    }

    @Nullable
    public static String removePrefix(String iban) {
        if (null != iban && iban.startsWith(IRAN_IBAN_PREFIX)) {
            iban = iban.substring(IRAN_IBAN_PREFIX.length());
        }
        return iban;
    }

    public static boolean validateIban(String iban) {
        return validateIban(iban, false);
    }

    public static boolean validateIban(String iban, boolean strict) {
        if (null == iban) {
            return false;
        }

        if (strict && !iban.startsWith(IRAN_IBAN_PREFIX)) {
            return false;
        }

        iban = toPlain(iban);

        if (iban.length() != IBAN_LENGTH_PLAIN) {
            return false;
        }

        String checksum = iban.substring(0, CHECKSUM_LENGTH);
        String toCheck = iban.substring(CHECKSUM_LENGTH)
                + IRAN_IBAN_PREFIX_NUMERIC_EQUIVALENCE
                + checksum;

        BigInteger modulo = new BigInteger(toCheck).mod(new BigInteger("97"));

        return modulo.intValue() == 1;
    }

    @NonNull
    public static String formatIban(String rawIban) {
        return formatIban(rawIban, true, IBAN_DEFAULT_SEPARATOR);
    }

    @NonNull
    public static String formatIban(String rawIban, boolean includePrefix, String separator) {
        StringBuilder iban = new StringBuilder(toPlain(rawIban));

        for (int i = 22; i > 0; i -= 4) {
            if (iban.length() > i) {
                iban.insert(i, separator);
            }
        }

        return (includePrefix ? IRAN_IBAN_PREFIX : "") + iban;
    }



    public static boolean ibansAreEqual(String a, String b) {
        if (TextUtils.equals(a, b)) return true;
        if (a != null && b != null) {
            return IbanUtils.toPlain(a).equals(IbanUtils.toPlain(b));
        }
        return false;
    }
}
