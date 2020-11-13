package tech.nilu.base.extension

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tech.nilu.base.result.Success

fun <T> Flow<T>.mapSuccess() = map(::Success)
