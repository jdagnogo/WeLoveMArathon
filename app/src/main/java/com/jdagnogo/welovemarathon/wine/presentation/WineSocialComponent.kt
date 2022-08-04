package com.jdagnogo.welovemarathon.wine.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.about.presentation.SocialComponent
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.SubTitleStyle
import com.jdagnogo.welovemarathon.common.utils.redirectToMail
import com.jdagnogo.welovemarathon.common.utils.redirectToPhone
import com.jdagnogo.welovemarathon.wine.domain.WineSocial

@Composable
fun SocialMediaComponent(
    wineSocial: List<WineSocial>,
    modifier: Modifier = Modifier,
) {
    if (wineSocial.isEmpty()) return
    val context = LocalContext.current
    Text(
        color = Secondary,
        style = SubTitleStyle,
        text = "Contact us :",
        modifier = Modifier.padding(top = 24.dp)
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        repeat(3) { index ->
            SocialComponent(
                socialMedia = wineSocial[index].toSocialMedia(),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(vertical = 16.dp)
            )
        }
        Image(
            painter = rememberImagePainter(data = R.drawable.ic_mail),
            contentDescription = null,
            modifier = modifier
                .padding(8.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false),
                ) {
                    redirectToMail(context, "zeginiswinery@gmail.com")
                }
                .size(50.dp)
        )
        Image(
            painter = rememberImagePainter(data = R.drawable.ic_tel),
            contentDescription = null,
            modifier = modifier
                .padding(8.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false),
                ) {
                    redirectToPhone(context, "+306982081914")
                }
                .size(50.dp)
        )
    }

    Text(
        color = Secondary,
        style = SubTitleStyle,
        text = "Book a tour here :",
        modifier = Modifier.padding(top = 8.dp)
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        repeat(2) { index ->
            SocialComponent(
                socialMedia = wineSocial[index + 3].toSocialMedia(),
                modifier = Modifier.padding(16.dp)
            )
        }
    }

}