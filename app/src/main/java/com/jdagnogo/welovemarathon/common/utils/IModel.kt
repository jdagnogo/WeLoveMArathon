package com.jdagnogo.welovemarathon.common.utils

import kotlinx.coroutines.flow.StateFlow

interface IModel<STATE, EVENT> {

    val state: StateFlow<STATE>

    fun dispatchEvent(event: EVENT)
}
