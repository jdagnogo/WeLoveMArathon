package com.jdagnogo.welovemarathon.common.ui.component


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryDark
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme

@Composable
fun TitleIconComponent(
    title: String = "",
    textStyle: TextStyle = MaterialTheme.typography.h6,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(modifier = modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .background(WeLoveMarathonTheme.colors.contentBackground)
        .padding(16.dp)
    ) {
        val (titleRef, imageRef) = createRefs()
        Text(
            text = title,
            maxLines = 1,
            color = PrimaryDark,
            textAlign = TextAlign.Start,
            style = textStyle,
            modifier = Modifier.constrainAs(titleRef) {
                linkTo(
                    start = parent.start,
                    end = imageRef.end,
                    16.dp,
                    endMargin = 8.dp,
                    bias = 0f
                )
                top.linkTo(imageRef.top)
                end.linkTo(imageRef.end)
            }
        )
        Image(
            painter = rememberImagePainter(R.drawable.ic_arrow_right),
            contentDescription = title,
            modifier = Modifier
                .size(24.dp)
                .constrainAs(imageRef) {
                    end.linkTo(parent.end, 16.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                })
    }
}

@Preview
@Composable
fun TitleComponentPreview() {
    MaterialTheme {
        TitleIconComponent(title = "Title")
    }
}
