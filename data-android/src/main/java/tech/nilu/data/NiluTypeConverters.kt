package tech.nilu.data

import androidx.room.TypeConverter
import java.math.BigDecimal
import java.util.*

object NiluTypeConverters {

    @TypeConverter
    @JvmStatic
    fun fromBigDecimal(value: BigDecimal?) = value?.let { value.toString() }

    @TypeConverter
    @JvmStatic
    fun toBigDecimal(value: String?) = value?.let { BigDecimal(it) }

    @TypeConverter
    @JvmStatic
    fun fromDate(value: Date?) = value?.time

    @TypeConverter
    @JvmStatic
    fun toDate(value: Long?) = value?.let { Date(it) }
}
