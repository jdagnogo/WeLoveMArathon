package com.jdagnogo.welovemarathon.tips.presentation

import com.jdagnogo.welovemarathon.common.utils.IReducer

class TipsReducer : IReducer<TipsState, TipsPartialState> {
    override fun reduce(state: TipsState, partialState: TipsPartialState): TipsState {
        return when (partialState) {
            is TipsPartialState.Error -> {
                state.copy()
            }
            is TipsPartialState.OnTipsSuccess -> {
                state.copy(tips = partialState.data)
            }
            is TipsPartialState.OnTipSelected -> {
                state.copy(
                    currentSelected = partialState.tips,
                )
            }
            TipsPartialState.OnCloseDialog -> {
                state.copy(
                    currentSelected = null,
                )
            }
        }
    }
}
