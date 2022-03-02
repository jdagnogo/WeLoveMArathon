package com.jdagnogo.welovemarathon.common.ui.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.TitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing

@Composable
fun TitleComponent(
    iconLeft: Int = R.drawable.ic_wlm_logo,
    onLeftIconClicked: () -> Unit = {},
    iconRight: Int = R.drawable.location,
    onRightIconClicked: () -> Unit = {},
    title: String,
    modifier: Modifier = Modifier
) {
    val iconSize = MaterialTheme.spacing.semiHuge
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = MaterialTheme.spacing.medium)
            .padding(horizontal = MaterialTheme.spacing.huge)
    ) {
        Icon(
            painterResource(id = iconLeft),
            contentDescription = "back",
            tint = Color.White,
            modifier = modifier
                .clickable {
                    onLeftIconClicked()
                }
                .height(iconSize)
                .wrapContentWidth(Alignment.Start)
                .background(Color(R.color.black), CircleShape)
        )
        Text(
            text = title,
            style = TitleStyle,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
        Icon(
            painterResource(id = iconRight),
            contentDescription = "map",
            tint = Color.White,
            modifier = modifier
                .clickable {
                    onRightIconClicked()
                }
                .height(iconSize)
                .size(iconSize)
                .wrapContentWidth(Alignment.End)
        )
    }
}

@ExperimentalAnimationApi
@Preview(name = "Align right", showBackground = true)
@Composable
fun RunTitleRightComponentPreview() {
    MaterialTheme {
        TitleComponent(
            R.drawable.ic_wlm_logo,
            {},
            R.drawable.ic_wlm_logo,
            {},
            "Title"
        )
    }
}
