package com.mercari.domain.repository.home

import com.mercari.domain.model.home.HomeItem
import com.mercari.domain.repository.base.BaseRepository
import com.mercari.domain.repository.base.CacheStrategy
import io.reactivex.Single

interface HomeRepository : BaseRepository {

    fun getHomeItems(cacheStrategy: CacheStrategy) : Single<List<HomeItem>>
}