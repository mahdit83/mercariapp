package com.mercari.android.MVVM.ui.components

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import com.mercari.android.R
import com.mercari.android.utils.Utils

class PersianEditText : AppCompatEditText {

    private var numberMode = false
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable) {
            var inputText = s.toString()
            if (numberMode) {
                inputText = inputText.replace("[^0-9۰-۹]".toRegex(), "")
                handleFocusForPersianNumbers()
            }
            this@PersianEditText.removeTextChangedListener(this)
            val text = Utils.toPersianNumber(inputText)
            s.clear()
            s.append(text)
            this@PersianEditText.addTextChangedListener(this)
        }
    }

    constructor(context: Context) : super(context) {
        initialize()
        setMyTypeface()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        handleAttributes(context, attrs, 0)
        initialize()
        setMyTypeface()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        handleAttributes(context, attrs, defStyleAttr)
        initialize()
        setMyTypeface()
    }

    private fun handleFocusForPersianNumbers() {

        setSelectAllOnFocus(true)
        setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                (v as PersianEditText).selectAll()
            }
        }
    }

    fun setMyTypeface() {
        typeface = ResourcesCompat.getFont(context, R.font.iransansmobile)
    }

    private fun initialize() {
        this.addTextChangedListener(textWatcher)

    }

    private fun handleAttributes(context: Context, attrs: AttributeSet, defStyle: Int) {

        val a = context.obtainStyledAttributes(
            attrs, R.styleable.PersianEditText,
            defStyle, 0
        )

        numberMode = a.getBoolean(R.styleable.PersianEditText_numberMode, false)

        //do something with str

        a.recycle()
    }
}
