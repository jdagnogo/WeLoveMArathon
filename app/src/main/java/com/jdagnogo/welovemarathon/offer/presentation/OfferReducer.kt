package com.jdagnogo.welovemarathon.offer.presentation

import com.jdagnogo.welovemarathon.common.utils.IReducer

class OfferReducer : IReducer<OfferViewModel.OfferState, OfferViewModel.OfferPartialState> {
    override fun reduce(
        state: OfferViewModel.OfferState,
        partialState: OfferViewModel.OfferPartialState
    ): OfferViewModel.OfferState {
        return when (partialState) {
            is OfferViewModel.OfferPartialState.Empty -> {
                state.copy()
            }

            is OfferViewModel.OfferPartialState.OnOfferSuccess -> {
                state.copy(
                    offer = partialState.offer,
                    activationDate = partialState.activationDate,
                )
            }
        }
    }
}