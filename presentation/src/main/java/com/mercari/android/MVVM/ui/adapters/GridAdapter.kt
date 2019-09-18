package com.mercari.android.MVVM.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mercari.android.MVVM.ui.listeners.OnAdapterItemClickListener
import com.mercari.android.MVVM.ui.viewHolders.BaseViewHolder
import com.mercari.android.model.BaseModel
import java.util.*

abstract class GridAdapter <T> constructor(
    val data: MutableList<T>,
    val onAdapterItemClickListener: OnAdapterItemClickListener<T>
): RecyclerView.Adapter<BaseViewHolder<T>>() where T: BaseModel  {


    lateinit var viewHolder: BaseViewHolder<T>


//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
//        return viewHolder
//    }

    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) =
        viewHolder.onBindView(data[position] as T)


    override fun getItemViewType(position: Int): Int = data[position].getViewType()


    fun clearData() {
        data!!.clear()
        notifyDataSetChanged()
    }

    fun addAll(newItems: ArrayList<T>) {
        data!!.addAll(newItems)
        notifyDataSetChanged()
    }
}