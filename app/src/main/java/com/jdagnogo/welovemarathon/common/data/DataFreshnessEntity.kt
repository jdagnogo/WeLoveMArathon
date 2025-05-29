package com.jdagnogo.welovemarathon.common.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "dataFreshness")
data class DataFreshnessEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dataName: String = "",
    var lastUpdate: Long = 0,
)
