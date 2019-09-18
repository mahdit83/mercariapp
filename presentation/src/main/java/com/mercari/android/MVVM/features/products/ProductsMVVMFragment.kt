package com.mercari.android.MVVM.features.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.mercari.android.R
import com.mercari.domain.model.products.Product
import dagger.android.support.AndroidSupportInjection
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
        productsViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ProductsViewModel::class.java)
        productsViewModel.start()
        subscribe()
    }

    private fun subscribe() {

//        productsViewModel.allProducts.observe(this, androidx.lifecycle.Observer { t -> renderData(t) })

    }

    private fun renderData(t: List<Product>?) {
        t?.forEach { product -> Logger.getLogger("salam").warning(product.name) }
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
