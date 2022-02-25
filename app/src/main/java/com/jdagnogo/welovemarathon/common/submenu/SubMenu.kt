package com.jdagnogo.welovemarathon.common.submenu

import com.jdagnogo.welovemarathon.R

enum class SubMenuShopping(
    val subMenuItem: SubMenuItem
) {
    WOMAN(SubMenuItem("Woman", "Let's explore", R.drawable.sports, 0)),
    MAN(SubMenuItem("Man", "Dive into", R.drawable.culture, 1)),
    KID(SubMenuItem("Kid", "Find treasures", R.drawable.shopping, 2)),
    PET(SubMenuItem("Pet", "Therapy time", R.drawable.ic_wlm_logo, 3)),
    HOME(SubMenuItem("Home", "Yummy", R.drawable.ic_wlm_logo, 4)),
    JEWELRY(SubMenuItem("Jewelry", "Deserve it", R.drawable.ic_wlm_logo, 5)),
    OPTICAL(SubMenuItem("Optical", "Deserve it", R.drawable.ic_wlm_logo, 6)),
    SPORT(SubMenuItem("Sport", "Deserve it", R.drawable.ic_wlm_logo, 7)),
    TECHNOLOGY(SubMenuItem("Technology", "Deserve it", R.drawable.ic_wlm_logo, 8)),
    BIO(SubMenuItem("Bio", "Deserve it", R.drawable.ic_wlm_logo, 9)),
    FLOWERS(SubMenuItem("Flowers", "Deserve it", R.drawable.ic_wlm_logo, 10)),
    MARKET(SubMenuItem("Market", "Deserve it", R.drawable.ic_wlm_logo, 11)),
}

enum class SubMenuFood(
    val subMenuItem: SubMenuItem
) {
    ACTIVITIES(SubMenuItem("Activities", "Let's explore", R.drawable.sports, 0)),
}
