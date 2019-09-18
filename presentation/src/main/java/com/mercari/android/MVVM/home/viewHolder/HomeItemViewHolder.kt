package com.mercari.android.MVVM.home.viewHolder

import android.view.View
import android.widget.ImageView
import com.mercari.android.MVVM.ui.components.PersianTextView
import com.mercari.android.MVVM.ui.viewHolders.BaseViewHolder
import com.mercari.android.R
import com.mercari.android.model.HomeItemModel

class HomeItemViewHolder(itemView: View) : BaseViewHolder<HomeItemModel>(itemView) {

    init {
        initializeUi(itemView)
    }

    private lateinit var image: ImageView
    private lateinit var text: PersianTextView

    private fun initializeUi(itemView: View) {

        image = itemView.findViewById(R.id.homeItemImage)
        text = itemView.findViewById(R.id.homeItemCaption)
    }


    override fun onBindView(data: HomeItemModel) {

        text.text = data.title
    }
}