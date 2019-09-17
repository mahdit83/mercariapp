package com.mercari.android.MVVM.ui.components;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.mercari.android.BuildConfig;


public class PersianTextView extends AppCompatTextView {

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
//            PersianTextView.this.removeTextChangedListener(textWatcher);
//            String text = Utils.toPersianNumber(s.toString());
//            s.clear();
//            s.append(text);
//            PersianTextView.this.addTextChangedListener(textWatcher);
        }
    };

    public PersianTextView(Context context) {
        super(context);
        setMyTypeface();
        initialize();
    }

    public PersianTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMyTypeface();
        initialize();
    }

    public PersianTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setMyTypeface();
        initialize();
    }


    public void setMyTypeface() {
        Typeface normalTypeface = Typeface.createFromAsset(getContext().getAssets(), BuildConfig.FONT);

        this.setTypeface(normalTypeface, Typeface.NORMAL);
    }

    private void initialize() {
        this.addTextChangedListener(textWatcher);
    }


}
