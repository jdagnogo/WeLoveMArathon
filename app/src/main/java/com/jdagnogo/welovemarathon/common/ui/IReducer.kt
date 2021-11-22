package com.jdagnogo.welovemarathon.common.ui

interface IReducer<STATE, PARTIAL_STATE> {
    fun reduce(state: STATE, partialState: PARTIAL_STATE): STATE
}
