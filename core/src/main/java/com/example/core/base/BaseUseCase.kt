package com.example.core.base

interface BaseUseCase {
    operator fun invoke()
}

interface BaseIOUseCase<IN, OUT> {
    operator fun invoke(input: IN): OUT
}

interface BaseIUseCase<IN> {
    operator fun invoke(input: IN)
}

interface BaseOUseCase<OUT> {
    operator fun invoke(): OUT
}

interface BaseSuspendIOUseCase<IN, OUT> {
    suspend operator fun invoke(input: IN): OUT
}

interface BaseSuspendIUseCase<IN> {
    suspend operator fun invoke(input: IN)
}

interface BaseSuspendOUseCase<OUT> {
    suspend operator fun invoke(): OUT
}

interface BaseSuspendUseCase {
    suspend operator fun invoke()
}