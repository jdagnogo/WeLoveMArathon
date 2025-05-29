package com.jdagnogo.welovemarathon.restaurant.presentation.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.SubTitleStyle
import com.jdagnogo.welovemarathon.restaurant.domain.Amenities
import com.jdagnogo.welovemarathon.restaurant.domain.AmenityIcons

fun LazyListScope.amenitiesSection(
    modifier: Modifier = Modifier,
    amenities: List<Amenities>,
    menu: List<String>,
    drinks: List<String>,
    cuisines: List<String> = emptyList()
) {
    if (amenities.isNotEmpty()) {
        item("Amenitie title") {
            Text(
                modifier = modifier.padding(horizontal = 16.dp),
                text = stringResource(id = R.string.amenitiesTitle),
                style = SubTitleStyle.copy(
                    fontSize = 18.sp,
                    color = Color.Black
                ),
            )
            Spacer(modifier = Modifier.padding(bottom = 16.dp))
        }

        items(
            addAmenities(amenities = amenities, menu = menu, drinks = drinks, cuisines = cuisines),
            key = { it.type }
        ) { amenity ->
            Card(
                backgroundColor = Color(0xFF1E4F7B).copy(alpha = 0.05f),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                elevation = 0.dp
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = rememberImagePainter(
                            data = amenity.icon,
                            builder = {
                                crossfade(true)
                                error(R.drawable.ic_wlm_logo)
                            }
                        ),
                        contentDescription = amenity.type,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(50.dp)
                            .padding(end = 12.dp)
                    )

                    Column {
                        Text(
                            text = amenity.type,
                            style = MaterialTheme.typography.subtitle1.copy(
                                color = Color.Black,
                                fontSize = 15.sp
                            )
                        )
                        Text(
                            text = amenity.description,
                            style = MaterialTheme.typography.body2.copy(
                                color = Color(0xFF1E4F7B).copy(alpha = 0.8f),
                                fontSize = 13.sp
                            ),
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

private fun getStaticIconForType(type: String): String {
    return when (type.lowercase()) {
        "view" -> AmenityIcons.VIEW
        "parking" -> AmenityIcons.PARKING
        "sweets" -> AmenityIcons.SWEETS
        "cuisine" -> AmenityIcons.CUISINE
        "in the menu" -> AmenityIcons.IN_THE_MENU
        "drinks" -> AmenityIcons.DRINKS
        "services" -> AmenityIcons.SERVICES
        "location" -> AmenityIcons.LOCATION
        "extras" -> "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/icons%2FNew%20Food%2Fextra1.png?alt=media&token=67bb429d-2fc5-4b9c-9957-3638c4b43272"
        else -> AmenityIcons.CUISINE
    }
}

private fun addAmenities(
    amenities: List<Amenities>,
    menu: List<String>,
    drinks: List<String>,
    cuisines: List<String> = emptyList()
): List<Amenities> {
    return buildList<Amenities> {
        addAll(amenities.map { amenity -> 
            amenity.copy(
                type = if (amenity.type.lowercase() == "cuisine") "Cuisine" else amenity.type,
                icon = getStaticIconForType(amenity.type)
            )
        })
        
        add(
            Amenities(
                type = "In the menu",
                icon = AmenityIcons.IN_THE_MENU,
                description = menu.joinToString(separator = ", "),
            )
        )
        add(
            Amenities(
                type = "Drinks",
                icon = AmenityIcons.DRINKS,
                description = drinks.joinToString(separator = ", "),
            )
        )
    }
}