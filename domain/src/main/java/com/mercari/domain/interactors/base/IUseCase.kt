package com.mercari.domain.interactors.base

interface IUseCase<T, Params> {
    fun execute(observer: T, params: Params)
}
