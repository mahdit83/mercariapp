package com.mercari.data.repository.datasource.products

import com.mercari.domain.model.CategoryEntity
import com.mercari.domain.model.ProductEntity
import io.reactivex.Observable

interface ProductsDataSource {

    fun getCategories(): Observable<List<CategoryEntity>>

    fun getProduct(categoryName: String): Observable<List<ProductEntity>>
}