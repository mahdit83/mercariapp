package com.mercari.android.MVVM.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mercari.android.R
import com.mercari.domain.model.Product
import java.util.logging.Logger

// TODO: Rename parameter arguments, choose names that match


class BaseMVVMFragment : Fragment() {


    lateinit var baseViewModel: BaseViewModel

    var rootView: View? = null

    lateinit var products: MutableLiveData<List<Product>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        baseViewModel = ViewModelProviders.of(this).get(BaseViewModel::class.java)
        baseViewModel.start()
        subscribe()
    }

    private fun subscribe() {

        baseViewModel.allProducts.observe(this, android.arch.lifecycle.Observer { t -> renderData(t) })

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
