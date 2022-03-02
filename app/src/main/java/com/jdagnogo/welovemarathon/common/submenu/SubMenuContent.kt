package com.jdagnogo.welovemarathon.common.submenu

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.statusBarsPadding
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.banner.GifBannerComponent
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent
import com.jdagnogo.welovemarathon.common.ui.theme.ShoppingColor
import com.jdagnogo.welovemarathon.common.ui.theme.spacing

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun SubMenuScreen(
    subMenuUiModel: SubMenuUiModel?,
    onItemSelected: (Int) -> Unit,
    onMapSelected: () -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier,
) {
    if (subMenuUiModel == null) return
    Box(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(subMenuUiModel.backgroundColor)
    ) {
        Card(
            elevation = 0.dp,
            shape = RoundedCornerShape(0.dp, 0.dp, 50.dp, 50.dp),
            modifier = Modifier
                .height(500.dp)
                .fillMaxWidth()

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
                    .height(250.dp),
                contentScale = ContentScale.Crop,
            )
        }
        TitleComponent(
            title = subMenuUiModel.screenName,
            onLeftIconClicked = onBackPressed,
            onRightIconClicked = onMapSelected
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = MaterialTheme.spacing.medium)
                .padding(bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            SubMenuComponent(
                items = subMenuUiModel.items,
                onItemSelected = onItemSelected,
            )
        }

        if (subMenuUiModel.banner != null) {
            GifBannerComponent(
                gifBanner = subMenuUiModel.banner,
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = MaterialTheme.spacing.extraHuge)
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
        backgroundColor = ShoppingColor
    )
    MaterialTheme {
        SubMenuScreen(
            subMenuUiModel = subMenuUiModel,
            modifier = Modifier,
            onItemSelected = {},
            onBackPressed = {},
            onMapSelected = {}
        )
    }
}
