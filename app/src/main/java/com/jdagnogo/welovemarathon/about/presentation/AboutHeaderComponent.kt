package com.jdagnogo.welovemarathon.about.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.about.domain.SocialMedia
import com.jdagnogo.welovemarathon.common.ui.component.ContactComponent
import com.jdagnogo.welovemarathon.common.ui.theme.SubTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.TitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.emptyScreenSubTitle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.common.utils.redirectToLink
import com.jdagnogo.welovemarathon.common.utils.redirectToMail
import com.jdagnogo.welovemarathon.common.utils.redirectToPhone

@Composable
fun AboutHeaderComponent(
    socialMedias: List<SocialMedia>, modifier: Modifier = Modifier,
    mail: String = "",
    phone: String = ""
) {
    ConstraintLayout(
        modifier = modifier.animateContentSize()
    ) {
        val (image, logo, title, subtitle, row1, follow, tel, email) = createRefs()
        Image(
            painter = rememberImagePainter(
                data = R.drawable.bg_team,
                builder = {
                    crossfade(true)
                    error(R.drawable.ic_wlm_logo)
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(image) {
                    linkTo(parent.start, parent.end)
                    top.linkTo(parent.top)
                },
            contentScale = ContentScale.Crop,
        )

        Icon(
            painter = rememberImagePainter(data = R.drawable.ic_wlm_logo),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .constrainAs(logo) {
                    linkTo(parent.start, parent.end)
                    top.linkTo(parent.top, 30.dp)
                },
            tint = Color.White,
        )

        Text(
            text = "We love Marathon",
            style = TitleStyle,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(logo.bottom, 16.dp)
                linkTo(parent.start, parent.end)
            }
        )
        Text(
            text = "Marathon, Greece",
            style = SubTitleStyle,
            modifier = Modifier.constrainAs(subtitle) {
                top.linkTo(title.bottom, 4.dp)
                linkTo(parent.start, parent.end)
            }
        )
        if (socialMedias.isNotEmpty()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    MaterialTheme.spacing.medium,
                    Alignment.CenterHorizontally
                ),
                modifier = Modifier.constrainAs(row1) {
                    bottom.linkTo(parent.bottom, 16.dp)
                    linkTo(parent.start, parent.end)
                }
            ) {
                repeat(socialMedias.size) {
                    SocialComponent(socialMedia = socialMedias[it])
                }
            }
            Text(text = "Follow us ! ", style = emptyScreenSubTitle,
                modifier = Modifier.constrainAs(follow) {
                    linkTo(parent.start, parent.end)
                    bottom.linkTo(row1.top, 8.dp)
                })
        }
        val context = LocalContext.current
        val uriHandler = LocalUriHandler.current
        ContactComponent(
            modifier = Modifier
                .padding(MaterialTheme.spacing.medium)
                .constrainAs(tel) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start, 8.dp)
                },
            icon = R.drawable.ic_phone,
            tint = Color.White,
            iconSize = 28.dp,
            onClicked = { redirectToPhone(context, phone) },
        )

        ContactComponent(
            modifier = Modifier
                .padding(MaterialTheme.spacing.medium)
                .constrainAs(email) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end, 8.dp)
                },
            icon = R.drawable.email,
            tint = Color.White,
            iconSize = 28.dp,
            onClicked = { redirectToMail(context, mail) },
        )
    }
}

@Preview
@Composable
fun AboutHeaderComponentPreview() {
    val socialMedia = listOf(
        SocialMedia("0", "", 0),
        SocialMedia("1", "", 1),
    )
    AboutHeaderComponent(
        socialMedia
    )
}