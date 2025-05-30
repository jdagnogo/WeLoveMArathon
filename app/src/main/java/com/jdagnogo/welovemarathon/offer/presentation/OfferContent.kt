package com.jdagnogo.welovemarathon.offer.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.SecondaryDark
import com.jdagnogo.welovemarathon.offer.domain.OfferWithRestaurant
import com.jdagnogo.welovemarathon.offer.domain.Promo
import com.jdagnogo.welovemarathon.restaurant.domain.Restaurant
import com.jdagnogo.welovemarathon.restaurant.presentation.RestaurantDetailsContent
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfferContent(
    offer: OfferWithRestaurant,
    onGetOfferClicked: () -> Unit = {}, //TODO : remove this
) {
    val showDialog = remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    Scaffold(
        backgroundColor = Color.White,
        topBar = {
            TopAppBar(
                backgroundColor = Color(0xFF1E4F7B),
                elevation = 0.dp,
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth(),
                title = {
                    Text(
                        text = stringResource(id = R.string.offer_of_the_month),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h6.copy(
                            color = Color.White,
                            fontSize = 24.sp,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )

                }
            )
        }
    ) { padding ->
        OfferComponent(
            modifier = Modifier.padding(padding),
            offer = offer,
            showDialog = showDialog.value,
            onDisplayOfferClaimed = { showDialog.value = true },
            onDismiss = { showDialog.value = false },
            onNavigateToRestaurant = {
                showBottomSheet = true
            }
        )

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                RestaurantDetailsContent(
                    restaurant = offer.restaurant
                )
            }
        }
    }
}

@Composable
private fun OfferComponent(
    modifier: Modifier = Modifier,
    offer: OfferWithRestaurant, showDialog: Boolean,
    onDisplayOfferClaimed: () -> Unit = {},
    onNavigateToRestaurant: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = 10.dp,
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Image(
                    painter = rememberImagePainter(
                        data = offer.restaurant?.bigImages?.firstOrNull(),
                        builder = {
                            crossfade(true)
                            error(R.drawable.ic_wlm_logo)
                        }
                    ),
                    contentDescription = "Promo Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .clickable {
                            onNavigateToRestaurant()
                        }
                )

                PromoComponent(
                    offer = offer, modifier = Modifier.padding(vertical = 16.dp),
                    onNavigateToRestaurant = onNavigateToRestaurant
                )

                DescriptionComponent(offer = offer)

                ClamButton(
                    onClaimClicked = onDisplayOfferClaimed,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }

        OfferClaimed(
            showDialog = showDialog,
            onDismiss = onDismiss
        )
    }

}

@Composable
private fun PromoComponent(
    modifier: Modifier = Modifier, offer: OfferWithRestaurant,
    onNavigateToRestaurant: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .paint(
                painterResource(R.drawable.coupon),
                contentScale = ContentScale.FillBounds,
                colorFilter = ColorFilter.tint(SecondaryDark)
            )
            .padding(horizontal = 16.dp)
            .clickable {
                onNavigateToRestaurant()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = offer.restaurant?.name.orEmpty(),
                style = MaterialTheme.typography.h4.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 24.sp
                ),
            )

            Text(
                text = offer.promos.firstOrNull()?.value.orEmpty(),
                color = Color.White,
                style = MaterialTheme.typography.h5.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.promo_icon),
            contentDescription = "Promo Icon",
            tint = Color.White,
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
private fun ClamButton(modifier: Modifier, onClaimClicked: () -> Unit) {
    Button(
        onClick = onClaimClicked,
        modifier = modifier
            .width(250.dp)
            .height(45.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(
                0xFF1E4F7B
            )
        )
    ) {
        Text(
            text = stringResource(R.string.claim_offer),
            color = Color.White,
            style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp
            )
        )
    }
}

@Composable
private fun DescriptionComponent(modifier: Modifier = Modifier, offer: OfferWithRestaurant) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF1E4F7B).copy(alpha = 0.1f))
            .padding(16.dp)
    ) {
        Text(
            text = offer.description,
            color = Secondary,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            letterSpacing = 0.25.sp,
        )
        Spacer(modifier = Modifier.height(12.dp))

        val formattedDate =
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(offer.endDate)
        Text(
            text = "Expires on $formattedDate",
            style = MaterialTheme.typography.body1.copy(
                color = Color(0xFF1E4F7B),
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OfferContentPreview() {
    MaterialTheme {
        OfferContent(
            offer = OfferWithRestaurant(
                id = "default",
                title = "Default Offer",
                description = "For the month of (month), (Restaurant) offers (offer) on all food items for in store orders. The offer works every day and can be claimed using the code below.",
                restaurant = Restaurant(name = "Default Restaurant "),
                promos = listOf(Promo(value = "35%")),
            )
        )
    }
}

