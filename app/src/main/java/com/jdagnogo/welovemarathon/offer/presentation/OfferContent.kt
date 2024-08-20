package com.jdagnogo.welovemarathon.offer.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.ui.Scaffold
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.category.FilterDialogButton
import com.jdagnogo.welovemarathon.common.ui.component.DescriptionItemFactory
import com.jdagnogo.welovemarathon.common.ui.component.DividerComponent
import com.jdagnogo.welovemarathon.common.ui.component.ExpandableComponent
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.common.ui.theme.TagColor
import com.jdagnogo.welovemarathon.common.ui.theme.emptyScreenSubTitle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing

@Composable
fun OfferContent() {
    Scaffold(
        backgroundColor = Primary,
        topBar = {
            TitleComponent(
                iconLeft = null,
                title = stringResource(id = R.string.offer_title)
            )
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
                        //TODO : on click offer
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MaterialTheme.spacing.small),
                )
            }

        },
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues = paddingValues)) {
            Box(Modifier.size(200.dp)) {

            }

            Spacer(modifier = Modifier.padding(8.dp))

            ExpandableComponent(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp)
            ) { isExpanded ->
                val descriptionItem = DescriptionItemFactory(isExpanded).create()

                Text(
                    overflow = descriptionItem.overflow,
                    maxLines = descriptionItem.maxLines,
                    style = emptyScreenSubTitle,
                    text = "We are volunteers, the WLM Team and We Love Marathon!  Most of us live in the municipality of Marathon and the rest of us are Marathon lovers! We all work non-profit with love and passion with the aim of promoting and highlighting this magical place!\n" +
                            "\n" +
                            "The application is designed voluntarily to help Marathon visitors better organize their holidays in our city. Lots of recommendations for food, drink, beaches and activities in the area of Marathon with detailed information! If you think there is room for improvement (there always is) don't hesitate to email us with your suggestions! The application was launched this summer 2022 as a pilot.",
                    color = Color.White,
                )
            }


        }

    }
}