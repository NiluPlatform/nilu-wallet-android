package tech.nilu.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import tech.nilu.base.result.Error
import tech.nilu.base.result.Result

abstract class FlowUseCase<in P, R>(private val dispatcher: CoroutineDispatcher) {

    operator fun invoke(params: P): Flow<Result<R>> = execute(params)
        .catch { e -> emit(Error(e)) }
        .flowOn(dispatcher)

    protected abstract fun execute(params: P): Flow<Result<R>>
}

operator fun <R> FlowUseCase<Unit, R>.invoke() = invoke(Unit)
