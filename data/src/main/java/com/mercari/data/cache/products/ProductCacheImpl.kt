package com.mercari.data.cache.products

import com.mercari.domain.model.CategoryEntity
import com.mercari.domain.model.ProductEntity
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductCacheImpl @Inject constructor(): ProductsCache {

    override fun getCategories(): Observable<List<CategoryEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteCategory(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveData(item: List<ProductEntity>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getData(key: String): Observable<List<ProductEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isCached(key: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteCache(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}