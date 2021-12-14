package com.jdagnogo.welovemarathon.common.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.R

@Keep
data class RightMenuData(
    val name: String = "",
    val icon: String = "",
    val iconRes: Int = 0,
) {
    fun getIcon(): Any {
        return icon.takeIf { icon.isNotEmpty() } ?: iconRes
    }
}

fun RightMenuData.toFakeList(): List<RightMenuData> {
    return listOf(
        RightMenuData("toto", "icon", R.drawable.ic_wlm_logo),
        RightMenuData("titi", "icon", R.drawable.ic_wlm_logo),
        RightMenuData("tata", "icon", R.drawable.ic_wlm_logo)
    )
}
