package com.jdagnogo.welovemarathon.common.submenu

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.statusBarsPadding
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.banner.GifBannerComponent
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent
import com.jdagnogo.welovemarathon.common.ui.theme.TagColor
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.common.utils.backgroundColor

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun SubMenuScreen(
    shouldDisplayTitle: Boolean = true,
    subMenuUiModel: SubMenuUiModel?,
    subMenuInteractions: SubMenuInteractions,
    modifier: Modifier,
) {
    if (subMenuUiModel == null) return
    Box(
        modifier = modifier
            .semantics { backgroundColor = Color.White }
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            if (shouldDisplayTitle) {
                TitleComponent(
                    title = subMenuUiModel.screenName,
                    onLeftIconClicked = subMenuInteractions.onBackPressed,
                    onRightIconClicked = subMenuInteractions.onMapSelected
                )
            }

            Card(
                elevation = 0.dp,
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = MaterialTheme.spacing.medium)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(50.dp))
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = subMenuUiModel.image,
                            builder = {
                                crossfade(true)
                                error(R.drawable.ic_wlm_logo)
                            }
                        ),
                        contentDescription = subMenuUiModel.screenName,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentScale = ContentScale.Crop,
                        colorFilter = ColorFilter.tint(
                            color = Color(0xFF1E4F7B).copy(alpha = 0.8f),
                            blendMode = BlendMode.Color
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.weight(0.57f))
        }

        SubMenuComponent(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.medium)
                .padding(bottom = if (subMenuUiModel.banner != null) MaterialTheme.spacing.medium * 2 else MaterialTheme.spacing.medium),
            items = subMenuUiModel.items,
            onItemSelected = subMenuInteractions.onItemSelected,
        )

        if (subMenuUiModel.banner != null) {
            GifBannerComponent(
                gifBanner = subMenuUiModel.banner,
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = MaterialTheme.spacing.extraMedium)
            )
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Preview(name = "SubMenuScreen")
@Composable
fun SubMenuScreenPreview() {
    val subMenuUiModel = SubMenuUiModel(
        screenName = "Shopping",
        items = SubMenuShopping.values().toList().map { it.subMenuItem },
        image = 0,
        banner = null,
        backgroundColor = TagColor
    )
    MaterialTheme {
        SubMenuScreen(
            subMenuUiModel = subMenuUiModel,
            modifier = Modifier,
            subMenuInteractions = SubMenuInteractions(
                onItemSelected = {},
                onBackPressed = {},
                onMapSelected = {}
            )
        )
    }
}
