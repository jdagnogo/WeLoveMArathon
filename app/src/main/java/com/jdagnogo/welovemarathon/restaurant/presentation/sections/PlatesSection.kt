package com.jdagnogo.welovemarathon.restaurant.presentation.sections

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.common.ui.component.RoundedImageComponent
import com.jdagnogo.welovemarathon.common.ui.theme.ActivitySubTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.TagColor
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.restaurant.domain.Plates

fun LazyListScope.platesSection(
    modifier: Modifier = Modifier,
    plates: List<Plates> = emptyList(),
) {
    item("platesSection") {
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(
                start = MaterialTheme.spacing.medium,
                end = MaterialTheme.spacing.huge,
            ),
            horizontalArrangement = Arrangement.spacedBy(
                MaterialTheme.spacing.medium,
                Alignment.CenterHorizontally
            ),
            modifier = modifier
                .fillMaxWidth()
                .animateContentSize()
        ) {
            items(items = plates, key = { it.name }) { plate ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    RoundedImageComponent(
                        modifier = Modifier.size(90.dp),
                        imageModifier = Modifier.height(100.dp),
                        image = plate.image,
                        name = plate.name,
                        onImageClicked = {}
                    ) 
                    
                    Text(modifier = Modifier.width(90.dp).padding(top = 8.dp),
                        text = plate.name,
                        style = ActivitySubTitleStyle.copy(color = TagColor),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2)
                }
            }
        }
    }

}