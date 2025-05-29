package com.jdagnogo.welovemarathon.offer.presentation

import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.offer.domain.OfferWithRestaurant

@Composable
fun OfferContent(
    offer: OfferWithRestaurant,
    onGetOfferClicked: () -> Unit = {},
) {
    val context = LocalContext.current
    val clipboardManager =
        remember { context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager }
    val showDialog = remember { mutableStateOf(false) }

    Scaffold(
        backgroundColor = Color.White
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopAppBar(
                    backgroundColor = Color(0xFF1E4F7B),
                    elevation = 0.dp,
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth(),
                    title = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp, horizontal = 20.dp)
                        ) {
                            Text(
                                text = "Offer of the Month",
                                style = MaterialTheme.typography.h6.copy(
                                    color = Color.White,
                                    fontSize = 24.sp,
                                ),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 28.dp)
                        .padding(top = 16.dp),
                    shape = RoundedCornerShape(20.dp),
                    elevation = 10.dp,
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.promo),
                            contentDescription = "Promo Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(20.dp))
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.coupon),
                                contentDescription = null,
                                modifier = Modifier.fillMaxWidth(),
                                contentScale = ContentScale.FillWidth,
                                colorFilter = ColorFilter.tint(Color(0xFF153754))
                            )
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .padding(horizontal = 24.dp, vertical = 26.dp),
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.Top
                                ) {
                                    Text(
                                        text = "Restaurant",
                                        style = MaterialTheme.typography.h4.copy(
                                            color = Color.White,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 28.sp
                                        ),
                                        modifier = Modifier.padding(bottom = 0.dp)
                                    )

                                    Text(
                                        text = "35% Coupon",
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
                                    modifier = Modifier
                                        .size(48.dp)
                                        .align(Alignment.CenterEnd)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF1E4F7B).copy(alpha = 0.1f))
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "For the month of (month), (Restaurant) offers (offer) on all food items for in store orders. The offer works every day and can be claimed using the code below.",
                                    style = MaterialTheme.typography.body1.copy(
                                        color = Color.Black.copy(alpha = 0.7f),
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 14.sp,
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                        letterSpacing = 0.25.sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "Expires on (date)",
                                    style = MaterialTheme.typography.body1.copy(
                                        color = Color(0xFF1E4F7B),
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 14.sp,
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                    )
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { showDialog.value = true },
                            modifier = Modifier
                                .width(250.dp)
                                .height(45.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(
                                    0xFF1E4F7B
                                )
                            )
                        ) {
                            Text(
                                text = "Claim",
                                color = Color.White,
                                style = MaterialTheme.typography.h5.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 18.sp
                                )
                            )
                        }
                    }
                }
            }

            OfferClaimed(
                showDialog = showDialog.value,
                onDismiss = { showDialog.value = false }
            )
        }
    }
}

