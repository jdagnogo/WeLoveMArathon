package com.jdagnogo.welovemarathon.home.presentation

import com.jdagnogo.welovemarathon.common.ui.IReducer

class HomeReducer : IReducer<HomeState, HomePartialState> {
    override fun reduce(state: HomeState, partialState: HomePartialState): HomeState {
        return when (partialState) {
            is HomePartialState.Error -> {
                state.copy()
            }
            HomePartialState.LoadingBlogs -> {
                state.copy(isLoadingBlogs = true)
            }
            HomePartialState.LoadingRuns -> {
                state.copy(isLoadingBlogs = true)
            }
            is HomePartialState.OnBlogsSuccess -> {
                state.copy(
                    isLoadingBlogs = false,
                    blogs = partialState.data
                )
            }
            is HomePartialState.OnRunsSuccess -> {
                state.copy(
                    isLoadingRuns = false,
                    runs = partialState.data
                )
            }
            is HomePartialState.OnBannerSuccess -> {
                state.copy(
                    banner = partialState.banner
                )
            }
        }
    }
}
