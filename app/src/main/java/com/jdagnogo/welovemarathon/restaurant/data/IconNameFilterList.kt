package com.jdagnogo.welovemarathon.restaurant.data

import androidx.annotation.Keep

import com.jdagnogo.welovemarathon.restaurant.domain.IconNameFilter

@Keep
data class IconNameFilterList(
    val iconNameFilter: List<IconNameFilter>
)