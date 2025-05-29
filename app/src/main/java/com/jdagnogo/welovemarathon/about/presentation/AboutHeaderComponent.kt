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
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.SubTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.TitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.emptyScreenSubTitle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.common.utils.redirectToLink
import com.jdagnogo.welovemarathon.common.utils.redirectToMail
import com.jdagnogo.welovemarathon.common.utils.redirectToPhone

@Composable
fun AboutHeaderComponent(
    socialMedias: List<SocialMedia>,
    modifier: Modifier = Modifier,
    mail: String = "",
    phone: String = ""
) {
    ConstraintLayout(
        modifier = modifier.animateContentSize()
    ) {
        val (title, subtitle) = createRefs()


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