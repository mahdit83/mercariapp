package com.mercari.domain.repository

import com.mercari.domain.model.Category
import com.mercari.domain.model.Product
import io.reactivex.Observable

interface ProductRepository : BaseRepository {

    fun getCategories(cacheStrategy: CacheStrategy): Observable<List<Category>>

    fun getProduct(cacheStrategy: CacheStrategy ,categoryName: String): Observable<List<Product>>
}