package com.jdagnogo.welovemarathon.tips.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.jdagnogo.welovemarathon.common.submenu.SubMenuInteractions

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun TipsScreen(
    viewModel: TipsViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    TipsContent(
        state = state,
        subMenuInteractions = SubMenuInteractions(
            onBackPressed = {},
            onItemSelected = {
                viewModel.dispatchEvent(TipsUiEvent.OnTipSelected(it))
            },
            onMapSelected = {},
        ),
        modifier = modifier
    )
}
