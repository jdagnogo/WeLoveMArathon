package com.jdagnogo.welovemarathon.restaurant.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.category.CategoryItem
import com.jdagnogo.welovemarathon.common.category.RecommendedCategoryDetailsFake
import com.jdagnogo.welovemarathon.common.ui.component.CarouselWithPreview
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.food.domain.FoodCategory
import com.jdagnogo.welovemarathon.restaurant.domain.Amenities
import com.jdagnogo.welovemarathon.restaurant.domain.Plates
import com.jdagnogo.welovemarathon.restaurant.domain.Restaurant
import com.jdagnogo.welovemarathon.restaurant.domain.RestaurantService
import com.jdagnogo.welovemarathon.restaurant.presentation.sections.BottomBarSection
import com.jdagnogo.welovemarathon.restaurant.presentation.sections.amenitiesSection
import com.jdagnogo.welovemarathon.restaurant.presentation.sections.descriptionSection
import com.jdagnogo.welovemarathon.restaurant.presentation.sections.mapSection
import com.jdagnogo.welovemarathon.restaurant.presentation.sections.nameAndLinkSection
import com.jdagnogo.welovemarathon.restaurant.presentation.sections.platesSection
import com.jdagnogo.welovemarathon.restaurant.presentation.sections.servicesSection

@Composable
fun RestaurantDetailsContent(
    modifier: Modifier = Modifier,
    state: RestaurantState,
    onBackPressed: () -> Unit = {},
    onLikeClicked: (String) -> Unit = {},
    onMapSelected: () -> Unit = {},
) {
    val restaurant = state.currentRestaurantSelected ?: return
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            BottomBarSection(
                restaurant = restaurant,
                context = context,
                uriHandler = uriHandler
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = PaddingValues(
                bottom = MaterialTheme.spacing.large,
            ),
            modifier = modifier
                .padding(paddingValues)
                .animateContentSize()
        ) {
            item {
                TitleComponent(
                    title = stringResource(id = R.string.food),
                    onLeftIconClicked = onBackPressed,
                    onRightIconClicked = onMapSelected
                )
            }

            item {
                val images = remember { restaurant.images }
                CarouselWithPreview(
                    urls = images,
                    shape = RoundedCornerShape(0),
                    bigImages = restaurant.bigImages,
                    modifier = Modifier.padding(vertical = MaterialTheme.spacing.medium)
                )
            }

            nameAndLinkSection(
                modifier = Modifier,
                website = restaurant.website,
                name = restaurant.name,
                uriHandler = uriHandler
            )

            servicesSection(
                modifier = Modifier.padding(top = 16.dp),
                services = restaurant.services
            )

            descriptionSection(
                modifier = Modifier.padding(top = 16.dp),
                description = restaurant.description
            )

            platesSection(
                modifier = Modifier.padding(top = 32.dp),
                plates = restaurant.plates
            )

            amenitiesSection(
                Modifier
                    .padding(top = 32.dp),
                amenities = restaurant.amenities
            )

            mapSection(
                Modifier.height(300.dp).fillMaxWidth()
                    .padding(top = 32.dp),
                name = restaurant.name,
                coordinate = restaurant.coordinate
            )

        }
    }
}

@Preview
@Composable
private fun RestaurantDetailsContentPreview() {
    MaterialTheme {
        val itemSelected = FoodCategory(
            name = "Ferdinand Huff",
            icon = "litora",
            ordinal = 3
        )
        val state = RestaurantState(
            currentCategorySelected = itemSelected,
            categories = FoodCategory().toFakeFoodCategoryList().plus(itemSelected),
            foods = CategoryItem().toFakeCategoryItemList(),
            recommendedItems = listOf(
                RecommendedCategoryDetailsFake,
                RecommendedCategoryDetailsFake.copy(id = "2"),
                RecommendedCategoryDetailsFake.copy(id = "3"),
            ),
            currentRestaurantSelected = Restaurant(
                id = "id",
                name = "Fake restaurant",
                website = "website",
                location = RecommendedCategoryDetailsFake.location,
                locationLink = RecommendedCategoryDetailsFake.locationLink,
                number = RecommendedCategoryDetailsFake.number,
                description = RecommendedCategoryDetailsFake.description,
                isRecommended = false,
                services = listOf(
                    RestaurantService(
                        title = "alterum", icon = "simul", description = "sagittis"
                    ),
                    RestaurantService(
                        title = "facilisis",
                        icon = "nostra",
                        description = "moderatius"
                    ),
                    RestaurantService(
                        title = "senserit",
                        icon = "potenti",
                        description = "litora"
                    )
                ),
                plates = listOf(
                    Plates(
                        name = "Virginia Solis",
                        image = RecommendedCategoryDetailsFake.images.last(),
                        bigImage = RecommendedCategoryDetailsFake.bigImages.last()
                    )
                ),
                amenities = listOf(
                    Amenities(type = "ultricies", "titi"),
                    Amenities(type = "ultricies", "titi"),
                    Amenities(type = "ultricies", "titi"),
                    Amenities(type = "ultricies", "titi"),
                    Amenities(type = "ultricies", "titi"),
                    Amenities(type = "ultricies", "titi"),
                    Amenities(type = "ultricies", "titi"),
                    Amenities(type = "ultricies", "titi"),
                ),
                images = RecommendedCategoryDetailsFake.images,
                bigImages = RecommendedCategoryDetailsFake.bigImages

            )

        )
        RestaurantDetailsContent(state = state)
    }
}