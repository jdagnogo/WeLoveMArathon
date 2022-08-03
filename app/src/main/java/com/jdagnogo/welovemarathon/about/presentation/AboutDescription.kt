package com.jdagnogo.welovemarathon.about.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.common.ui.component.DescriptionItemFactory
import com.jdagnogo.welovemarathon.common.ui.component.ExpandableComponent
import com.jdagnogo.welovemarathon.common.ui.theme.emptyScreenSubTitle

@Composable
fun AboutDescription(
    modifier: Modifier = Modifier
) {
    AboutTitle(title = "About us")

    ExpandableComponent(
        modifier = modifier
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