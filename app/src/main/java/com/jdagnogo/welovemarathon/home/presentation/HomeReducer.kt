package com.jdagnogo.welovemarathon.home.presentation

import com.jdagnogo.welovemarathon.common.utils.IReducer

class HomeReducer : IReducer<HomeState, HomePartialState> {
    override fun reduce(state: HomeState, partialState: HomePartialState): HomeState {
        return when (partialState) {
            is HomePartialState.Error -> {
                state.copy(hasError = true)
            }
            HomePartialState.LoadingBeaches -> state.copy()
            is HomePartialState.OnBannerSuccess -> state.copy(
                banner = partialState.banner)
            is HomePartialState.OnBeachesSuccess -> {
                state.copy(
                    isLoadingBeaches = false,
                    beaches = partialState.data)
            }

            is HomePartialState.OnOfferSuccess ->{
                state.copy(
                    shouldOpenOfferBottomSheet = partialState.shouldOpenOfferBottomSheet,
                    offer = partialState.offer,
                )
            }
        }
    }
}
