package com.mercari.android.model

data class ProductModel constructor(
    val id: String,
    val name: String,
    val status: String,
    val num_likes: Int,
    val num_comments: Int,
    val price: Int,
    val photo: String) : BaseModel() {


    companion object {
        const val VIEW_TYPE = 9
    }

    override fun getViewType(): Int {
        return VIEW_TYPE
    }


}