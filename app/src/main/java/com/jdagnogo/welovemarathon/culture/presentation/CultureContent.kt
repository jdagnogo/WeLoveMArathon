package com.jdagnogo.welovemarathon.culture.presentation

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jdagnogo.welovemarathon.common.category.LongCategoryScreen

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun CultureContent(
    state: CultureState,
    onCultureSelected: (id: String) -> Unit,
    onRecommendedSelected: (id: String) -> Unit,
    onMapSelected: () -> Unit,
    onBackPressed: () -> Unit,
    onRecommendedDialogClosed: () -> Unit,
    modifier: Modifier = Modifier,
) {

    LongCategoryScreen(
        title = state.mainScreenName,
        recommendedItems = state.recommendedItems,
        items = state.longCategoryItems,
        onItemSelected = onCultureSelected,
        onRecommendedSelected = onRecommendedSelected,
        onMapSelected = onMapSelected,
        onBackPressed = onBackPressed,
        shouldOpenRecommenderDialog = state.shouldOpenRecommendedDialog,
        onRecommendedDialogClosed = onRecommendedDialogClosed,
        currentRecommended = state.currentCultureSelected,
        modifier = modifier,
    )
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Preview(name = "FoodContent")
@Preview("Dark : FoodContent", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CultureDetailsContentPreview() {
    val reducer = CultureReducer()
    val state =
        reducer.reduce(
            CultureState(),
            CulturePartialState.Error("")
        )
    val finalState =
        reducer.reduce(
            state, CulturePartialState.OnCultureSuccess(
                cultures = emptyList()
            )
        )
    MaterialTheme {
        CultureContent(
            finalState,
            onMapSelected = {},
            onBackPressed = {},
            onRecommendedSelected = {},
            onCultureSelected = {},
            onRecommendedDialogClosed = {},
            modifier = Modifier
        )
    }
}
