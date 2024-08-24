package com.jdagnogo.welovemarathon.common.category

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryDark
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryLight
import com.jdagnogo.welovemarathon.common.ui.theme.RecommendedCategoryItemTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.TagColor
import com.jdagnogo.welovemarathon.common.ui.theme.White
import com.jdagnogo.welovemarathon.common.ui.theme.filterButtonStyle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.common.ui.theme.tagStyle

@Composable
fun FilterDialogComponent(
    tags: List<CategoryTag>,
    onFiltersSelected: (ids: List<String>) -> Unit = {},
    onResetSelected: () -> Unit = {},
    onDismissRequest: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        FilterDetailsDialogContent(
            tags = tags,
            onFiltersSelected = onFiltersSelected,
            onResetSelected = onResetSelected,
        )
    }
}

@Composable
fun FilterDetailsDialogContent(
    tags: List<CategoryTag>,
    onFiltersSelected: (ids: List<String>) -> Unit = {},
    onResetSelected: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(10),
        elevation = 8.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.background(PrimaryLight)
        ) {
            Text(
                style = RecommendedCategoryItemTitleStyle,
                text = "Select your tags",
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.medium)
            )
            FlowRow(
                modifier = Modifier
                    .padding(vertical = MaterialTheme.spacing.medium)
                    .padding(horizontal = MaterialTheme.spacing.small),
                mainAxisAlignment = MainAxisAlignment.Center,
                mainAxisSize = SizeMode.Expand,
                crossAxisSpacing = MaterialTheme.spacing.extraSmall,
                mainAxisSpacing = MaterialTheme.spacing.small
            ) {
                tags.forEach { tag ->
                    var isSelected by remember { mutableStateOf(tag.isSelected) }
                    FilterItem(tag = tag.name, isSelected = isSelected,
                        onTagSelected = {
                            isSelected = isSelected.not()
                            tags.find { it.name == tag.name }?.isSelected = isSelected
                        })
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = MaterialTheme.spacing.medium)
                    .padding(horizontal = MaterialTheme.spacing.small)
            ) {
                FilterDialogButton("Done ! ", {
                    onFiltersSelected(
                        tags.filter { it.isSelected }.map { it.name }
                    )
                }, Modifier.weight(1f))
                FilterDialogButton("Reset", {
                    onResetSelected()
                }, Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun FilterDialogButton(
    title: String,
    onClicked: () -> Unit = {},
    modifier: Modifier = Modifier,
    color: Color = TagColor,
) {
    Button(
        modifier = modifier.padding(
            start = MaterialTheme.spacing.small,
            end = MaterialTheme.spacing.small
        ),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            backgroundColor = color
        ),
        onClick = {
            onClicked()
        },
    ) {
        Text(text = title, style = filterButtonStyle)
    }
}

@Composable
fun FilterItem(
    tag: String,
    isSelected: Boolean = false,
    onTagSelected: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Text(
        style = tagStyle,
        text = "#$tag",
        color = PrimaryDark.takeIf { isSelected } ?: White,
        modifier = modifier
            .clickable {
                onTagSelected()
            }
            .background(
                color = Secondary.takeIf { isSelected } ?: PrimaryDark,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(8.dp),
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}

@SuppressLint("UnrememberedMutableState")
@Preview(name = "Filter tag item")
@Composable
fun FilterDetailsDialogContentComponentPreview() {
    FilterItem(
        tag = CategoryTagFake.name
    )
}

@SuppressLint("UnrememberedMutableState")
@Preview(name = "Filter tag item selected")
@Composable
fun FilterDetailsDialogSelectedContentComponentPreview() {
    FilterItem(
        tag = CategoryTag("name", true).name,
        isSelected = true
    )
}

@SuppressLint("UnrememberedMutableState")
@Preview(name = "Button")
@Composable
fun FilterDialogButtonPreview() {
    FilterDialogButton(
        title = "Done"
    )
}

@Preview(name = "Filter dialog")
@Composable
fun FilterDialogComponentPreview() {
    FilterDetailsDialogContent(
        tags =
        listOf(
            CategoryTag("name", true),
            CategoryTag("long long long name", false),
            CategoryTag("name2", false),
            CategoryTag("name2", true),
            CategoryTag("name2", false),
        )
    )
}
