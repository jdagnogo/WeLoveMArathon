package com.jdagnogo.welovemarathon.common.ui.component

import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat

import com.jdagnogo.welovemarathon.R

/**
 * Renders HTML (bold/italic/underline/lists/links - e.g. a Google Calendar
 * event description, which the Calendar API returns as HTML) via a hosted
 * TextView, since Compose's Text has no built-in HTML support.
 *
 * @param maxLines pass a finite value to truncate with an ellipsis (e.g. for
 * a collapsed preview); [onOverflowDetected] then reports whether the text
 * actually got cut off, so callers can conditionally show a "See more" affordance
 * (see events/presentation/EventListItem.kt's DescriptionSection for the pairing
 * with ExpandableComponent).
 */
@Composable
fun HtmlTextComponent(
    text: String,
    textAlignment: Int = View.TEXT_ALIGNMENT_TEXT_START,
    style: Int = R.style.htmlText,
    maxLines: Int = Int.MAX_VALUE,
    onOverflowDetected: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    AndroidView(
        factory = { context ->
            TextView(context).apply {
                setTextAlignment(textAlignment)
                setTextAppearance(context, style)
                movementMethod = LinkMovementMethod.getInstance()
                setLinkTextColor(ContextCompat.getColor(context, R.color.secondary))
            }
        },
        update = { textView ->
            val spanned = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY).trimBlankLines()
            textView.text = spanned
            textView.maxLines = maxLines
            textView.ellipsize = if (maxLines != Int.MAX_VALUE) TextUtils.TruncateAt.END else null

            if (maxLines == Int.MAX_VALUE) {
                onOverflowDetected(false)
            } else {
                textView.post {
                    // Deliberately NOT using layout.getEllipsisCount(lastLine): Google
                    // Calendar's HTML wraps each line in its own block (<div>), which
                    // HtmlCompat turns into blank spacer lines between them. If a blank
                    // line lands on the last visible line, Android reports 0 ellipsis
                    // there even though real content further down got dropped entirely.
                    // Comparing the FULL text's required line count against maxLines
                    // sidesteps that - it's not fooled by where exactly the cut falls.
                    val width = textView.width - textView.paddingLeft - textView.paddingRight
                    if (width > 0) {
                        val fullLineCount = measureLineCount(spanned, textView, width)
                        onOverflowDetected(fullLineCount > maxLines)
                    }
                }
            }
        },
        modifier = modifier,
    )
}

/**
 * Strips leading/trailing blank lines. Google Calendar's HTML wraps each
 * source line in its own `<div>`, and a blank one at the start/end of the
 * description (an empty `<div><br></div>`) turns into dead vertical space
 * above or below the visible text once HtmlCompat renders it.
 */
private fun CharSequence.trimBlankLines(): CharSequence {
    var start = 0
    while (start < length && (this[start] == '\n' || this[start].isWhitespace())) start++
    var end = length
    while (end > start && (this[end - 1] == '\n' || this[end - 1].isWhitespace())) end--
    return subSequence(start, end)
}

@Suppress("DEPRECATION")
private fun measureLineCount(text: CharSequence, textView: TextView, width: Int): Int {
    val layout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        StaticLayout.Builder.obtain(text, 0, text.length, textView.paint, width).build()
    } else {
        StaticLayout(text, textView.paint, width, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false)
    }
    return layout.lineCount
}
