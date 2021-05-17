package tech.nilu.explorer

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import kotlinx.coroutines.flow.Flow
import tech.nilu.explorer.coroutines.dispatcher
import tech.nilu.explorer.database.DatabaseDriverFactory

class AndroidNetworkSdk(driver: DatabaseDriverFactory) : NetworkSdk(driver) {

    override fun count(): Flow<Long> = repository.count().asFlow().mapToOne(dispatcher)
}
