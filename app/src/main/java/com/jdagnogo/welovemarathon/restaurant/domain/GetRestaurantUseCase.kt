package com.jdagnogo.welovemarathon.restaurant.domain

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.food.domain.FoodCategory
import com.jdagnogo.welovemarathon.restaurant.data.RestaurantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter.All

class GetRestaurantUseCase @Inject constructor(
    private val repository: RestaurantRepository,
) {
    operator fun invoke(
        filter: RestaurantAppliedFilter
    ): Flow<Resource<List<Restaurant>>> {
        return repository.data.map { resource ->
            when (repository.data.value) {
                is Resource.Loading -> Resource.Loading()
                is Resource.Success -> {
                    var allRestaurant = resource.data
                    if (filter.typeOfFilters.isNotEmpty() && filter.typeOfFilters.contains(
                            FoodCategory.ALL).not()) {
                        allRestaurant = allRestaurant?.filter { restaurant ->
                            restaurant.categories.any { filter.typeOfFilters.contains(it) }
                        }
                    }

                    if (filter.services.isNotEmpty()) {
                        allRestaurant = allRestaurant?.filter { restaurant ->
                            restaurant.services.any { filter.services.contains(it.title) }
                        }
                    }

                    if (filter.cuisines.isNotEmpty()) {
                        allRestaurant = allRestaurant?.filter { restaurant ->
                            restaurant.cuisines.any { filter.cuisines.contains(it) }
                        }
                    }

                    if (filter.plates.isNotEmpty()) {
                        allRestaurant = allRestaurant?.filter { restaurant ->
                            restaurant.plates.any { filter.plates.contains(it.name) }
                        }
                    }

                    if (filter.drinks.isNotEmpty()) {
                        allRestaurant = allRestaurant?.filter { restaurant ->
                            restaurant.drinks.any { filter.drinks.contains(it) }
                        }
                    }

                    if (filter.location.isNotEmpty()) {
                        allRestaurant = allRestaurant?.filter { restaurant ->
                            restaurant.location in filter.location
                        }
                    }

                    if (filter.prices.isNotEmpty()) {
                        allRestaurant = allRestaurant?.filter { restaurant ->
                            restaurant.priceRange in filter.prices
                        }
                    }

                    if (filter.handicapAccess) {
                        allRestaurant = allRestaurant?.filter { restaurant ->
                            restaurant.handicapAccess
                        }
                    }

                    Resource.Success(allRestaurant)
                }

                else -> {
                    Resource.Error("", null)
                }
            }
        }
    }
}