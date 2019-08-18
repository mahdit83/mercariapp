package com.mercari.data.cache.products

import com.mercari.data.cache.base.BaseCache
import com.mercari.domain.model.CategoryEntity
import com.mercari.domain.model.ProductEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface ProductsCache : BaseCache<List<ProductEntity>> {

    fun getCategories(): Observable<List<CategoryEntity>>

    fun deleteCategory(): Completable

}
