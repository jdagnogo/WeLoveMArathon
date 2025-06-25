package com.jdagnogo.welovemarathon.offer.presentation

import android.net.Uri
import android.widget.VideoView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jdagnogo.welovemarathon.R

@Composable
fun OfferClaimed(
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    AnimatedVisibility(
        visible = showDialog,
        enter = fadeIn(animationSpec = tween(400)) +
                slideInVertically(
                    animationSpec = tween(500),
                    initialOffsetY = { it / 2 }
                ),
        exit = fadeOut(animationSpec = tween(400)) +
                slideOutVertically(
                    animationSpec = tween(500),
                    targetOffsetY = { it / 2 }
                )
    ) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(300.dp),
                shape = RoundedCornerShape(20.dp),
                elevation = 10.dp,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(70.dp)
                                .padding(top = 2.dp),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            AndroidView(
                                factory = { context ->
                                    VideoView(context).apply {
                                        setZOrderOnTop(true)
                                        holder.setFormat(android.graphics.PixelFormat.TRANSPARENT)
                                        setVideoURI(Uri.parse("android.resource://${context.packageName}/${R.raw.animation_check4}"))
                                        setOnPreparedListener { mediaPlayer ->
                                            mediaPlayer.isLooping = false
                                            mediaPlayer.setVolume(0f, 0f)
                                            start()
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .size(60.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Offer claimed successfully!",
                            style = MaterialTheme.typography.h4,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black,
                            fontSize = 28.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        Spacer(modifier = Modifier.height(54.dp))

                        Button(
                            onClick = onDismiss,
                            modifier = Modifier
                                .width(250.dp)
                                .height(45.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFF1E4F7B)
                            )
                        ) {
                            Text(
                                text = "Close",
                                color = Color.White,
                                style = MaterialTheme.typography.h5.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 18.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
} 