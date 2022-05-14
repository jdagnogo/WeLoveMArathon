package com.jdagnogo.welovemarathon.common.submenu

data class SubMenuInteractions(
    val onItemSelected: (Int) -> Unit = {},
    val onMapSelected: () -> Unit,
    val onBackPressed: () -> Unit,
)
