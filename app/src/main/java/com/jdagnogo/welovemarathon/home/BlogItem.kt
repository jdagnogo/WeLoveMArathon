package com.jdagnogo.welovemarathon.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R

@Composable
fun BlogItem(
    blog: Blog,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(end = 8.dp)
            .fillMaxWidth()
            .height(200.dp)
            .padding(bottom = 16.dp)
            .clickable {

            }
    ) {
        ConstraintLayout(modifier = modifier.fillMaxSize()) {
            val (image, title, author, date, summary) = createRefs()
            Image(
                painter = rememberImagePainter(
                    data = blog.link,
                    builder = {
                        crossfade(true)
                        placeholder(drawableResId = R.drawable.ic_wlm_logo)
                        error(R.drawable.ic_wlm_logo)
                    }
                ),
                contentDescription = blog.title,
                modifier = Modifier.constrainAs(image){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    width
                }
            )

            Text(
                text = blog.author,
                style = MaterialTheme.typography.overline,
                modifier = Modifier.constrainAs(author){
                    top.linkTo(parent.top, 16.dp)
                    start.linkTo(image.end)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            )

            Text(
                text = blog.date,
                style = MaterialTheme.typography.overline,
                modifier = Modifier.constrainAs(date){
                    top.linkTo(author.bottom)
                    start.linkTo(image.end)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            )

            Text(
                text = blog.title,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.constrainAs(title){
                    top.linkTo(date.bottom, 16.dp)
                    start.linkTo(image.end)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            )

            Text(
                text = blog.summary,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.constrainAs(summary){
                    top.linkTo(title.bottom, 8.dp)
                    start.linkTo(title.start)
                    end.linkTo(parent.end, 8.dp)
                    width = Dimension.fillToConstraints
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
