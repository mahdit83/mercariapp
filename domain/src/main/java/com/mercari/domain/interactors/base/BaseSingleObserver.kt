package com.mercari.domain.interactors.base

import io.reactivex.observers.DisposableSingleObserver

class BaseSingleObserver<T> : DisposableSingleObserver<T>(), IObserver {


    override fun onSuccess(t: T) {

    }

    override fun onError(e: Throwable) {

    }
}
