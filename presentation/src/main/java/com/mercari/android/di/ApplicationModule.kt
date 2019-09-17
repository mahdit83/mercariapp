package com.mercari.android.di

import android.content.Context
import com.mercari.android.executors.UIThread
import com.mercari.android.global.MercariApplication
import com.mercari.data.cache.products.ProductCacheImpl
import com.mercari.data.cache.products.ProductsCache
import com.mercari.data.executor.JobExecutor
import com.mercari.data.repository.datasource.products.ProductsDataRepository
import com.mercari.device.ConnectionDetector
import com.mercari.domain.executor.PostExecutionThread
import com.mercari.domain.executor.ThreadExecutor
import com.mercari.domain.repository.products.ProductRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {


    @Provides
    internal fun provideContext(application: MercariApplication): Context {
        return application.applicationContext
    }


    //executors
    @Provides
    @Singleton
    internal fun providesThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor {
        return jobExecutor
    }

    @Provides
    @Singleton
    internal fun providesPostExecutionThread(uiThread: UIThread): PostExecutionThread {
        return uiThread
    }

    //repository
    @Provides
    @Singleton
    internal fun providesProductRepository(productsDataRepository: ProductsDataRepository): ProductRepository {
        return productsDataRepository
    }

    //caches
    @Provides
    @Singleton
    internal fun providesProductCache(productCacheImpl: ProductCacheImpl): ProductsCache {
        return productCacheImpl
    }

    //Device
    @Provides
    @Singleton
    fun providesConnectionDetector(context: Context): ConnectionDetector {
        return ConnectionDetector(context)
    }


}
