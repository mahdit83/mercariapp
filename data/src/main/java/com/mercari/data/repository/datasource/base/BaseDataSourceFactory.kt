package com.mercari.data.repository.datasource.base

import com.mercari.domain.repository.base.CacheStrategy
import io.reactivex.Completable

abstract class BaseDataSourceFactory<T> {

    abstract fun create(cacheStrategy: CacheStrategy, vararg params: String): T
    abstract fun deleteCache(): Completable

}
