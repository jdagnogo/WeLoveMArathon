package com.jdagnogo.welovemarathon.about.domain

import androidx.annotation.Keep

@Keep
data class AboutUseCases(
    val getDataUseCase: GetAboutUseCase,
)