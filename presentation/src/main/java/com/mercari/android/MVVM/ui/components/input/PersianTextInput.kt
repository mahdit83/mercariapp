package com.mercari.android.MVVM.ui.components.input

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.textfield.TextInputLayout
import com.mercari.android.MVVM.ui.components.PersianEditText
import com.mercari.android.R

class PersianTextInput : FrameLayout {

    constructor(context: Context) : this(context, null){
        initializeUi(null,0)
    }
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0){
        initializeUi(attrs ,0)

    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initializeUi(attrs ,defStyleAttr)

    }


    internal lateinit var text: String
    lateinit var editTextView: PersianEditText
    lateinit var textInputLayout: TextInputLayout


    private fun initializeUi(attrs: AttributeSet?, defStyle: Int) {

        val view = View.inflate(context, R.layout.custom_persian_input_layout, null)
        editTextView = view.findViewById(R.id.editText)
        textInputLayout = view.findViewById(R.id.textInputLayout)


        attrs?.let { updateVariableFromXml(it) }

    }

    private fun updateVariableFromXml(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.PersianTextInput)

        var hint = a.getString(R.styleable.PersianTextInput_android_hint)


    }

    fun setText(text: String) {
        this.editTextView.setText(text)
    }

    fun setErrorMessageText(errorMessageText: String) {
        textInputLayout.error = errorMessageText
    }


    fun setImage(drawable: Drawable?) {
        editTextView.setCompoundDrawables(null, null, drawable, null)

    }
}
