package tech.nilu.data.entity

import androidx.room.*
import java.math.BigInteger

@Entity(
    indices = [Index("walletId")],
    foreignKeys = [ForeignKey(entity = Wallet::class, parentColumns = ["id"], childColumns = ["walletId"])]
)
data class ContractInfo(
    @PrimaryKey(autoGenerate = true) override val id: Long,
    val walletId: Long,
    val address: String,
    val name: String,
    val image: String,
    val types: String,
    var symbol: String? = null
) : BaseEntity {

    @Ignore
    var balance: BigInteger = BigInteger.ZERO

    @Ignore
    var tokenName: String? = null

    @Ignore
    var tokenSymbol: String? = null

    @Ignore
    var tokenTotalSupply: BigInteger? = null

    @Ignore
    var tokenDecimals: BigInteger? = null

    @Ignore
    var tokenRate: BigInteger? = null

    @Ignore
    var tokenIsPayable: Boolean? = null

    override fun toString(): String = name

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as ContractInfo
        if (other.address != address) return false

        return true
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
