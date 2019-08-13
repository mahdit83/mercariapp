package com.mercari.data.repository.datasource.products

import com.mercari.data.mapper.ProductsEntityMapper
import com.mercari.domain.model.Category
import com.mercari.domain.model.CategoryEntity
import com.mercari.domain.model.Product
import com.mercari.domain.model.ProductEntity
import com.mercari.domain.repository.base.CacheStrategy
import com.mercari.domain.repository.products.ProductRepository
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductsDataRepository @Inject constructor(
    private val productsEntityMapper: ProductsEntityMapper,
    private val productsDataSourceFactory: ProductsDataSourceFactory
) : ProductRepository {


    override fun getCategories(cacheStrategy: CacheStrategy): Observable<List<Category>> {

        var dataSource= productsDataSourceFactory.create(cacheStrategy)

        return dataSource.getCategories().map { categoryEntities: List<CategoryEntity> -> productsEntityMapper.transformCategory(categoryEntities) }
    }

    override fun getProduct(cacheStrategy: CacheStrategy, categoryName: String): Observable<List<Product>> {
        var dataSource= productsDataSourceFactory.create(cacheStrategy)

        return dataSource.getProduct(categoryName).map { productEntities: List<ProductEntity> -> productsEntityMapper.transform(productEntities) }
    }

    override fun deleteCache() {
        productsDataSourceFactory.deleteCache()
    }
}