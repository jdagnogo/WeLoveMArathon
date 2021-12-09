package com.jdagnogo.welovemarathon.common.banner

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

@ExperimentalMaterialApi
@Composable
fun GifBannerComponent(gifBanner: GifBanner, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    Card(
        shape = MaterialTheme.shapes.large,
        onClick = {
            uriHandler.openUri(gifBanner.link)
        },
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp)) {
        val imageLoader = ImageLoader.invoke(context).newBuilder()
            .componentRegistry {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder(context))
                } else {
                    add(GifDecoder())
                }
            }.build()
        Image(
            painter = rememberImagePainter(
                data = gifBanner.resLink,
                builder = {
                    crossfade(true)
                },
                imageLoader = imageLoader
            ),
            contentDescription = gifBanner.name,
            contentScale = ContentScale.Crop
        )
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun GifBannerComponentPreview() {
    MaterialTheme {
        val banner = GifBanner().fakeBanner()
        GifBannerComponent(banner, modifier = Modifier)
    }
}
