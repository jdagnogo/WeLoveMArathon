package com.jdagnogo.welovemarathon.shopping.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme
import com.jdagnogo.welovemarathon.shopping.domain.Shopping
import com.jdagnogo.welovemarathon.shopping.domain.ShoppingCategories
import com.jdagnogo.welovemarathon.shopping.domain.fakeList

@Composable
fun ShoppingContent(
    state: ShoppingState,
    onCategoryClicked: (ShoppingCategories) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier
        .fillMaxSize()
        .statusBarsPadding()
        .background(WeLoveMarathonTheme.colors.contentBackground)) {

        ShoppingComponent(
            shoppings = state.shoppings,
            currentCategory = state.currentSelected,
            recommended = state.recommended,
            modifier = Modifier.fillMaxSize().padding(end = 100.dp))

        ShoppingCategoryComponent(
            currentSelected = state.currentSelected.ordinal,
            onCategoryClicked = onCategoryClicked,
            modifier = Modifier.align(Alignment.TopEnd))
    }
}

@ExperimentalAnimationApi
@Preview(name = "Full content")
@Composable
fun ShoppingContentPreview() {
    val reducer = ShoppingReducer()
    val state =
        reducer.reduce(ShoppingState(),
            ShoppingPartialState.Error(""))
    val finalState =
        reducer.reduce(state, ShoppingPartialState.OnShoppingsSuccess(
            Shopping().fakeList(),
            Shopping("recomended"),
            ShoppingCategories.Pet
        ))
    MaterialTheme {
        ShoppingContent(state = finalState, modifier = Modifier)
    }
}
