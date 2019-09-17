package com.mercari.android.utils;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.Arrays;
import java.util.regex.Pattern;

public class ValidationUtil implements PatternStrings {

  public static final int UNKNOWN = 0;
  public static final int INVALID = 1;
  public static final int VALID = 2;
  // Valid reasons
  public static final int REASON_NO_REASON = -1;
  // Invalid reasons
  public static final int REASON_NO_INPUT = 0;
  public static final int REASON_BAD_FORMAT = 1;
  public static final int REASON_ALREADY_EXISTS = 2;
  public static final int REASON_DOESNT_EXIST = 3;
  // Undone reasons
  // Also REASON_NO_REASON
  public static final int REASON_NO_INTERNET = 4;
  public static final int REASON_CONNECTION_PROBLEM = 5;
  public static final int REASON_IS_VALIDATING = 6;
  private static final String TAG = "ValidationUtil";
  private static final long TIME_BEFORE_VALIDATION_START = 500;
  private static final Pattern EMAIL_PATTERN = Pattern.compile(S_EMAIL_PATTERN);
  private static final Pattern MOBILE_PATTERN = Pattern.compile(S_MOBILE_PATTERN);
  private static final Pattern MOBILE_PATTERN_BY_ZERO = Pattern.compile(S_MOBILE_PATTERN_BY_ZERO);
  private static final Pattern USERNAME_PATTERN = Pattern.compile(S_USERNAME_PATTERN);
  private static final Pattern PASSWORD_PATTERN = Pattern.compile(S_PASSWORD_PATTERN);
  private static final Pattern WEBSITE_PATTERN = Pattern.compile(S_WEBSITE_PATTERN);
  private static final Pattern CODE_PATTERN = Pattern.compile(S_VERIFICATION_CODE_PATTERN);
  private static final Pattern NAME_PATTERN = Pattern.compile(S_NAME_PATTERN);
  private static final Pattern NATIONAL_CARD_PATTERN = Pattern.compile(S_NATIONAL_CARD_PATTERN);

  public static boolean isValidNationalCard(String username) {
    return isValid(NATIONAL_CARD_PATTERN, username);
  }

  public static boolean isValidUsername(String username) {
    return isValid(USERNAME_PATTERN, username);
  }

  public static boolean isValidEmail(String email) {
    return isValid(EMAIL_PATTERN, email);
  }

  public static boolean isValidPassword(String password) {
    return isValid(PASSWORD_PATTERN, password);
  }

  public static boolean isValidRePassword(String password, String rePassword) {
    if (!password.equals(rePassword)) {
      return false;
    }
    return isValid(PASSWORD_PATTERN, password);
  }

  public static boolean isValidMobile(String mobile) {
    return isValid(MOBILE_PATTERN, mobile);
  }

  public static boolean isValidMobileByZero(String mobile) {
    return isValid(MOBILE_PATTERN_BY_ZERO, mobile);
  }

  public static boolean isValidWebsite(String website) {
    return isValid(WEBSITE_PATTERN, website);
  }

  public static boolean isValidCode(String code) {
    return isValid(CODE_PATTERN, code);
  }

  public static boolean isValidFirstName(String firstName) {
    return isValid(NAME_PATTERN, firstName);
  }

  public static boolean isValidLastName(String lastName) {
    return isValidFirstName(lastName);
  }

  private static boolean isValid(Pattern pattern, String input) {
    return pattern.matcher(input).matches();
  }

  public static boolean isValidNationalCode(String melliCode) {
    String[] identicalDigits = {"0000000000", "1111111111", "2222222222", "3333333333",
        "4444444444", "5555555555", "6666666666", "7777777777", "8888888888", "9999999999",
        "۰۰۰۰۰۰۰۰۰۰", "۱۱۱۱۱۱۱۱۱۱", "۲۲۲۲۲۲۲۲۲۲", "۳۳۳۳۳۳۳۳۳۳", "۴۴۴۴۴۴۴۴۴۴", "۵۵۵۵۵۵۵۵۵۵",
        "۶۶۶۶۶۶۶۶۶۶", "۷۷۷۷۷۷۷۷۷۷", "۸۸۸۸۸۸۸۸۸۸", "۹۹۹۹۹۹۹۹۹۹"};
    if (melliCode.trim().isEmpty()) {
      return false; // Melli Code is empty
    } else if (melliCode.length() != 10) {
      return false; // Melli Code is less or more than 10 digits
    } else if (Arrays.asList(identicalDigits).contains(melliCode)) {
      return false; // Fake Melli Code
    } else {
      int sum = 0;
      for (int i = 0; i < 9; i++) {
        sum += Character.getNumericValue(melliCode.charAt(i)) * (10 - i);
      }
      int lastDigit;
      int divideRemaining = sum % 11;
      if (divideRemaining < 2) {
        lastDigit = divideRemaining;
      } else {
        lastDigit = 11 - (divideRemaining);
      }
      if (Character.getNumericValue(melliCode.charAt(9)) == lastDigit) {
        return true;
      } else {
        return false; // Invalid MelliCode
      }
    }
  }

  public interface ValidationViewUpdater {

    void onValidationStarted();

    void onValidationFinished();

