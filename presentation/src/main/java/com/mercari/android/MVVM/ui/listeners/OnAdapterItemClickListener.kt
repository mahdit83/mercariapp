package com.mercari.android.MVVM.ui.listeners

interface OnAdapterItemClickListener<T> {

    fun onAdapterItemClick(t: T)

    fun onAdapterItemLongClick(t: T)
}
