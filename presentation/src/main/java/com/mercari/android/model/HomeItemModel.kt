package com.mercari.android.model

data class HomeItemModel constructor(val title: String, val imageIcon: String, val actionUri: String) :
    BaseModel() {


    companion object {

        const val VIEW_TYPE = 9
    }

    override fun getViewType(): Int {
        return VIEW_TYPE
    }
}