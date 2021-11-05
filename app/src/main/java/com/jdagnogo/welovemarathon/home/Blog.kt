package com.jdagnogo.welovemarathon.home

import java.util.*


data class Blog(
    val id: String = "",
    val title: String = "",
    val summary: String = "",
    val author: String = "",
    val link: String = "",
    val date: Date = Calendar.getInstance().time,
)
