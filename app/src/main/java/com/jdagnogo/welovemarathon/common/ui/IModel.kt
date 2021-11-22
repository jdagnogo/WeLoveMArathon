package com.jdagnogo.welovemarathon.common.ui

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.StateFlow

interface IModel<STATE, EVENT> {

    val state: StateFlow<STATE>

    fun dispatchEvent(event: EVENT)
}
