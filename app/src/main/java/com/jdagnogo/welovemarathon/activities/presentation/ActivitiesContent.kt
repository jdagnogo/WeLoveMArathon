package com.jdagnogo.welovemarathon.activities.presentation

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import com.jdagnogo.welovemarathon.common.category.CategoryScreen
import com.jdagnogo.welovemarathon.common.ui.component.DividerComponent
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent
import com.jdagnogo.welovemarathon.common.ui.component.simpleListComponent
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme
import com.jdagnogo.welovemarathon.activities.domain.ActivitiesCategory
import com.jdagnogo.welovemarathon.activities.presentation.ActivitiesState

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun ActivitiesContent(
    state: ActivitiesState,
    onItemSelected: (String) -> Unit = {},
    onMapSelected: () -> Unit,
    onBackPressed: () -> Unit,
    onFiltersSelected: (ids: List<String>) -> Unit = {},
    onResetSelected: () -> Unit = {},
    onFilterClicked: (isVisible: Boolean) -> Unit,
    onRecommendedDialogClosed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CategoryScreen(
        title = state.currentSelected.name,
        recommendedItems = state.recommendedItems,
        items = state.activitiess,
        tags = state.categoryTags,
        onFiltersSelected = onFiltersSelected,
        modifier = modifier,
        onItemSelected = onItemSelected,
        onMapSelected = onMapSelected,
        onBackPressed = onBackPressed,
        onFilterClicked = onFilterClicked,
        onResetSelected = onResetSelected,
        shouldOpenRecommenderDialog = state.shouldOpenRecommendedDialog,
        shouldOpenFilterDialog = state.shouldOpenFilterDialog,
        onRecommendedDialogClosed = onRecommendedDialogClosed,
        currentRecommended = state.currentActivitiesSelected,
    )
}
