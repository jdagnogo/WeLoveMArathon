package com.jdagnogo.welovemarathon.run.presentation

import com.jdagnogo.welovemarathon.common.ui.IReducer

class RunReducer : IReducer<RunState, RunPartialState> {
    override fun reduce(state: RunState, partialState: RunPartialState): RunState {
        return when (partialState) {
            is RunPartialState.Error -> {
                state.copy()
            }
            is RunPartialState.OnBlogsSuccess -> {
                state.copy(
                    blogs = partialState.data
                )
            }
            is RunPartialState.OnRunsSuccess -> {
                state.copy(
                    runs = partialState.data
                )
            }
            RunPartialState.Loading -> state.copy()
        }
    }
}
