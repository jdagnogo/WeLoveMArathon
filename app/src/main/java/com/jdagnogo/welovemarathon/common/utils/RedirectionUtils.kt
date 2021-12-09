package com.jdagnogo.welovemarathon.common.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.ui.platform.UriHandler
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf

fun redirectToLink(uriHandler: UriHandler, locationLink: String) {
    try {
        uriHandler.openUri(locationLink)
    } catch (e: ActivityNotFoundException) {
        Log.d("redirectToLink", "link not valid : $locationLink")
    }
}

fun redirectToPhone(context: Context, number: String) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel: $number")
    ContextCompat.startActivity(context, intent, bundleOf())
}
