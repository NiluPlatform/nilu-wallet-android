package tech.nilu.base.mapper

interface Mapper<in F, out T> {
    suspend fun map(from: F): T
}
