package com.jdagnogo.welovemarathon.about.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.about.domain.SocialMedia
import com.jdagnogo.welovemarathon.common.ui.theme.SubTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.TitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import kotlin.math.log

@Composable
fun AboutHeaderComponent(socialMedias: List<SocialMedia>, modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier.animateContentSize()
    ) {
        val (image, logo, title, subtitle,row1, row2) = createRefs()
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

        Image(
            painter = rememberImagePainter(data = R.drawable.ic_wlm_logo),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.White)
                .constrainAs(logo) {
                    linkTo(parent.start, parent.end)
                    top.linkTo(parent.top, 30.dp)
                },
            contentScale = ContentScale.Crop,
        )

        Text(
            text = "We loves Marathon",
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

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                MaterialTheme.spacing.medium,
                Alignment.CenterHorizontally
            ),
            modifier = Modifier.constrainAs(row1){
                bottom.linkTo(row2.top, 8.dp)
                linkTo(parent.start, parent.end)
            }
        ) {
            repeat(3) {
                SocialComponent(socialMedia = socialMedias[it])
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                MaterialTheme.spacing.medium,
                Alignment.CenterHorizontally
            ),
            modifier = Modifier.constrainAs(row2){
                bottom.linkTo(parent.bottom, 16.dp)
                linkTo(parent.start, parent.end)
            }
        ) {
            repeat(2) {
                SocialComponent(socialMedia = socialMedias[it + 3])
            }
        }
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