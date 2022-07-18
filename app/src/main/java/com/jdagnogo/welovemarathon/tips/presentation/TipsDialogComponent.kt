package com.jdagnogo.welovemarathon.tips.presentation

import android.annotation.SuppressLint
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryLight
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.tips.domain.Tips

@Composable
fun TipsDialogComponent(
    item: Tips?,
    onDismissRequest: () -> Unit,
    modifier: Modifier,
) {
    if (item == null) return
    val optionsScrollState = rememberScrollState()
    Dialog(onDismissRequest = { onDismissRequest() }) {
        TipsDialogContent(
            item = item,
            modifier = modifier
                .heightIn(0.dp, 500.dp)
                .verticalScroll(
                    optionsScrollState
                )
        )
    }
}

@Composable
fun TipsDialogContent(
    item: Tips,
    modifier: Modifier,
) {
    Card(
        shape = RoundedCornerShape(10),
        elevation = 8.dp
    ) {
        Column(
            modifier
                .background(PrimaryLight)
        ) {

            if (item.image.isNotEmpty()) {
                Image(
                    painter = rememberImagePainter(
                        data = item.image,
                        builder = {
                            crossfade(true)
                            error(R.drawable.food)
                        }
                    ),
                    contentDescription = item.title,
                    modifier = Modifier
                        .padding(
                            bottom = MaterialTheme.spacing.medium,
                        )
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10, 10, 0, 0))
                        .height(150.dp),
                    contentScale = ContentScale.FillWidth
                )
            }
            TitleComponent(
                title = item.title,
                iconLeft = null,
                iconRight = null,
            )
            HtmlText(text = item.description)
        }
    }
}

@Composable
fun HtmlText(text: String) {
    AndroidView(factory = { context ->
        TextView(context).apply {
            setText(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY))
            movementMethod = LinkMovementMethod.getInstance()
            setLinkTextColor(ContextCompat.getColor(context, R.color.secondary))
        }
    }, modifier = Modifier.padding(MaterialTheme.spacing.medium))
}

@SuppressLint("UnrememberedMutableState")
@Preview(name = "Custom Dialog")
@Composable
fun TipsDialogContentPreview() {
    TipsDialogContent(
        item = Tips("", title = "title", description = "<b>hello</b> hello"),
        modifier = Modifier
    )
}