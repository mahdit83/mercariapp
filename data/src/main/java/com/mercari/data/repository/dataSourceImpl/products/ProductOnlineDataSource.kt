package com.mercari.data.repository.dataSourceImpl.products

import com.mercari.data.networking.base.AbstractService
import com.mercari.data.networking.manager.AuthorizationManager
import com.mercari.data.networking.retrofit.ProductRetrofitService
import com.mercari.data.repository.datasource.products.ProductsDataSource
import com.mercari.domain.model.CategoryEntity
import com.mercari.domain.model.ProductEntity
import io.reactivex.Observable

class ProductOnlineDataSource(
    authorizationManager: AuthorizationManager,
    serviceType: Class<ProductRetrofitService>
) : AbstractService<ProductRetrofitService>(authorizationManager, serviceType), ProductsDataSource {
    override fun getCategories(): Observable<List<CategoryEntity>> {
        return service.getCategories()
    }

    override fun getProduct(categoryName: String): Observable<List<ProductEntity>> {
        return service.getCategories().flatMapIterable { category: List<CategoryEntity> -> category }
            .flatMap { t: CategoryEntity -> getProductFormServer(t, categoryName) }


    }

    private fun getProductFormServer(t: CategoryEntity, categoryName: String): Observable<List<ProductEntity>>? {

        return if (t.name == categoryName) {
            service.getProducts(t.data, t.name)

        } else {
            null
        }

    }
}