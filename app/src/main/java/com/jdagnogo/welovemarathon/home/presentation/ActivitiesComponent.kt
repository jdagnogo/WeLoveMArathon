package com.jdagnogo.welovemarathon.home.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme
import com.jdagnogo.welovemarathon.home.domain.Activities
import kotlin.math.max

@ExperimentalFoundationApi
@Composable
fun ActivitiesGridComponent(
    activities: List<Activities>,
    onActivitySelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        TitleComponent(title = "Activities", alignRight = true, modifier = Modifier)
        LazyRow(
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .animateContentSize(),
        ) {
            items(activities.size) { index ->
                val activity = activities[index]
                val randomColor: Color = remember { activity.backgroundColor }
                ActivityItem(
                    index = index,
                    activities = activity,
                    color = randomColor,
                    onActivityClicked = onActivitySelected,
                )
            }
        }
    }
}

@Composable
fun ActivityItem(
    index: Int = 0,
    activities: Activities,
    onActivityClicked: (Int) -> Unit,
    color: Color = Secondary,
    modifier: Modifier = Modifier,
) {
    Layout(
        modifier = modifier
            .height(100.dp)
            .aspectRatio(2f)
            .shadow(elevation = 3.dp, shape = CategoryShape)
            .clip(CategoryShape)
            .background(Brush.horizontalGradient(WeLoveMarathonTheme.colors.gradient))
            .clickable { onActivityClicked(index) },
        content = {
            Text(
                text = activities.name,
                style = MaterialTheme.typography.subtitle1,
                color = Color.Black,
                modifier = Modifier
                    .padding(4.dp)
                    .padding(start = 8.dp)
            )
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(
                        color = Color.White,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = activities.icon,
                        builder = {
                            crossfade(true)
                            placeholder(drawableResId = R.drawable.ic_wlm_logo)
                        }
                    ),
                    contentDescription = activities.name,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop,
                )
            }
        }
    ) { measurables, constraints ->
        // Text given a set proportion of width (which is determined by the aspect ratio)
        val textWidth = (constraints.maxWidth * CategoryTextProportion).toInt()
        val textPlaceable = measurables[0].measure(Constraints.fixedWidth(textWidth))

        // Image is sized to the larger of height of item, or a minimum value
        // i.e. may appear larger than item (but clipped to the item bounds)
        val imageSize = max(MinImageSize.roundToPx(), constraints.maxHeight)
        val imagePlaceable = measurables[1].measure(Constraints.fixed(imageSize, imageSize))
        layout(
            width = constraints.maxWidth,
            height = constraints.minHeight
        ) {
            textPlaceable.placeRelative(
                x = 0,
                y = (constraints.maxHeight - textPlaceable.height) / 2 // centered
            )
            imagePlaceable.placeRelative(
                // image is placed to end of text i.e. will overflow to the end (but be clipped)
                x = textWidth,
                y = (constraints.maxHeight - imagePlaceable.height) / 2 // centered
            )
        }
    }
}

private val MinImageSize = 134.dp
private val CategoryShape = RoundedCornerShape(10.dp)
private const val CategoryTextProportion = 0.55f
val CircleShape = RoundedCornerShape(50)

@ExperimentalFoundationApi
@Preview
@Composable
fun ActivitiesGridComponentPreview() {
    val activities = Activities.values()
    MaterialTheme {
        ActivitiesGridComponent(activities = activities.toList(), {})
    }
}
