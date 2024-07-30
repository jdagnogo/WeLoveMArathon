package com.jdagnogo.welovemarathon.restaurant.presentation.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.emptyScreenSubTitle
import com.jdagnogo.welovemarathon.common.ui.theme.emptyScreenTitle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing

@Composable
fun RestaurantEmptySection(
    modifier: Modifier = Modifier,
    onRedirectToFilterClicked: () -> Unit = {},
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(horizontal = MaterialTheme.spacing.medium),

            ) {
            Spacer(modifier = Modifier.padding(top = MaterialTheme.spacing.huge))

            Image(
                painter = rememberImagePainter(
                    data = R.drawable.ic_fav_unselected,
                    builder = {
                        crossfade(true)
                        error(R.drawable.ic_wlm_logo)
                    }
                ),
                contentDescription = "",
                modifier = Modifier.size(300.dp),
            )

            Text(
                text = stringResource(id = R.string.restaurant_empty_title),
                style = emptyScreenTitle,
                modifier = Modifier.padding(top = MaterialTheme.spacing.medium)
            )

            Text(
                text = stringResource(id = R.string.restaurant_empty_description),
                style = emptyScreenSubTitle,
                modifier = Modifier.padding(top = MaterialTheme.spacing.medium)
            )

            Button(
                shape = MaterialTheme.shapes.medium,
                onClick = { onRedirectToFilterClicked() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Secondary),
                modifier = modifier
                    .padding(top = MaterialTheme.spacing.huge)
            ) {
                Text(text = "Go to categories")
            }
        }
    }
}