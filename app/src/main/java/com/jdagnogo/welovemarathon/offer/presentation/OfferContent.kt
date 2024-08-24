package com.jdagnogo.welovemarathon.offer.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.insets.ui.Scaffold
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.category.FilterDialogButton
import com.jdagnogo.welovemarathon.common.ui.component.DividerComponent
import com.jdagnogo.welovemarathon.common.ui.theme.OfferTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.common.ui.theme.TagColor
import com.jdagnogo.welovemarathon.common.ui.theme.emptyScreenTitle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.offer.domain.OfferWithRestaurant

@Composable
fun OfferContent(
    offer: OfferWithRestaurant,
    onGetOfferClicked: () -> Unit = {},
) {
    Scaffold(
        backgroundColor = Primary,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
            ) {
                Text(
                    style = OfferTitleStyle,
                    text = offer.title
                )
                Text(
                    style = OfferTitleStyle,
                    text = stringResource(id = R.string.offer_title)
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = emptyScreenTitle,
                    text = stringResource(id = R.string.offer_subtitle)
                )
            }
        },
        bottomBar = {
            Column(
                Modifier
                    .background(Color.White)
                    .padding(bottom = MaterialTheme.spacing.semiHuge)
            ) {
                DividerComponent(
                    Modifier.fillMaxWidth(),
                    thickness = 2.dp,
                    color = TagColor
                )
                FilterDialogButton(
                    stringResource(id = R.string.offer_button),
                    {
                        onGetOfferClicked()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MaterialTheme.spacing.small),
                )
            }

        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues = paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.offer))
            val progress by animateLottieCompositionAsState(
                composition,
                iterations = LottieConstants.IterateForever,
            )
            LottieAnimation(
                modifier = Modifier.size(400.dp),
                composition = composition,
                progress = { progress },
            )
        }

    }
}