    void updateViewValidation(int id, int validationStatus, int reason);

  }

  public interface ValidationResultListener {

    void onValidationResult(int validationResult, int reason);

    void onValidationFailed(Exception exception, int reason);

  }

  public interface Validator {

    boolean isEssential();

    boolean validatesImmediately();

    int fastValidation(String input, ValidationResultListener listener);

    void validate(String input, ValidationResultListener listener);

    int getExplanation(int reason);

  }

  protected static class ValidationField implements TextWatcher, ValidationResultListener {

    private Handler mHandler;

    private int mId;

    private EditText mInput;

    private ValidationViewUpdater mValidationViewUpdater;

    private Validator mValidator;

    private int mValidationStatus = UNKNOWN;
    private int mReason = REASON_NO_REASON;

    private boolean mIsValidating = false;
    private ValidationResultListener mFastValidationResultListener = new ValidationResultListener() {

      @Override
      public void onValidationResult(int validationResult, int reason) {
        if (validationResult != UNKNOWN) {
          setValidationStatus(validationResult, reason);
        }
      }

      @Override
      public void onValidationFailed(Exception exception, int reason) {
      }

    };
    private Runnable mValidationChecker = new Runnable() {

      @Override
      public void run() {
        String input = getInput();

        mIsValidating = true;

        mValidationViewUpdater.onValidationStarted();

        mValidator.validate(input, new ValidationResultReceiver(input));
      }

    };

    protected ValidationField(int id, EditText input, Validator validator,
        ValidationViewUpdater validationViewUpdater, Handler handler) {
      mHandler = handler;

      mId = id;

      mInput = input;
      mInput.addTextChangedListener(this);

      mValidationViewUpdater = validationViewUpdater;

      mValidator = validator;
    }

    public boolean isValidating() {
      return mIsValidating;
    }

    public int getValidationStatus() {
      if (mIsValidating) {
        return UNKNOWN;
      }

      if (mValidationStatus == UNKNOWN) {
        checkValidation(true);
      }

      return mValidationStatus;
    }

    public int getReason() {
      if (mIsValidating) {
        return REASON_IS_VALIDATING;
      }

      return mReason;
    }

    public void forceValidation(boolean onlyImmediate) {
      if (mValidationStatus == UNKNOWN) {
        mReason = REASON_NO_REASON;

        checkValidation(onlyImmediate);
      }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
      checkValidation(false);
    }

    private void checkValidation(boolean onlyImmediate) {
      String input = getInput();

      if (input.length() == 0) {
        if (mValidator.isEssential()) {
          setValidationStatus(INVALID, REASON_NO_INPUT);
        } else {
          setValidationStatus(VALID, REASON_NO_REASON);
        }
        return;
      }

      int fastValidationResult = mValidator.fastValidation(input, mFastValidationResultListener);

      if (fastValidationResult != UNKNOWN) {
        return;
      }

      if (mValidator.validatesImmediately()) {
        mValidator.validate(input, this);
      } else if (!onlyImmediate) {
        mHandler.removeCallbacks(mValidationChecker);
        mHandler.postDelayed(mValidationChecker, TIME_BEFORE_VALIDATION_START);
      }
    }

    @Override
    public void onValidationResult(int validationResult, int reason) {
      setValidationStatus(validationResult, reason);
    }

    @Override
    public void onValidationFailed(Exception exception, int reason) {
      Log.e(TAG, "Failed to perform validation.", exception);

      setValidationStatus(UNKNOWN, reason);
    }

    private void setValidationStatus(int validationStatus, int reason) {
      if (mValidationStatus == validationStatus && mReason == reason) {
        return;
      }

      mValidationStatus = validationStatus;
      mReason = reason;

      mValidationViewUpdater.updateViewValidation(mId, mValidationStatus, reason);
    }

    private String getInput() {
      return mInput.getText().toString();
    }

    private class ValidationResultReceiver implements ValidationResultListener {

      private String mValidatingInput;

      protected ValidationResultReceiver(String input) {
        mValidatingInput = input;
      }

      @Override
      public void onValidationResult(int validationResult, int reason) {
        if (mValidatingInput.equals(getInput())) {
          mIsValidating = false;

          mValidationViewUpdater.onValidationFinished();

          setValidationStatus(validationResult, reason);
        }
      }

      @Override
      public void onValidationFailed(Exception exception, int reason) {
        Log.e(TAG, "Failed to perform validation.", exception);

        if (mValidatingInput.equals(getInput())) {
          mIsValidating = false;

          mValidationViewUpdater.onValidationFinished();

          setValidationStatus(UNKNOWN, reason);
        }
      }

    }

  }

  public static abstract class AbsValidator implements Validator {

    @Override
    public int fastValidation(String input, ValidationResultListener listener) {
      listener.onValidationResult(UNKNOWN, REASON_NO_REASON);

      return UNKNOWN;
    }

    @Override
    public int getExplanation(int reason) {
//            switch (reason) {
//                case ValidationUtil.REASON_NO_INTERNET:
//                    return R.string.validationhandlnig_nointernet;
//                case ValidationUtil.REASON_CONNECTION_PROBLEM:
//                    return R.string.validationhandling_connectionproblem;
//            }

      return 0;
    }

  }

}
