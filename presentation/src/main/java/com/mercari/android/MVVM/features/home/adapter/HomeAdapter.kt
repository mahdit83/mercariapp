package com.mercari.android.MVVM.features.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mercari.android.MVVM.features.home.viewHolder.HomeItemViewHolder
import com.mercari.android.MVVM.ui.adapters.GridAdapter
import com.mercari.android.MVVM.ui.listeners.OnAdapterItemClickListener
import com.mercari.android.MVVM.ui.viewHolders.BaseViewHolder
import com.mercari.android.model.HomeItemModel

class HomeAdapter(
    data: MutableList<HomeItemModel>,
    onAdapterItemClickListener: OnAdapterItemClickListener<HomeItemModel>
) : GridAdapter<HomeItemModel>(data, onAdapterItemClickListener) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<HomeItemModel> {
        if (viewType == HomeItemModel.VIEW_TYPE) {
            viewHolder = HomeItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    HomeItemModel.VIEW_TYPE,
                    parent,
                    false
                ), onAdapterItemClickListener
            )
        }

        return viewHolder

    }
}