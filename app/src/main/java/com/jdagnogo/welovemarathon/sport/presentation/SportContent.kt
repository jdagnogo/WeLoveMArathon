package com.jdagnogo.welovemarathon.sport.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.jdagnogo.welovemarathon.common.ui.component.RightMenuComponent
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme

@Composable
fun SportContent(
    state: SportState,
    onCategoryClicked: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier
        .fillMaxSize()
        .navigationBarsPadding(start = false, end = false)
        .statusBarsPadding()
        .background(WeLoveMarathonTheme.colors.contentBackground)) {

        SportComponent(
            sports = state.sports,
            currentCategory = state.currentCategory?.name ?: "",
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 100.dp))

        RightMenuComponent(
            currentSelected = state.currentCategory?.ordinal ?: 0,
            categories = state.categories.map { it.toRightMenuData() },
            onCategoryClicked = { onCategoryClicked(it) },
            modifier = Modifier.align(Alignment.TopEnd))
    }
}

/*@ExperimentalAnimationApi
@Preview(name = "Full content")
@Composable
fun SportContent() {
    val reducer = SportReducer()
    val state =
        reducer.reduce(SportState(),
            SportPartialState.Error(""))
    val finalState =
        reducer.reduce(state, SportPartialState.OnSportsSuccess(
            Shopping().fakeList(),
            Shopping("recomended"),
            ShoppingCategories.Pet
        ))
    MaterialTheme {
        SportContent(state = finalState, modifier = Modifier)
    }
}*/
