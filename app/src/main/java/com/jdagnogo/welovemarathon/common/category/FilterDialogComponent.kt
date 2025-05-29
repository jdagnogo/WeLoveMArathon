package com.jdagnogo.welovemarathon.common.category

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.border
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
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.indication
import androidx.compose.material.ripple.rememberRipple

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
            modifier = modifier.background(Color.White)
        ) {
            Text(
                style = RecommendedCategoryItemTitleStyle,
                text = "Select your tags",
                fontSize = 22.sp,
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.medium)
            )
            FlowRow(
                modifier = Modifier
//                    .padding(vertical = MaterialTheme.spacing.medium)
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
                val blue = Color(0xFF1E4F7B)
                FilterDialogButton("Reset", {
                    onResetSelected()
                }, Modifier.weight(1f), color = blue)
                FilterDialogButton("Done", {
                    onFiltersSelected(
                        tags.filter { it.isSelected }.map { it.name }
                    )
                }, Modifier.weight(1f), color = blue)
            }
        }
    }
}

@Composable
fun FilterDialogButton(
    title: String,
    onClicked: () -> Unit = {},
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF1E4F7B),
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
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text = title, style = filterButtonStyle, color = Color.White)
    }
}

@Composable
fun FilterItem(
    tag: String,
    isSelected: Boolean = false,
    onTagSelected: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val blue = Color(0xFF1E4F7B)
    val interactionSource = remember { MutableInteractionSource() }
    Text(
        text = "#$tag",
        color = if (isSelected) Color.White else Color.Black,
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = true,
                    color = Color.Gray,
                    radius = 24.dp,
                )
            ) { onTagSelected() }
            .background(
                color = if (isSelected) blue else Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 2.dp,
                color = blue,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
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
