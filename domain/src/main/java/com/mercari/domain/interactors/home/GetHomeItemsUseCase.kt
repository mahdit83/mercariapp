package com.mercari.domain.interactors.home

import com.mercari.domain.executor.PostExecutionThread
import com.mercari.domain.executor.ThreadExecutor
import com.mercari.domain.interactors.base.SingleUseCase
import com.mercari.domain.model.home.HomeItem
import com.mercari.domain.repository.base.CacheStrategy
import com.mercari.domain.repository.home.HomeRepository
import io.reactivex.Single
import javax.inject.Inject

class GetHomeItemsUseCase
@Inject constructor(
    private val homeRepository: HomeRepository,
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread
) :
    SingleUseCase<List<HomeItem>, Int>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseSingle(params: Int): Single<List<HomeItem>> {
        return homeRepository.getHomeItems(CacheStrategy.CACHE_FIRST)
    }
}