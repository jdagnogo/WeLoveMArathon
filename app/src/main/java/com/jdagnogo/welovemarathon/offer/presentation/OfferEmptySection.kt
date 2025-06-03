package com.jdagnogo.welovemarathon.offer.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.emptyScreenSubTitle
import com.jdagnogo.welovemarathon.common.ui.theme.emptyScreenTitle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing

@Composable
fun OfferEmptySection(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),

        ) {
        Spacer(modifier = Modifier.padding(top = MaterialTheme.spacing.huge))

        Image(
            painter = rememberImagePainter(
                data = R.drawable.ic_discount,
                builder = {
                    crossfade(true)
                    error(R.drawable.ic_wlm_logo)
                }
            ),
            contentDescription = "",
            modifier = Modifier.size(150.dp),
        )

        Text(
            text = stringResource(id = R.string.offer_no_offer_title),
            textAlign = TextAlign.Center,
            style = emptyScreenTitle,
            modifier = Modifier.padding(top = MaterialTheme.spacing.medium)
        )

        Text(
            text = stringResource(id = R.string.offer_no_offer),
            style = emptyScreenSubTitle,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = MaterialTheme.spacing.medium)
        )

    }
}