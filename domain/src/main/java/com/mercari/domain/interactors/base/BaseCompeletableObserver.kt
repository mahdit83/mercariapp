package com.mercari.domain.interactors.base

import io.reactivex.observers.DisposableCompletableObserver

class BaseCompeletableObserver : DisposableCompletableObserver(), IObserver {

    override fun onComplete() {

    }

    override fun onError(e: Throwable) {

    }
}
