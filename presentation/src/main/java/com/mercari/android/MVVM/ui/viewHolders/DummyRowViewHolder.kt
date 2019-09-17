package com.mercari.android.MVVM.ui.viewHolders

import android.util.Log
import android.view.View

import com.mercari.android.model.BaseModel

class DummyRowViewHolder(itemView: View) : BaseViewHolder<BaseModel>(itemView) {

    override fun onBindView(data: BaseModel) {
        Log.d("TAG", "onBindView: ")
    }

}
