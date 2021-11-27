package com.jdagnogo.welovemarathon.run.data

import androidx.annotation.Keep

@Keep
data class RunData(
    val runDao: RunDao,
    val runRemoteData: RunRemoteData,
    val runMapper: RunMapper,
)
