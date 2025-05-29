package com.jdagnogo.welovemarathon.run

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.about.presentation.AboutTitle
import com.jdagnogo.welovemarathon.common.ui.component.ContactComponent
import com.jdagnogo.welovemarathon.common.ui.theme.emptyScreenSubTitle
import com.jdagnogo.welovemarathon.common.ui.theme.emptyScreenTitle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.common.utils.redirectToLink

private const val RUN_LINK =
    "https://www.athensauthenticmarathon.gr/site/index.php/en/registration-en"

@Composable
fun RunScreen() {
    val scroll = rememberScrollState(0)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scroll)
    ) {
        ConstraintLayout(
            modifier = Modifier.animateContentSize()
        ) {
            val uriHandler = LocalUriHandler.current
            val (image, icon) = createRefs()
            Image(
                painter = rememberImagePainter(
                    data = R.drawable.bg_run,
                    builder = {
                        crossfade(true)
                        error(R.drawable.ic_wlm_logo)
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(400.dp)
                    .constrainAs(image) {
                        linkTo(parent.start, parent.end)
                        top.linkTo(parent.top)
                    },
                contentScale = ContentScale.Crop,
            )
            ContactComponent(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.medium)
                    .constrainAs(icon) {
                        top.linkTo(image.bottom)
                        bottom.linkTo(image.bottom)
                        start.linkTo(parent.start, 16.dp)
                    },
                icon = R.drawable.ic_link,
                iconSize = 48.dp,
                backgroundColor = Color.White,
                onClicked = { redirectToLink(uriHandler, RUN_LINK) },
            )
        }

        Text(
            text = "Athens Marathon the Authentic",
            style = emptyScreenTitle,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Text(
            modifier = Modifier.padding(16.dp),
            style = emptyScreenSubTitle,
            text = "The Athens Classic Marathon The Authentic is an annual marathon road race held in Athens, Greece, normally in early November (the second Sunday of November), since 1972."
                    + "\n\nDistance course: 42.195 kilometers (26.2 mile) course\n" +
                    "Starting line: Marathon Stadium in Marathon city\n" +
                    "Finish line: Panathenaic Stadium in Athens"
                    + "\n\nThe marathon race and course is inspired by the Ancient Greek legend of Pheidippides, a messenger who is said to have run from Marathon to Athens to bring news of the Greek victory over the Persians at the Battle of Marathon."
                    + "\n\nTaking from the tradition of the Olympic Torch, the race features the Marathon Flame, which is lit at the Tomb of the Battle of Marathon and carried to the stadium in Marathon before the beginning of each race, every year.  "
                    + "\n\nIt is perhaps the most difficult major marathon race: the course is uphill from the 10 km mark to the 31 km mark – the toughest uphill climb of any major marathon.The course begins in the town of Marathon, where it passes the tomb of the Athenian soldiers, and it traces a path near the coast through Nea Makri. Following the steep rise, the course goes lightly downhill towards the city of Athens. It passes a statue of a runner in the city centre before finishing up at the Panathinaiko Stadium; a site for athletics competitions in ancient times and the finishing point for both the 1896 and 2004 Olympic marathons.",
            color = Color.White,
            textAlign = TextAlign.Start,
        )
    }
}