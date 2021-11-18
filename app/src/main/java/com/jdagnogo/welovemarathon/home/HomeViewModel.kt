package com.jdagnogo.welovemarathon.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val state: StateFlow<HomeViewState> get() = _state

    init {
        fakeData()
    }

    private fun fakeData() {
        viewModelScope.launch {
            //delay(1000)
            _state.value = HomeViewState.OnBlogSuccess(
                Blog().fakeList()
            )
        }
    }
}
