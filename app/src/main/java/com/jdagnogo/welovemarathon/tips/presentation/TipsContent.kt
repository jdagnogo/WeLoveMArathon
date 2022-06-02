package com.jdagnogo.welovemarathon.tips.presentation

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jdagnogo.welovemarathon.common.submenu.SubMenuInteractions
import com.jdagnogo.welovemarathon.common.submenu.SubMenuScreen
import com.jdagnogo.welovemarathon.common.submenu.SubMenuShopping
import com.jdagnogo.welovemarathon.common.submenu.SubMenuUiModel
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent
import com.jdagnogo.welovemarathon.common.ui.theme.TagColor
import com.jdagnogo.welovemarathon.tips.domain.Tips

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun TipsContent(
    state: TipsState,
    subMenuInteractions: SubMenuInteractions,
    modifier: Modifier = Modifier
) {
    SubMenuScreen(
        shouldDisplayTitle = false,
        subMenuUiModel = state.subMenuUiModel,
        modifier = modifier,
        subMenuInteractions = subMenuInteractions,
    )
    if (state.shouldOpenDialog){
        // Display dialog
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@Preview(name = "TipsContent")
@Preview("Dark : TipsContent", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TipsContentPreview() {
    MaterialTheme {
        TipsContent(
            state = TipsState(
                error = "",
                tips = listOf(Tips())
            ),
            subMenuInteractions = SubMenuInteractions(
                onItemSelected = {},
                onBackPressed = {},
                onMapSelected = {}
            )
        )
    }
}
