package com.jdagnogo.welovemarathon.restaurant.presentation.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.restaurant.domain.RestaurantService
import com.jdagnogo.welovemarathon.restaurant.domain.ServiceIcons

fun LazyListScope.servicesSection(
    modifier: Modifier = Modifier,
    services: List<RestaurantService>
) {
    item("servicesSection") {
        Card(
            backgroundColor = Color(0xFF1E4F7B).copy(alpha = 0.05f),
            shape = MaterialTheme.shapes.medium,
            modifier = modifier
                .wrapContentSize()
                .padding(horizontal = 16.dp),
            elevation = 0.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    services.forEach { service ->
                        ServiceItem(
                            service = service,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ServiceItem(
    service: RestaurantService,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(horizontal = 4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Image(
                painter = rememberImagePainter(
                    data = getStaticIconForService(service.title),
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = service.title,
                modifier = Modifier.size(49.dp),
                contentScale = ContentScale.Fit,
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = service.title,
                style = MaterialTheme.typography.subtitle1.copy(
                    fontSize = 15.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            )
            
            Text(
                text = service.description,
                style = MaterialTheme.typography.body2.copy(
                    fontSize = 13.sp,
                    color = Color(0xFF1E4F7B).copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(top = 2.dp)
            )
        }

    }
}

private fun getStaticIconForService(title: String): String {
    return when (title.lowercase()) {
        "services" -> ServiceIcons.SERVICES
        "cuisine" -> ServiceIcons.CUISINE
        "location" -> ServiceIcons.LOCATION
        else -> ServiceIcons.SERVICES
    }
}