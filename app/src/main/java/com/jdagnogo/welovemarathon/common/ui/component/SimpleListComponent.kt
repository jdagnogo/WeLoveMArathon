package com.jdagnogo.welovemarathon.common.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.SimpleListItem
import com.jdagnogo.welovemarathon.common.fakeList

@Composable
fun SimpleListComponent(item: SimpleListItem, modifier: Modifier = Modifier) {
    DividerComponent()

    Text(text = item.name, modifier = Modifier.padding(top = 8.dp))

    ContactComponent(icon = R.drawable.ic_location,
        textSize = 12.sp,
        iconSize = 24.dp,
        text = item.location, modifier = Modifier.padding(top = 16.dp))

    ContactComponent(icon = R.drawable.ic_phone,
        textSize = 12.sp,
        iconSize = 24.dp,
        text = item.number, modifier = Modifier.padding(top = 16.dp))
}

fun simpleListComponent(
    data: List<SimpleListItem>,
    scope: LazyListScope,
    modifier: Modifier = Modifier,
) {
    with(scope) {
        itemsIndexed(data) { _, item ->
            SimpleListComponent(item = item, modifier)
        }
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun SimpleListComponentPreview() {
    MaterialTheme {
        LazyColumn(modifier = Modifier
            .fillMaxWidth()) {
            simpleListComponent(SimpleListItem().fakeList(), this)
        }
    }
}