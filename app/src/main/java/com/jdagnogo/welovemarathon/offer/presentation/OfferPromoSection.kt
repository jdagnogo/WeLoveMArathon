package com.jdagnogo.welovemarathon.offer.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.ActivityColor
import com.jdagnogo.welovemarathon.common.ui.theme.FoodColor
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryLight
import com.jdagnogo.welovemarathon.common.ui.theme.ShoppingColor
import com.jdagnogo.welovemarathon.common.ui.theme.TitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme
import com.jdagnogo.welovemarathon.common.ui.theme.emptyScreenTitle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.common.ui.theme.wineDescription
import com.jdagnogo.welovemarathon.offer.domain.Promo

fun LazyListScope.offerPromosSection(
    modifier: Modifier = Modifier,
    promos: List<Promo> = listOf(),
) {
    item {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.medium)
        ) {
            promos.forEachIndexed { index, promo ->
                OfferPromoComponent(
                    promo = promo,
                    color = when (index) {
                        0 -> ActivityColor
                        1 -> FoodColor
                        else -> ShoppingColor
                    }
                )
            }
        }
    }
}

@Composable
private fun OfferPromoComponent(
    promo: Promo,
    color: Color,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(modifier = modifier.width(90.dp)) {
        val (text, card) = createRefs()
        Card(
            backgroundColor = PrimaryLight,
            shape = RoundedCornerShape(100.dp),
            modifier = Modifier
                .constrainAs(card) {
                    linkTo(parent.start, parent.end)
                    top.linkTo(parent.top, 40.dp)
                }

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(color = color)
                    .padding(horizontal = 16.dp),
            )
            {
                Image(
                    painter = rememberImagePainter(
                        data = promo.icon,
                        builder = {
                            crossfade(true)
                            error(R.drawable.ic_wlm_logo)
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .size(90.dp),
                    contentScale = ContentScale.Crop,
                )

                Text(
                    text = promo.title,
                    textAlign = TextAlign.Center,
                    style = emptyScreenTitle,
                    color = Color.White,
                    modifier = modifier

                )

                Text(
                    text = promo.subtitle,
                    textAlign = TextAlign.Center,
                    style = wineDescription,
                    modifier = modifier.padding(vertical = 16.dp)

                )
            }
        }
        if (promo.value.isNotEmpty()) {
            Text(
                text = promo.value,
                style = TitleStyle.copy(color = Color.Black, fontSize = 18.sp),
                modifier = Modifier
                    .background(shape = CircleShape, color = Color.White)
                    .padding(8.dp)
                    .constrainAs(text) {
                        top.linkTo(card.top)
                        linkTo(parent.start, parent.end)
                        bottom.linkTo(card.top)
                    },
            )
        }
    }
}

@Preview
@Composable
private fun OfferPromoComponentPreview() {
    WeLoveMarathonTheme {
        Surface(Modifier.background(Color.Red)) {
            LazyColumn(
                contentPadding = PaddingValues(
                    bottom = MaterialTheme.spacing.large,
                ),
                modifier = Modifier
            ) {
                offerPromosSection(
                    promos = listOf(
                        Promo(
                            icon = "posuere",
                            title = "eu",
                            subtitle = "volutpat",
                            value = "altera"
                        ),
                        Promo(
                            icon = "interpretaris",
                            title = "praesent",
                            subtitle = "facilisis",
                            value = "tritani",
                        ),
                        Promo(
                            icon = "sodales",
                            title = "justo",
                            subtitle = "error",
                            value = ""
                        )
                    )
                )
            }
        }
    }
}