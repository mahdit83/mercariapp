package com.mercari.data.mapper

import com.mercari.data.mapper.base.DataMapper
import com.mercari.domain.model.products.Category
import com.mercari.domain.model.CategoryEntity
import com.mercari.domain.model.products.Product
import com.mercari.domain.model.ProductEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductsEntityMapper @Inject constructor() : DataMapper<List<ProductEntity>, List<Product>> {


    override fun transform(productEntites: List<ProductEntity>?): List<Product> {

        var products : MutableList<Product> = mutableListOf()

        productEntites?.forEach { productEntity: ProductEntity ->
            val product = Product(
                productEntity?.id,
                productEntity?.name,
                productEntity?.status,
                productEntity?.num_likes,
                productEntity?.num_comments,
                productEntity?.price,
                productEntity?.photo
            )

            products.add(product)

        }

        return products
    }

    fun transformCategory(categoryEntities: List<CategoryEntity>?): List<Category> {

        var categories : MutableList<Category> = mutableListOf()

        categoryEntities?.forEach { categoryEntity: CategoryEntity ->

            val category =
                Category(categoryEntity.name, categoryEntity.data)

            categories.add(category)
        }


        return categories
    }
}