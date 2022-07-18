package com.jdagnogo.welovemarathon.common.category

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.component.CarouselWithPreview
import com.jdagnogo.welovemarathon.common.ui.component.ContactComponent
import com.jdagnogo.welovemarathon.common.ui.component.HorizontalCarousel
import com.jdagnogo.welovemarathon.common.ui.theme.*
import com.jdagnogo.welovemarathon.common.utils.redirectToLink
import com.jdagnogo.welovemarathon.common.utils.redirectToPhone

@Composable
fun RecommendedCategoryDetailsDialogComponent(
    item: RecommendedCategoryDetails?,
    onDismissRequest: () -> Unit,
) {
    if (item != null) {
        Dialog(onDismissRequest = { onDismissRequest() }) {
            RecommendedCategoryDetailsDialogContent(
                item = item,
            )
        }
    }
}

@Composable
fun RecommendedCategoryDetailsDialogContent(
    item: RecommendedCategoryDetails,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    Card(
        shape = RoundedCornerShape(10),
        elevation = 8.dp
    ) {
        Column(
            modifier
                .background(PrimaryLight)
        ) {
            val images = remember { item.images }
            CarouselWithPreview(
                urls = images.map { it.url },
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.medium)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        redirectToLink(uriHandler, item.website)
                    }
                    .padding(horizontal = MaterialTheme.spacing.medium)
            ) {
                Text(
                    overflow = TextOverflow.Ellipsis,
                    style = SplashScreenTitleStyle,
                    maxLines = 1,
                    text = item.name,
                )
                Icon(
                    painterResource(id = R.drawable.ic_link),
                    contentDescription = "back",
                    tint = Color.White,
                    modifier = modifier
                        .padding(start = MaterialTheme.spacing.extraMedium)
                        .size(MaterialTheme.spacing.medium)

                )
            }
            Text(
                overflow = TextOverflow.Ellipsis,
                style = tagsTitleStyle,
                text = item.tags,
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium)
            )

            Text(
                overflow = TextOverflow.Ellipsis,
                style = recommendedCategoryContentStyle,
                text = item.description,
                modifier = Modifier
                    .padding(vertical = MaterialTheme.spacing.huge)
                    .padding(horizontal = MaterialTheme.spacing.medium)
            )

            ContactComponent(
                icon = R.drawable.location,
                iconSize = 24.dp,
                onClicked = { redirectToLink(uriHandler, item.locationLink) },
                text = item.location,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .padding(horizontal = MaterialTheme.spacing.medium),
            )

            ContactComponent(
                icon = R.drawable.ic_phone,
                iconSize = 24.dp,
                onClicked = {
                    redirectToPhone(context, item.number)
                },
                text = item.number,
                modifier = Modifier
                    .padding(MaterialTheme.spacing.medium)
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(name = "Custom Dialog")
@Composable
fun RecommendedCategoryDetailsDialogComponentPreview() {
    RecommendedCategoryDetailsDialogContent(
        item = RecommendedCategoryDetailsFake
    )
}
