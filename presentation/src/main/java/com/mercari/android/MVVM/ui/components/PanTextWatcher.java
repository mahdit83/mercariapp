package com.mercari.android.MVVM.ui.components;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.LineHeightSpan;
import android.text.style.ReplacementSpan;

public class PanTextWatcher implements TextWatcher {

    private int mStart;
    private int mCount;

    private int mLineHeight = 0;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mStart = start;
        mCount = count;
    }

    @Override
    public void afterTextChanged(Editable s) {


        for (int i = 0; i < mCount; i++) {
            s.setSpan(new PanSpan(), i + mStart, i + mStart + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    private String convertDigits(CharSequence digits) {
//        return Utils.toPersianNumber(digits.toString());
        return digits.toString();
    }

    private String convertDigits(CharSequence digits, int start, int end) {
//        return Utils.toPersianNumber(digits.subSequence(start, end).toString());
        return digits.subSequence(start, end).toString();
    }

    public void setLineHeight(int height) {
        mLineHeight = height;
    }

    private class PanSpan extends ReplacementSpan implements LineHeightSpan {

        @Override
        public int getSize(Paint paint, CharSequence text, int start, int end,
                           Paint.FontMetricsInt fm) {
            return (int) (paint.measureText(convertDigits(text), start, end) + ((start > 0 && start % 4 == 0) ? paint.measureText(" ") : 0));
        }

        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top,
                         int y, int bottom, Paint paint) {
            Paint.FontMetrics fm = paint.getFontMetrics();

            String s = ((start > 0 && start % 4 == 0) ? " " : "") + convertDigits(text, start, end);

            canvas.drawText(s, x, top - fm.top, paint);
        }

        @Override
        public void chooseHeight(CharSequence text, int start, int end, int istartv, int v,
                                 Paint.FontMetricsInt fm) {
            int ht = mLineHeight;

            int need = ht - (v + fm.descent - fm.ascent - istartv);

            if (need > 0) {
                fm.descent += need;
            }

            need = ht - (v + fm.bottom - fm.top - istartv);

            if (need > 0) {
                fm.bottom += need;
            }
        }

    }

}
