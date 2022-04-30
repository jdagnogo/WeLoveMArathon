package com.jdagnogo.welovemarathon.beach.presentation

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
fun BeachContent(
    state: BeachState,
    onBeachSelected: (id: String) -> Unit,
    onRecommendedSelected: (id: String) -> Unit,
    onMapSelected: () -> Unit,
    onBackPressed: () -> Unit,
    onRecommendedDialogClosed: () -> Unit,
    modifier: Modifier = Modifier,
) {

    LongCategoryScreen(
        title = state.currentSelected.name,
        recommendedItems = state.recommendedItems,
        items = state.beaches,
        onItemSelected = onBeachSelected,
        onRecommendedSelected = onRecommendedSelected,
        onMapSelected = onMapSelected,
        onBackPressed = onBackPressed,
        shouldOpenRecommenderDialog = state.shouldOpenRecommendedDialog,
        onRecommendedDialogClosed = onRecommendedDialogClosed,
        currentRecommended = state.currentBeachSelected,
        modifier = modifier,
    )
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Preview(name = "FoodContent")
@Preview("Dark : FoodContent", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BeachDetailsContentPreview() {
    val reducer = BeachReducer()
    val state =
        reducer.reduce(
            BeachState(),
            BeachPartialState.Error("")
        )
    val finalState =
        reducer.reduce(
            state, BeachPartialState.OnBeachSuccess(
                beaches = emptyList()
            )
        )
    MaterialTheme {
        BeachContent(
            finalState,
            onMapSelected = {},
            onBackPressed = {},
            onRecommendedSelected = {},
            onBeachSelected = {},
            onRecommendedDialogClosed = {},
            modifier = Modifier
        )
    }
}
