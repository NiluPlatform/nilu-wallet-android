package tech.nilu.data

import tech.nilu.data.dao.NetworkDao

interface NiluDatabase {

    fun networkDao(): NetworkDao
}
