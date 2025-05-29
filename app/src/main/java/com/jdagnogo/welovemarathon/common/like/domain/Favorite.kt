package com.jdagnogo.welovemarathon.common.like.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.category.CategoryItem

@Keep
data class Favorite(
    val id: String = "",
    val name: String = "",
    val locationLink: String = "",
    val number: String = "",
    val parentIcon: Int = R.drawable.ic_wlm_logo,
    val tags: String = "",
)