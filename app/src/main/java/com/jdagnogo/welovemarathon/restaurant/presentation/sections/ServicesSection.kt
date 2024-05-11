package com.jdagnogo.welovemarathon.restaurant.presentation.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryLight
import com.jdagnogo.welovemarathon.common.ui.theme.recommendedCategoryContentStyle
import com.jdagnogo.welovemarathon.common.ui.theme.tagsTitleStyle
import com.jdagnogo.welovemarathon.restaurant.domain.RestaurantService

fun LazyListScope.servicesSection(
    modifier: Modifier = Modifier,
    services: List<RestaurantService>
) {
    item("servicesSection") {
        FlowRow(
            mainAxisSpacing = 8.dp,
            crossAxisSpacing = 8.dp,
            lastLineMainAxisAlignment = FlowMainAxisAlignment.Center,
            modifier = modifier.fillParentMaxWidth()
        ) {
            val itemModifier = Modifier.fillMaxWidth(0.3f)
            repeat(services.size) { index ->
                ServiceComponent(
                    modifier = itemModifier,
                    services[index]
                )
            }
        }
    }
}

@Composable
private fun ServiceComponent(
    modifier: Modifier = Modifier,
    service: RestaurantService,
) {
    Column(
        modifier = modifier
    ) {
        Image(
            painter = rememberImagePainter(data = service.icon),
            contentDescription = service.title,
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop,
        )
        Card(
            backgroundColor = PrimaryLight,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(modifier = Modifier
                .padding(8.dp)
                .height(90.dp)) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = service.title,
                    style = tagsTitleStyle.copy(fontSize = 14.sp)
                )
                Divider(Modifier.padding(vertical = 8.dp))
                Text(
                    text = service.description,
                    style = recommendedCategoryContentStyle.copy(fontSize = 12.sp),
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

    }

}