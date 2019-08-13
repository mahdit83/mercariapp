package com.mercari.data.repository.dataSourceImpl.products

import com.mercari.data.cache.products.ProductsCache
import com.mercari.data.repository.datasource.products.ProductsDataSource
import com.mercari.domain.model.CategoryEntity
import com.mercari.domain.model.ProductEntity
import io.reactivex.Observable

class ProductsCacheDataSource constructor(private val productsCache: ProductsCache) : ProductsDataSource {


    override fun getCategories(): Observable<List<CategoryEntity>> {
        return productsCache.getCategories()
    }

    override fun getProduct(categoryName: String): Observable<List<ProductEntity>> {
        return productsCache.getData(categoryName)
    }
}