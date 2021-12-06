package com.jdagnogo.welovemarathon.tips.data

import androidx.annotation.Keep

@Keep
data class TipsData(
    val dao: TipsDao,
    val remoteData: TipsRemoteData,
    val mapper: TipsMapper,
)
