package com.jdagnogo.welovemarathon.restaurant.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.category.CategoryItem
import com.jdagnogo.welovemarathon.common.category.RecommendedCategoryDetailsFake
import com.jdagnogo.welovemarathon.common.ui.component.CarouselWithPreview
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent
import com.jdagnogo.welovemarathon.common.ui.theme.SecondaryLight
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
) {
    val restaurant = state.currentRestaurantSelected ?: return
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    var isFav = remember {
        mutableStateOf(
            state.currentRestaurantSelected.isFavItem
        )
    }

    Scaffold(
        topBar = {
            TitleComponent(
                title = stringResource(id = R.string.food),
                iconRight = if (isFav.value) R.drawable.fav else R.drawable.ic_fav_unselected,
                onLeftIconClicked = onBackPressed,
                onRightIconClicked = {
                    onLikeClicked(restaurant.id)
                    isFav.value = !isFav.value
                }
            )
        },
        bottomBar = {
            BottomBarSection(
                restaurant = restaurant,
                context = context,
                uriHandler = uriHandler
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = PaddingValues(0.dp),
            modifier = modifier
                .padding(paddingValues)
                .animateContentSize()
        ) {
            item {
                val images = remember { restaurant.images }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    CarouselWithPreview(
                        urls = images,
                        shape = RoundedCornerShape(0),
                        bigImages = restaurant.bigImages,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                    )
                    if (restaurant.isRecommended) {
                        Icon(
                            painter = rememberImagePainter(
                                data = R.drawable.france ,
                                builder = {
                                    crossfade(true)
                                    error(R.drawable.ic_wlm_logo)
                                }
                            ),
                            tint = SecondaryLight,
                            contentDescription = "icon",
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.TopStart)
                                .size(MaterialTheme.spacing.large)
                        )
                    }
                }
            }

            nameAndLinkSection(
                modifier = Modifier,
                website = restaurant.website,
                name = restaurant.name,
                uriHandler = uriHandler
            )

            if (restaurant.isRecommended) {
                descriptionSection(
                    modifier = Modifier.padding(top = 0.dp, bottom = 0.dp),
                    description = restaurant.description
                )
            }

            servicesSection(
                modifier = Modifier.padding(top = 4.dp),
                services = restaurant.services
            )

            platesSection(
                modifier = Modifier.padding(top = 32.dp),
                plates = restaurant.plates
            )

            amenitiesSection(
                Modifier
                    .padding(top = 32.dp),
                amenities = restaurant.amenities,
                menu = restaurant.menu,
                drinks = restaurant.drinks,
                cuisines = restaurant.cuisines
            )

            mapSection(
                Modifier
                    .height(300.dp)
                    .fillMaxWidth()
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
            items = listOf(
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