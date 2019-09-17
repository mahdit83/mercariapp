package com.mercari.android.MVVM.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatEditText;

import com.mercari.android.BuildConfig;
import com.mercari.android.R;
import com.mercari.android.utils.Utils;

public class PersianEditText extends AppCompatEditText {

    private final String TAG = "mahdi";
    private boolean numberMode = false;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String inputText = s.toString();
            if (numberMode) {
                inputText = inputText.replaceAll("[^0-9۰-۹]", "");
                handleFocusForPersianNumbers();
            }
            PersianEditText.this.removeTextChangedListener(textWatcher);
            String text = Utils.toPersianNumber(inputText);
            Log.i(TAG, "afterTextChanged: " + text);
            s.clear();
            s.append(text);
            PersianEditText.this.addTextChangedListener(textWatcher);
        }
    };

    public PersianEditText(Context context) {
        super(context);
        initialize();
        setMyTypeface();
    }

    public PersianEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        handleAttributes(context, attrs, 0);
        initialize();
        setMyTypeface();
    }

    public PersianEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        handleAttributes(context, attrs, defStyleAttr);
        initialize();
        setMyTypeface();
    }

    private void handleFocusForPersianNumbers() {

        setSelectAllOnFocus(true);
        setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                ((PersianEditText) v).selectAll();
            }
        });
    }

    public void setMyTypeface() {
        Typeface normalTypeface = Typeface.createFromAsset(getContext().getAssets(),
                BuildConfig.FONT);

        this.setTypeface(normalTypeface, Typeface.NORMAL);
    }

    private void initialize() {
        this.addTextChangedListener(textWatcher);

    }

    private void handleAttributes(Context context, AttributeSet attrs, int defStyle) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PersianEditText,
                defStyle, 0);

        numberMode = a.getBoolean(R.styleable.PersianEditText_numberMode, false);

        //do something with str

        a.recycle();
    }
}
