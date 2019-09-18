package com.mercari.android.MVVM.features.home.viewHolder

import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.mercari.android.MVVM.ui.components.PersianTextView
import com.mercari.android.MVVM.ui.listeners.OnAdapterItemClickListener
import com.mercari.android.MVVM.ui.viewHolders.BaseViewHolder
import com.mercari.android.R
import com.mercari.android.model.HomeItemModel

class HomeItemViewHolder(itemView: View ,val onAdapterItemClickListener: OnAdapterItemClickListener<HomeItemModel>) : BaseViewHolder<HomeItemModel>(itemView) {


    lateinit var homeItemModel : HomeItemModel

    init {
        initializeUi(itemView)
    }

    private lateinit var image: ImageView
    private lateinit var text: PersianTextView
    private lateinit var cardView : CardView

    private fun initializeUi(itemView: View) {

        image = itemView.findViewById(R.id.homeItemImage)
        text = itemView.findViewById(R.id.homeItemCaption)
        cardView = itemView.findViewById(R.id.homeItemContainer)

        cardView.setOnClickListener {
            onAdapterItemClickListener.onAdapterItemClick(homeItemModel)

        }
    }


    override fun onBindView(data: HomeItemModel) {
        this.homeItemModel = data

        text.text = data.title
    }
}