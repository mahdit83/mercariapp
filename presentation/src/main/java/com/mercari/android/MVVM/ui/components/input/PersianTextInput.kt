package com.mercari.android.MVVM.ui.components.input

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.mercari.android.MVVM.ui.components.PersianEditText
import com.mercari.android.MVVM.ui.components.PersianTextView

import com.mercari.android.R

@SuppressLint("ViewConstructor")
class PersianTextInput : LinearLayout {

    internal lateinit var text: String
    internal lateinit var errorMessageText: String
    internal lateinit var hintText: String

    lateinit var editTextView: PersianEditText
    lateinit var errorTextView: PersianTextView
    lateinit var hintTextView: PersianTextView

    constructor(context: Context) : super(context)


    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        attrs?.let { initializeUi(it, -1) }
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        attrs?.let { initializeUi(it, defStyleAttr) }
    }


    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        initializeUi(attrs, defStyleAttr)
    }


    private fun initializeUi(attrs: AttributeSet, defStyle: Int) {

        val view = View.inflate(context, R.layout.custom_persian_input_layout, null)
        editTextView = view.findViewById(R.id.editText)
        errorTextView = view.findViewById(R.id.errorMessage)
        hintTextView = view.findViewById(R.id.hintText)

        updateVariableFromXml(attrs)

    }

    private fun updateVariableFromXml(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.PersianTextInput)

        var hint = a.getString(R.styleable.PersianTextInput_android_hint)

        if(!TextUtils.isEmpty(hint)){
            hintTextView.visibility = View.VISIBLE
            hintTextView.text = hint
        }else{
            hintTextView.visibility = View.GONE
        }


    }

    fun setText(text: String) {
        this.text = text
    }

    fun setErrorMessageText(errorMessageText: String) {
        this.errorMessageText = errorMessageText
    }

    fun setHintText(hintText: String) {
        this.hintText = hintText
    }
}
