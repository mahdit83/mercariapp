package com.mercari.android.MVVM.features.products

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mercari.domain.interactors.base.BaseObserver
import com.mercari.domain.interactors.products.GetProductUseCase
import com.mercari.domain.model.products.Product
import java.util.logging.Logger
import javax.inject.Inject

class ProductsViewModel @Inject constructor(private val getProductUseCase: GetProductUseCase) : ViewModel() {


    private val logger = Logger.getLogger("Mahdi")

    private val observer: ProductObserver = ProductObserver()
    var allProducts: MutableLiveData<List<Product>> = MutableLiveData()


    fun start() {
        //All , Men , Women
        getProductUseCase.execute(observer, "All")

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
            logger.warning("onComplete")

        }

        override fun onError(exception: Throwable) {
            // no-op by default.
            logger.warning("onError")

        }
    }


}