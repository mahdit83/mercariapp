package com.mercari.android.global

import com.mercari.android.di.ApplicationComponent
import com.mercari.android.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class MercariApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = appComponent

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        appComponent.inject(this)
    }
}