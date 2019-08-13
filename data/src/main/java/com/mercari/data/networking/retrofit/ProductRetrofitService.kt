package com.mercari.data.networking.retrofit

import com.mercari.domain.model.CategoryEntity
import com.mercari.domain.model.ProductEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

interface ProductRetrofitService {

    @GET("json/master.json")
    fun getCategories(): Observable<List<CategoryEntity>>

    @GET
    fun getProducts(@Url url : String ,categoryName: String): Observable<List<ProductEntity>>
}