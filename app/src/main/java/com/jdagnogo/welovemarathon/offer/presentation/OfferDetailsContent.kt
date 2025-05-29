package com.jdagnogo.welovemarathon.offer.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.category.FilterDialogButton
import com.jdagnogo.welovemarathon.common.ui.component.CarouselWithPreview
import com.jdagnogo.welovemarathon.common.ui.component.ContactComponent
import com.jdagnogo.welovemarathon.common.ui.theme.ActivityColor
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.SecondaryLight
import com.jdagnogo.welovemarathon.common.ui.theme.SubTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.TitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.contactStyle
import com.jdagnogo.welovemarathon.common.ui.theme.descriptionStyle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.common.utils.redirectToLink
import com.jdagnogo.welovemarathon.common.utils.redirectToMail
import com.jdagnogo.welovemarathon.restaurant.presentation.sections.amenitiesSection
import com.jdagnogo.welovemarathon.restaurant.presentation.sections.descriptionSection
import com.jdagnogo.welovemarathon.restaurant.presentation.sections.platesSection
import com.jdagnogo.welovemarathon.restaurant.presentation.sections.servicesSection

@Composable
fun OfferDetailsContent(
    modifier: Modifier,
    state: OfferViewModel.OfferState,
    onOfferActivationClicked: (String) -> Unit = {},
) {
    val offer = state.offer
    val restaurant = offer?.restaurant ?: return
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    Scaffold(
        modifier = modifier,
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                ContactComponent(
                    modifier = Modifier
                        .padding(MaterialTheme.spacing.medium),
                    icon = R.drawable.email,
                    tint = Secondary,
                    iconSize = 28.dp,
                    onClicked = { redirectToMail(context, "agapamemarathona@gmail.com") },
                )
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.offer_title),
                        style = TitleStyle,
                    )
                    Text(
                        text = offer.restaurant.name,
                        style = SubTitleStyle,
                    )
                }
                ContactComponent(
                    modifier = Modifier
                        .padding(MaterialTheme.spacing.medium),
                    icon = R.drawable.ic_location,
                    tint = Secondary,
                    iconSize = 28.dp,
                    onClicked = {
                        redirectToLink(
                            uriHandler,
                            offer.restaurant.locationLink
                        )
                    },
                )
            }
        },
    ) { paddingValues ->
        LazyColumn(
            contentPadding = PaddingValues(
                bottom = MaterialTheme.spacing.large,
            ),
            modifier = modifier
                .padding(paddingValues)
                .animateContentSize()
        ) {
            offerPromosSection(
                promos = offer.promos,
            )

            item {
                Text(
                    style = contactStyle,
                    text = offer.description,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(vertical = MaterialTheme.spacing.small)
                )
            }

            item {
                if (state.activationDate != null) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp)
                            .background(ActivityColor)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        text = "Your offer is Activated since " + state.activationDate,
                        textAlign = TextAlign.Center
                    )
                } else {
                    FilterDialogButton(
                        stringResource(id = R.string.offer_activate_button),
                        {
                            onOfferActivationClicked(offer.id)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(top = MaterialTheme.spacing.small),
                        color = Secondary,
                    )
                }
            }

            item {
                Text(
                    style = descriptionStyle,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = MaterialTheme.spacing.small),
                    text = stringResource(id = R.string.offer_activation_condition)
                )
            }

            item {
                FilterDialogButton(
                    stringResource(id = R.string.offer_email_button),
                    {
                        redirectToMail(context, "agapamemarathona@gmail.com")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = MaterialTheme.spacing.small),
                    color = Secondary,
                )
            }

            item {
                Text(
                    text = stringResource(id = R.string.offer_email_condition),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp)
                )
            }

            item {
                val images = remember { restaurant.images }
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    CarouselWithPreview(
                        urls = images,
                        shape = RoundedCornerShape(0),
                        bigImages = restaurant.bigImages,
                        modifier = Modifier.padding(bottom = MaterialTheme.spacing.medium)

                    )
                    if (restaurant.isRecommended) {
                        Icon(
                            painter = rememberImagePainter(
                                data = R.drawable.france,
                                builder = {
                                    crossfade(true)
                                    error(R.drawable.ic_wlm_logo)
                                }
                            ),
                            tint = SecondaryLight,
                            contentDescription = "icon",
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.TopStart)
                                .size(MaterialTheme.spacing.large)
                        )
                    }
                }
            }

            servicesSection(
                modifier = Modifier.padding(top = 16.dp),
                services = restaurant.services
            )

            descriptionSection(
                modifier = Modifier.padding(top = 16.dp),
                description = restaurant.description
            )

            platesSection(
                modifier = Modifier.padding(top = 32.dp),
                plates = restaurant.plates
            )

            amenitiesSection(
                Modifier
                    .padding(top = 32.dp),
                amenities = restaurant.amenities,
                menu = restaurant.menu,
                drinks = restaurant.drinks,
                cuisines = restaurant.cuisines
            )
        }
    }
}