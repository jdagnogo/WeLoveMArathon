package com.jdagnogo.welovemarathon.common.utils

interface IReducer<STATE, PARTIAL_STATE> {
    fun reduce(state: STATE, partialState: PARTIAL_STATE): STATE
}
