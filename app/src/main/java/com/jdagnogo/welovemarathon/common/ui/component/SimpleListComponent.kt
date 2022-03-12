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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W300
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.domain.SimpleListItem
import com.jdagnogo.welovemarathon.common.domain.fakeList
import com.jdagnogo.welovemarathon.common.ui.theme.ContentBackgroundDark
import com.jdagnogo.welovemarathon.common.ui.theme.Neutral4
import com.jdagnogo.welovemarathon.common.utils.redirectToLink
import com.jdagnogo.welovemarathon.common.utils.redirectToPhone

@Composable
fun SimpleListComponent(
    item: SimpleListItem,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    DividerComponent()

    Text(
        text = item.name,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = Color.Black,
        modifier = Modifier.padding(top = 8.dp)
    )

    ContactComponent(
        icon = R.drawable.location,
        iconSize = 24.dp,
        onClicked = { redirectToLink(uriHandler, item.locationLink) },
        text = item.location, modifier = Modifier.padding(top = 16.dp)
    )

    ContactComponent(
        icon = R.drawable.ic_phone,
        iconSize = 24.dp,
        onClicked = {
            redirectToPhone(context, item.number)
        },
        text = item.number, modifier = Modifier.padding(top = 16.dp)
    )
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
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            simpleListComponent(SimpleListItem().fakeList(), this)
        }
    }
}
