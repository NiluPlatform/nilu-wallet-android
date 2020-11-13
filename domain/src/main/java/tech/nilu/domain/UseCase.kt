package tech.nilu.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import tech.nilu.base.result.Error
import tech.nilu.base.result.Result
import tech.nilu.base.result.Success

abstract class UseCase<in P, R>(private val dispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(params: P): Result<R> = try {
        withContext(dispatcher) {
            val result = execute(params)
            Success(result)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Error(e)
    }

    protected abstract suspend fun execute(params: P): R
}

suspend operator fun <R> UseCase<Unit, R>.invoke() = invoke(Unit)
