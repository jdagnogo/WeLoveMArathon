package com.jdagnogo.welovemarathon.about.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.jdagnogo.welovemarathon.about.domain.SocialMedia

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