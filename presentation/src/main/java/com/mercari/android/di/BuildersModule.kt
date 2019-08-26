package com.mercari.android.di

import com.mercari.android.MVVM.products.ProductsMVVMFragment
import com.mercari.android.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    //Activities

    @ActivityScope
    @ContributesAndroidInjector(modules = [ActivityModule::class])
    internal abstract fun bindMainActivity(): MainActivity


    //fragments
    @ActivityScope
    @ContributesAndroidInjector(modules = [ActivityModule::class])
    internal abstract fun bindProductsMVVMFragment(): ProductsMVVMFragment


}