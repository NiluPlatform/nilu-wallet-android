package tech.nilu.data

interface DatabaseTransactionRunner {
    suspend operator fun <T> invoke(block: suspend () -> T): T
}
