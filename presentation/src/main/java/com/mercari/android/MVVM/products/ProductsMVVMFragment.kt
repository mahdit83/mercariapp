package com.mercari.android.MVVM.products

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mercari.android.R
import com.mercari.domain.model.Product
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import java.util.logging.Logger
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match


class ProductsMVVMFragment : Fragment() {


    lateinit var productsViewModel: ProductsViewModel

    var rootView: View? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var products: MutableLiveData<List<Product>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        productsViewModel = ViewModelProviders.of(this,viewModelFactory).get(ProductsViewModel::class.java)
        productsViewModel.start()
        subscribe()
    }

    private fun subscribe() {

        productsViewModel.allProducts.observe(this, android.arch.lifecycle.Observer { t -> renderData(t) })

    }

    private fun renderData(t: List<Product>?) {
        t?.forEach { product-> Logger.getLogger("salam").warning(product.name) }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_base_mvvm, container, false)
        initializeUi(rootView)
        return rootView
    }

    private fun initializeUi(rootView: View?) {


    }

}
