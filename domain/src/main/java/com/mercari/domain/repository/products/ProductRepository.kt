package com.mercari.domain.repository.products

import com.mercari.domain.model.products.Category
import com.mercari.domain.model.products.Product
import com.mercari.domain.repository.base.BaseRepository
import com.mercari.domain.repository.base.CacheStrategy
import io.reactivex.Observable

interface ProductRepository : BaseRepository {

    fun getCategories(cacheStrategy: CacheStrategy): Observable<List<Category>>

    fun getProduct(cacheStrategy: CacheStrategy, categoryName: String): Observable<List<Product>>
}