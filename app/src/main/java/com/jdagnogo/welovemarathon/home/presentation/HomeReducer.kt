package com.jdagnogo.welovemarathon.home.presentation

import com.jdagnogo.welovemarathon.common.ui.IReducer

class HomeReducer : IReducer<HomeState, HomePartialState> {
    override fun reduce(state: HomeState, partialState: HomePartialState): HomeState {
        return when (partialState) {
            is HomePartialState.Error -> {
                state.copy()
            }
            HomePartialState.LoadingBeaches -> state.copy()
            is HomePartialState.OnBannerSuccess -> state.copy(banner = partialState.banner)
        }
    }
}
