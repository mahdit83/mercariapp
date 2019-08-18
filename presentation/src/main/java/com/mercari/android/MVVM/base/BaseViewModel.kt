package com.mercari.android.MVVM.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.mercari.domain.interactors.base.BaseObserver
import com.mercari.domain.interactors.products.GetProductUseCase
import com.mercari.domain.model.Product

class BaseViewModel : ViewModel() {


    private lateinit var getProductUseCase: GetProductUseCase
    private val observer: ProductObserver = ProductObserver()
    var allProducts: MutableLiveData<List<Product>> = MutableLiveData()


    fun start() {
//        getProductUseCase.execute(observer, 0)

    }



    override fun onCleared() {
        getProductUseCase.dispose()
        super.onCleared()
    }

    inner class ProductObserver : BaseObserver<List<Product>>() {

        override fun onNext(products: List<Product>) {
            allProducts.postValue(products)
        }

        override fun onComplete() {
            // no-op by default.
        }

        override fun onError(exception: Throwable) {
            // no-op by default.
        }
    }


}