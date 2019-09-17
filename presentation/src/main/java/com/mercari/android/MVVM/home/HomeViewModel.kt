package com.mercari.android.MVVM.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mercari.domain.interactors.base.BaseSingleObserver
import com.mercari.domain.interactors.home.GetHomeItemsUseCase
import com.mercari.domain.model.home.HomeItem
import java.util.logging.Logger
import javax.inject.Inject

class HomeViewModel @Inject constructor() :
    ViewModel() {

    private val logger = Logger.getLogger("Mahdi")

    var homeDataItems : MutableLiveData<List<HomeItem>> = MutableLiveData()
    private val observer: HomeItemObserver = HomeItemObserver()

    fun start(){

    }

    inner class HomeItemObserver : BaseSingleObserver<List<HomeItem>>(){

        override fun onSuccess(t: List<HomeItem>) {

            logger.warning("on Success")
            homeDataItems.postValue(t)
        }

        override fun onError(e: Throwable) {

            logger.warning("on error")
        }
    }

}
