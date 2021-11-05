package com.jdagnogo.welovemarathon.home

sealed class HomeViewState {
    object Loading : HomeViewState()
    data class Error(val message: String) : HomeViewState()
    data class OnBlogSuccess(val data: List<Blog>) : HomeViewState()
}
