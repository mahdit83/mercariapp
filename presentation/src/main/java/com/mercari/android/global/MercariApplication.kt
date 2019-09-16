package com.mercari.android.global

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.mercari.android.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MercariApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    override fun supportFragmentInjector(): AndroidInjector<androidx.fragment.app.Fragment> {
        return fragmentInjector
    }


    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<androidx.fragment.app.Fragment>


    override fun onCreate() {
        super.onCreate()
        initializeAppInjector()
    }

    private fun initializeAppInjector() {
        DaggerApplicationComponent.builder().application(this).build().inject(this)
    }
}