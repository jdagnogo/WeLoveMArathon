package com.jdagnogo.welovemarathon.beach.data

import androidx.annotation.Keep

@Keep
data class BeachData(
    val beachDao: BeachDao,
    val beachRemoteData: BeachRemoteData,
    val beachMapper: BeachMapper,
)
