package com.mercari.android.MVVM.ui.components.input;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;

import com.mercari.android.MVVM.ui.components.PanTextWatcher;
import com.mercari.android.R;
import com.mercari.android.utils.Utils;

import java.util.ArrayList;

public class PanInput extends AppCompatEditText {

    public static final int PAN_COMPLETE_LENGTH = 16;
    private final String EMPTY_TEXT = "________________";
    private TextChangeListener mListener;
    private boolean mShowUnderlines = false;
    private boolean needsFullWidth = false;
    private int desiredFontWidth = 0;
    private float fontTextRatio = 1;
    private AppCompatEditText mShadowText;
    private PanTextWatcher[] mPanWatchers = new PanTextWatcher[2];

    public PanInput(Context context) {
        super(context);
        initialize(context, null, 0);
    }

    public PanInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, 0);
    }

    public PanInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);

        if (mShadowText != null) {
            mShadowText.setLayoutParams(params);
        }
    }

    @Override
    public void setTextSize(float size) {
        super.setTextSize(size);

        if (mPanWatchers != null) {
            Paint.FontMetrics fm = getPaint().getFontMetrics();

            mPanWatchers[0].setLineHeight((int) (fm.bottom - fm.top));
            mPanWatchers[1].setLineHeight((int) (fm.bottom - fm.top));
        }

        if (mShadowText != null) {
            mShadowText.setTextSize(size);
        }
    }

    @Override
    public void setTextSize(int unit, float size) {
        super.setTextSize(unit, size);

        if (mShadowText != null) {
            mShadowText.setTextSize(unit, size);
        }
    }

    @Override
    public void setTypeface(Typeface tf) {
        super.setTypeface(tf);

        if (mShadowText != null) {
            mShadowText.setTypeface(tf);
        }
    }

    @Override
    public void setTextColor(@ColorInt int color) {
        super.setTextColor(color);

        if (mShadowText != null) {
            mShadowText.setTextColor(color);
        }
    }

    @Override
    public void setTextColor(ColorStateList colors) {
        super.setTextColor(colors);

        if (mShadowText != null) {
            mShadowText.setTextColor(colors);
        }
    }

    @Override
    public void setInputType(int type) {
        super.setInputType(type);

        mShadowText.setInputType(type);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mShowUnderlines) {
            mShadowText.draw(canvas);
        }
        super.onDraw(canvas);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mShadowText.measure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mShadowText.layout(0, 0, getWidth(), getHeight());
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);

        if (text.length() > PAN_COMPLETE_LENGTH) {
            setText(text.subSequence(0, PAN_COMPLETE_LENGTH));
            setSelection(PAN_COMPLETE_LENGTH);
        } else {

            if (mShadowText != null && mShowUnderlines) {
                mShadowText.setText(text + getUnderlines(PAN_COMPLETE_LENGTH - text.length()));
            }
            if (mListener != null) {
                mListener.onTextChanged(text.toString());
            }
        }
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);

        if (mPanWatchers != null) {
            Paint.FontMetrics fm = getPaint().getFontMetrics();

            mPanWatchers[0].setLineHeight((int) (fm.bottom - fm.top));
            mPanWatchers[1].setLineHeight((int) (fm.bottom - fm.top));
        }
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        if (id == android.R.id.paste) {
            ClipboardManager clipboard =
                    (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = clipboard.getPrimaryClip();

            if (clip == null) {
                return true;
            }

            String text = clip.toString().replaceAll("[\\s]", "").replaceAll("-", "").replaceAll(
                    "/", "");
            text = Utils.toEnglishNumber(text);

            ArrayList<String> numbers = Utils.getNumbers(text);

            String pan = "";
            for (String number : numbers) {
                if (number.length() > pan.length())
                    pan = number;
                if (pan.length() >= PAN_COMPLETE_LENGTH) break;
            }

            if (pan.length() < PAN_COMPLETE_LENGTH) {
                pan = getText() + pan;
            }

            if (pan.length() > PAN_COMPLETE_LENGTH) {
                pan = pan.substring(0, PAN_COMPLETE_LENGTH);
            }

            setText(pan);

            return true;
        }

        return super.onTextContextMenuItem(id);
    }

    public void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        setTypeface(ResourcesCompat.getFont(context, R.font.pt_mono));

        mPanWatchers[0] = new PanTextWatcher();
        mPanWatchers[1] = new PanTextWatcher();

        addTextChangedListener(mPanWatchers[0]);

        mShadowText = new AppCompatEditText(context, attrs, defStyleAttr);
        mShadowText.setTypeface(getTypeface());
        mShadowText.setInputType(getInputType());
        mShadowText.setEnabled(false);
        mShadowText.setTextColor(getResources().getColor(R.color.primaryTextColor));
        mShadowText.addTextChangedListener(mPanWatchers[1]);
        mShadowText.setText(EMPTY_TEXT);
        if (getLayoutParams() != null) {
            mShadowText.setLayoutParams(getLayoutParams());
        }
        mShadowText.setTextSize(getTextSize());
        mShadowText.setTextColor(getTextColors());

        Paint.FontMetrics fm = getPaint().getFontMetrics();
        mPanWatchers[0].setLineHeight((int) (fm.bottom - fm.top));
        mPanWatchers[1].setLineHeight((int) (fm.bottom - fm.top));

        if (needsFullWidth) {
            handleFullWidth();
        }
    }


    private void handleFullWidth() {

        //Get width after view created
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                ViewGroup.MarginLayoutParams params =
                        (ViewGroup.MarginLayoutParams) getLayoutParams();


                desiredFontWidth =
                        (int) ((getWidth() - params.leftMargin - params.rightMargin) * fontTextRatio);

                setTextSize(TypedValue.COMPLEX_UNIT_PX, getFullWidthTextSize(PanInput.this,
                        desiredFontWidth));
                if (mShowUnderlines) {
                    mShadowText.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            getFullWidthTextSize(PanInput.this,
                                    desiredFontWidth));
                }
            }
        });
    }

    private float getFullWidthTextSize(TextView textView, int desiredWidth) {
        Paint paint = new Paint();
        Rect bounds = new Rect();

        paint.setTypeface(textView.getTypeface());
        float textSize = textView.getTextSize();
        paint.setTextSize(textSize);
        String text = EMPTY_TEXT;
        paint.getTextBounds(text, 0, text.length(), bounds);
        while (bounds.width() < desiredWidth) {
            textSize++;
            paint.setTextSize(textSize);
            paint.getTextBounds(text, 0, text.length(), bounds);
        }
        return textSize;
    }

    private String getUnderlines(int length) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append('_');
        }

        return sb.toString();
    }

    public String getPan() {
        return getText().toString();
    }

    public void setNeedsFullWidth(boolean needsFullWidth) {
        this.needsFullWidth = needsFullWidth;
        handleFullWidth();
    }


    /*
    Ratio of Text to screen width
     */
    public void setFontTextRatio(float fontTextRatio) {
        this.needsFullWidth = true;
        if (fontTextRatio < 1) {
            this.fontTextRatio = fontTextRatio;
            handleFullWidth();
        }
    }

    public void setShowUnderlines(boolean showUnderlines) {
        mShowUnderlines = showUnderlines;

        setText(getText());
    }

    public void setListener(TextChangeListener listener) {
        this.mListener = listener;
    }

    public interface TextChangeListener {

        void onTextChanged(String newText);

    }

}