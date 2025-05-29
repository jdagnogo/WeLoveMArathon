@file:OptIn(ExperimentalFoundationApi::class)

package com.jdagnogo.welovemarathon.about.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.component.ContactComponent
import com.jdagnogo.welovemarathon.common.utils.redirectToLink
import com.jdagnogo.welovemarathon.common.utils.redirectToMail
import com.jdagnogo.welovemarathon.common.utils.redirectToPhone

@Composable
fun AboutContent(
    state: AboutState,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current
    val currentImageIndex = rememberSaveable { mutableStateOf(0) }  // Add this line
    val totalImages = 10  // Add this line

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // TopBar with blue background and white logo
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1E4F7B))
                    .padding(vertical = 16.dp, horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_wlm_logo),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(48.dp),
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.White)
                    )

                    Spacer(modifier = Modifier.width(12.dp))  // Add some space between logo and text

                    Text(
                        text = "Marathon, Greece",
                        style = MaterialTheme.typography.subtitle1.copy(
                            color = Color.White
                        ),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
        // Header Section with Image Carousel
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(280.dp),
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                backgroundColor = MaterialTheme.colors.surface
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Background Image
                    Image(
                        painter = painterResource(
                            id = when(currentImageIndex.value) {
                                0 -> R.drawable.about1
                                1 -> R.drawable.about2
                                2 -> R.drawable.about3
                                3 -> R.drawable.about4
                                4 -> R.drawable.about5
                                5 -> R.drawable.about6
                                6 -> R.drawable.about7
                                7 -> R.drawable.about8
                                8 -> R.drawable.about9
                                else -> R.drawable.about10
                            }
                        ),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )

                    // Navigation Arrows
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Left Arrow
                        IconButton(
                            onClick = {
                                if (currentImageIndex.value > 0) {
                                    currentImageIndex.value--
                                }
                            },
                            modifier = Modifier
                                .background(
                                    color = Color.Black.copy(alpha = 0.3f),
                                    shape = CircleShape
                                )
                                .size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Previous",
                                tint = Color.White
                            )
                        }

                        // Right Arrow
                        IconButton(
                            onClick = {
                                if (currentImageIndex.value < totalImages - 1) {
                                    currentImageIndex.value++
                                }
                            },
                            modifier = Modifier
                                .background(
                                    color = Color.Black.copy(alpha = 0.3f),
                                    shape = CircleShape
                                )
                                .size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Next",
                                tint = Color.White
                            )
                        }
                    }

                    // Page Indicators
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(totalImages) { index ->
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .size(8.dp)
                                    .background(
                                        color = if (currentImageIndex.value == index)
                                            Color.White
                                        else
                                            Color.White.copy(alpha = 0.5f),
                                        shape = CircleShape
                                    )
                            )
                        }
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                backgroundColor = Color(0xFF1E4F7B)
            ) {
                val context = LocalContext.current  // Add this line to get the context

                Column(
                    modifier = Modifier
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Check out our other socials!",
                        style = MaterialTheme.typography.subtitle1,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Single Row for all items with equal spacing
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Phone Contact
                        ContactComponent(
                            modifier = Modifier,
                            icon = R.drawable.ic_phone,
                            tint = Color.White,
                            iconSize = 50.dp,
                            onClicked = { redirectToPhone(context, state.phone) }  // Use state.phone
                        )

                        // Email Contact
                        ContactComponent(
                            modifier = Modifier,
                            icon = R.drawable.email,
                            tint = Color.White,
                            iconSize = 50.dp,
                            onClicked = { redirectToMail(context, state.mail) }  // Use state.mail
                        )

                        // Social Media Icons
                        state.socialMedias.forEach { socialMedia ->
                            SocialComponent(
                                socialMedia = socialMedia,
                                modifier = Modifier
                            )
                        }
                    }
                }
            }
        }

        // About Description Section
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                backgroundColor = MaterialTheme.colors.surface
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "About Us",
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    AboutDescription()
                }
            }
        }

        // Team Section
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Our Team",
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                backgroundColor = MaterialTheme.colors.surface
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    FlowRow(
                        mainAxisSpacing = 16.dp,
                        crossAxisAlignment = FlowCrossAxisAlignment.Center,
                        crossAxisSpacing = 16.dp,
                        mainAxisAlignment = FlowMainAxisAlignment.Center,
                        modifier = Modifier.wrapContentWidth()
                    ) {
                        state.members.forEach { member ->
                            TeamComponent(member)
                        }
                    }
                }
            }
        }

        // Privacy Policy Section
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp),
                backgroundColor = MaterialTheme.colors.surface
            ) {
                Text(
                    text = "Privacy Policy",
                    textDecoration = TextDecoration.Underline,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.secondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .clickable {
                            redirectToLink(uriHandler = uriHandler, state.policy)
                        }
                )
            }
        }
    }
}

@Composable
private fun ContactButtons(phone: String, mail: String) {
    val context = LocalContext.current

    // Phone Contact
    ContactComponent(
        modifier = Modifier,
        icon = R.drawable.ic_phone,
        tint = Color.White,
        iconSize = 50.dp,
        onClicked = { redirectToPhone(context, phone) }
    )

    // Email Contact
    ContactComponent(
        modifier = Modifier,
        icon = R.drawable.email,
        tint = Color.White,
        iconSize = 50.dp,
        onClicked = { redirectToMail(context, mail) }
    )
}

@Preview
@Composable
fun AboutContentPreview() {
    AboutContent(
        state = AboutState()
    )
}