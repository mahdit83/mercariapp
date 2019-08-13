package com.mercari.data.repository.datasource.products

import com.mercari.data.cache.products.ProductsCache
import com.mercari.data.networking.manager.AuthorizationManager
import com.mercari.data.networking.retrofit.ProductRetrofitService
import com.mercari.data.repository.dataSourceImpl.products.ProductOnlineDataSource
import com.mercari.data.repository.dataSourceImpl.products.ProductsCacheDataSource
import com.mercari.data.repository.datasource.base.BaseDataSourceFactory
import com.mercari.domain.repository.base.CacheStrategy
import io.reactivex.Completable
import javax.inject.Inject

class ProductsDataSourceFactory @Inject constructor(
    private val productsCache: ProductsCache,
    private val authorizationManager: AuthorizationManager
) : BaseDataSourceFactory<ProductsDataSource>() {


    override fun create(cacheStrategy: CacheStrategy, vararg params: String): ProductsDataSource {

        var productsDataSource :ProductsDataSource

        if (cacheStrategy == CacheStrategy.CACHE_FIRST) {

            if (productsCache.isCached("")) {
                productsDataSource = createOnlineDataSource()
            } else {
                productsDataSource = ProductsCacheDataSource(productsCache)
            }
        } else {
            productsDataSource = createOnlineDataSource()
        }
        return productsDataSource
    }

    private fun createOnlineDataSource() =
        ProductOnlineDataSource(authorizationManager, ProductRetrofitService::class.java)

    override fun deleteCache(): Completable {
        return productsCache.deleteCache()
    }
}
