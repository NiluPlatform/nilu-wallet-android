package tech.nilu.explorer.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal actual val dispatcher: CoroutineDispatcher = Dispatchers.IO
