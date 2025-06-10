package com.jdagnogo.welovemarathon.offer.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jdagnogo.welovemarathon.R
import androidx.compose.foundation.background

@Composable
fun OfferEmptySection() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1E4F7B))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Offers",
                style = MaterialTheme.typography.h6.copy(
                    color = Color.White,
                    fontSize = 24.sp
                )
            )
        }


        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.noresults),
                    contentDescription = "No results found",
                    modifier = Modifier.size(170.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "No offers are available right now",
                    style = MaterialTheme.typography.h5.copy(
                        color = Color.Black,
                        fontSize = 20.sp
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}