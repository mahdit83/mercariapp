package com.mercari.domain.interactors.products

import com.mercari.domain.executor.PostExecutionThread
import com.mercari.domain.executor.ThreadExecutor
import com.mercari.domain.interactors.base.UseCase
import com.mercari.domain.model.Category
import com.mercari.domain.model.Product
import com.mercari.domain.repository.CacheStrategy
import com.mercari.domain.repository.ProductRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetProductUseCase
@Inject constructor(
    private val productCategory: Category,
    private val productRepository: ProductRepository,
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread
) : UseCase<List<Product>, Int>(threadExecutor, postExecutionThread) {


    override fun buildUseCaseObservable(params: Int): Observable<List<Product>> {
        return productRepository.getProduct(CacheStrategy.ONLINE_FIRST ,productCategory.name)

    }


}