package com.jdagnogo.welovemarathon.home.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.home.domain.Blog
import com.jdagnogo.welovemarathon.home.domain.fakeList

@Composable
fun BlogItem(
    blog: Blog,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = 20.dp,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
            .fillMaxWidth()
            .height(150.dp)
            .clickable {

            }
    ) {
        ConstraintLayout(modifier = modifier
            .fillMaxSize()
            .animateContentSize()) {
            val (image, title, author, date, summary) = createRefs()
            val guideline = createGuidelineFromStart(0.4f)
            Image(
                painter = rememberImagePainter(
                    data = blog.image,
                    builder = {
                        crossfade(true)
                        error(R.drawable.ic_wlm_logo)
                    }
                ),
                contentDescription = blog.title,
                modifier = Modifier.constrainAs(image) {
                    linkTo(parent.start, guideline)
                    linkTo(parent.top, parent.bottom)
                    width = Dimension.fillToConstraints
                },
                contentScale = ContentScale.FillHeight,

                )

            Text(
                text = blog.author,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.constrainAs(author) {
                    top.linkTo(parent.top, 16.dp)
                    start.linkTo(image.end, 8.dp)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            )

            Text(
                text = blog.date,
                style = MaterialTheme.typography.overline,
                modifier = Modifier.constrainAs(date) {
                    top.linkTo(author.bottom)
                    start.linkTo(author.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            )

            Text(
                text = blog.title,
                maxLines = 1,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(date.bottom, 8.dp)
                    start.linkTo(author.start)
                    end.linkTo(parent.end, 4.dp)
                    width = Dimension.fillToConstraints
                }
            )

            Text(
                text = blog.summary,
                maxLines = 2,
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .constrainAs(summary) {
                        linkTo(
                            start = author.start,
                            end = parent.end,
                            endMargin = 4.dp,
                            bias = 0f
                        )
                        top.linkTo(title.bottom, 8.dp)
                        bottom.linkTo(parent.bottom, 16.dp)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
            )
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun BlogItemPreview() {
    MaterialTheme {
        BlogItem(Blog().fakeList().first())
    }
}
