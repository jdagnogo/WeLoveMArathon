package com.jdagnogo.welovemarathon.restaurant.presentation.filter

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.SubTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.White

@Composable
fun FilterBooleanComponent(
    modifier: Modifier = Modifier,
    title: Int = R.string.access,
    icon: Int = R.drawable.access,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Text(
        modifier = modifier,
        text = stringResource(id = title),
        style = SubTitleStyle.copy(fontSize = 18.sp),
    )

    Spacer(modifier = Modifier.padding(4.dp))
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = rememberImagePainter(
                data = icon,
                builder = {
                    crossfade(true)
                    error(R.drawable.ic_wlm_logo)
                }
            ),
            contentDescription = "disable people icon",
            tint = White,
            modifier = modifier.size(56.dp)

        )
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )

    }
}