package com.jdagnogo.welovemarathon.shopping.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.submenu.SubMenuScreen
import com.jdagnogo.welovemarathon.common.submenu.SubMenuShopping
import com.jdagnogo.welovemarathon.common.submenu.SubMenuUiModel
import com.jdagnogo.welovemarathon.common.ui.theme.ShoppingColor
import com.jdagnogo.welovemarathon.shopping.domain.Shopping
import com.jdagnogo.welovemarathon.shopping.domain.ShoppingCategories
import com.jdagnogo.welovemarathon.shopping.domain.fakeList

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun ShoppingContent(
    state: ShoppingState,
    onCategoryClicked: (ShoppingCategories) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val subMenuUiModel = SubMenuUiModel(
        screenName = "Shopping",
        items = SubMenuShopping.values().toList().map { it.subMenuItem },
        image = R.drawable.beach,
        banner = null,
        background = ShoppingColor
    )
    SubMenuScreen(
        subMenuUiModel = subMenuUiModel,
        modifier = Modifier,
        onItemSelected = {},
        onMapSelected = {}
    )
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Preview(name = "Full content")
@Composable
fun ShoppingContentPreview() {
    val reducer = ShoppingReducer()
    val state =
        reducer.reduce(
            ShoppingState(),
            ShoppingPartialState.Error("")
        )
    val finalState =
        reducer.reduce(
            state, ShoppingPartialState.OnShoppingsSuccess(
                Shopping().fakeList(),
                Shopping("recomended"),
                ShoppingCategories.Pet
            )
        )
    MaterialTheme {
        ShoppingContent(state = finalState, modifier = Modifier)
    }
}
