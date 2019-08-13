package com.mercari.data.cache.base

import io.reactivex.Completable
import io.reactivex.Observable

interface BaseCache<T> {

    fun saveData(item: T)
    fun getData(key: String): Observable<T>
    fun isCached(key: String): Boolean
    fun deleteCache(): Completable
}