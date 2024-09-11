package com.jdagnogo.welovemarathon.offer.domain

data class OfferUseCase(
    val getOfferUseCase: GetOfferUseCase,
    val getOfferActivatedUseCase: GetOfferActivatedUseCase,
    val activateOfferUseCase: ActivateOfferUseCase,
)