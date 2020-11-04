package tech.nilu.web3core

class Web3Sdk {

    suspend fun saveContracts(): Result<Boolean> {
        return Result.Success(true)
    }

    sealed class Result<out T> {

        data class Success<T>(
            val value: T
        ) : Result<T>()
    }
}
