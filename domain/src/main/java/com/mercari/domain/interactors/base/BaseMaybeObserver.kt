package com.mercari.domain.interactors.base

import io.reactivex.observers.DisposableMaybeObserver

class BaseMaybeObserver<T> : DisposableMaybeObserver<T>(), IObserver {

    override fun onSuccess(t: T) {

    }

    override fun onError(e: Throwable) {

    }

    override fun onComplete() {

    }
}
