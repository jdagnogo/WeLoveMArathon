package com.jdagnogo.welovemarathon.about.domain

import androidx.annotation.Keep

@Keep
data class Members(
    var member: List<Member>
)