package com.mercari.android.MVVM.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mercari.android.MVVM.products.ProductsViewModel
import com.mercari.android.MVVM.ui.adapters.GridAdapter
import com.mercari.android.MVVM.ui.listeners.OnAdapterItemClickListener
import com.mercari.android.R
import com.mercari.android.model.HomeItemModel
import kotlinx.android.synthetic.main.home_fragment.*
import javax.inject.Inject

class HomeFragment : Fragment() {

    lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAdapter()
    }


    private fun setUpAdapter() {

        val fakeItems = mutableListOf<HomeItemModel>()

        val item = HomeItemModel("test","http://url","actionUrl")

        for ( x in 0..10){
            fakeItems.add(item)
        }



        val adapter = GridAdapter(fakeItems, object :
            OnAdapterItemClickListener<HomeItemModel> {
            override fun onAdapterItemClick(t: HomeItemModel) {

            }

            override fun onAdapterItemLongClick(t: HomeItemModel) {

            }
        })

        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(activity, layoutManager.orientation))
        recyclerView.adapter = adapter


    }


}
