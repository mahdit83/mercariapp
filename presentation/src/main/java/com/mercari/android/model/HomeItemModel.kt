package com.mercari.android.model

import com.mercari.android.R

data class HomeItemModel constructor(val title: String, val imageIcon: String, val actionUri: String) :
    BaseModel() {


    companion object {

        const val VIEW_TYPE = R.layout.home_item_layout
    }

    override fun getViewType(): Int {
        return VIEW_TYPE
    }
}