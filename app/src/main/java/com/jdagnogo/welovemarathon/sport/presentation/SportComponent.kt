package com.jdagnogo.welovemarathon.sport.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.common.ui.component.simpleListComponent
import com.jdagnogo.welovemarathon.sport.domain.Sport
import com.jdagnogo.welovemarathon.sport.domain.toFakeList

@Composable
fun SportComponent(
    sports: List<Sport> = listOf(),
    currentCategory: String,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Text(
                text = currentCategory,
                style = MaterialTheme.typography.h5,
            )

            Spacer(modifier = Modifier.padding(top = 16.dp))
        }

        simpleListComponent(sports.map { it.toSimpleListItem() }, this)
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun ShoppingComponentPreview() {
    MaterialTheme {
        val list = Sport().toFakeList()
        SportComponent(sports = list, currentCategory = "category")
    }
}
