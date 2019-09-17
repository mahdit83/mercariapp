package com.mercari.domain.interactors.products

import com.mercari.domain.executor.PostExecutionThread
import com.mercari.domain.executor.ThreadExecutor
import com.mercari.domain.interactors.base.UseCase
import com.mercari.domain.model.products.Category
import com.mercari.domain.repository.base.CacheStrategy
import com.mercari.domain.repository.products.ProductRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetCategoryUseCase
@Inject constructor(
    private val productRepository: ProductRepository,
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread
) : UseCase<List<Category>, Int>(threadExecutor, postExecutionThread) {


    override fun buildUseCaseObservable(params: Int): Observable<List<Category>> {
        return productRepository.getCategories(CacheStrategy.ONLINE_FIRST)
    }
}