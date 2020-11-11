package tech.nilu.thread

import kotlinx.coroutines.CoroutineDispatcher

data class Dispatcher(
    val io: CoroutineDispatcher,
    val computation: CoroutineDispatcher,
    val main: CoroutineDispatcher
)
