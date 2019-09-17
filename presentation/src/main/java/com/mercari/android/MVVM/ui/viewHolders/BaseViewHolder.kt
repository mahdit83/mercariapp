package com.mercari.android.MVVM.ui.viewHolders

import android.view.View

import androidx.recyclerview.widget.RecyclerView

import com.mercari.android.MVVM.ui.listeners.OnAdapterItemClickListener


abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {


    protected lateinit var onItemClickListener: OnAdapterItemClickListener<T>

    abstract fun onBindView(data: T)

}