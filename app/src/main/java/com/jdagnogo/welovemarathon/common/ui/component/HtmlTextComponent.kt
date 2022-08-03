package com.jdagnogo.welovemarathon.common.ui.component

import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.jdagnogo.welovemarathon.R

@Composable
fun HtmlTextComponent(
    text: String,
    textAlignment: Int = View.TEXT_ALIGNMENT_TEXT_START,
    style: Int = R.style.htmlText,
    modifier: Modifier = Modifier,
) {
    AndroidView(factory = { context ->
        TextView(context).apply {
            setText(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY))
            setTextAlignment(textAlignment)
            setTextAppearance(context, style)
            movementMethod = LinkMovementMethod.getInstance()
            setLinkTextColor(ContextCompat.getColor(context, R.color.secondary))
        }
    }, modifier = modifier)
}