package com.jdagnogo.welovemarathon.beach.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.beach.domain.Beach
import com.jdagnogo.welovemarathon.beach.domain.toFakeList
import com.jdagnogo.welovemarathon.common.ui.component.DividerComponent
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryDark

@Composable
fun BeachItem(
    beach: Beach,
    position: Int,
    onBeachSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(modifier = modifier
        .fillMaxSize()
        .padding(bottom = 24.dp)
        .clickable {
            onBeachSelected(position)
        }) {
        val (image, title, divider) = createRefs()
        Image(
            painter = rememberImagePainter(
                data = beach.image,
                builder = {
                    crossfade(true)
                    error(R.drawable.ic_wlm_logo)
                }
            ),
            contentDescription = beach.name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(5))
                .border(2.dp, PrimaryDark, RoundedCornerShape(5))
                .height(120.dp)
                .constrainAs(image) {
                    linkTo(parent.start, parent.end)
                    top.linkTo(parent.top)
                }
                .fillMaxSize(),
            contentScale = ContentScale.FillWidth
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = beach.name,
            fontSize = 20.sp,
            color = PrimaryDark,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(title) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(image.bottom)
                    width = Dimension.fillToConstraints
                }
        )
        DividerComponent(
            Modifier
                .padding(top = 8.dp, start = 24.dp, end = 24.dp)
                .constrainAs(divider) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
    }
}

fun beachList(beaches: List<Beach>, onBeachSelected: (Int) -> Unit, scope: LazyListScope) {
    with(scope) {
        itemsIndexed(beaches) { currentPage, beach ->
            BeachItem(beach = beach, currentPage, onBeachSelected)
        }
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun BeachComponentPreview() {
    val data = Beach().toFakeList()
    MaterialTheme {
        BeachItem(beach = data.first(), 0, {})
    }
}
