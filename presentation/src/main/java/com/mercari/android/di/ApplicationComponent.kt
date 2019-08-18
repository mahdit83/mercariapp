package com.mercari.android.di

import com.mercari.android.global.MercariApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,ApplicationModule::class, BuildersModule::class])
interface ApplicationComponent : AndroidInjector<MercariApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MercariApplication): Builder

        fun build(): ApplicationComponent
    }


}