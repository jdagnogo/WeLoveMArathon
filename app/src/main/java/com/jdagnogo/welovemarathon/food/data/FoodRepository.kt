package com.jdagnogo.welovemarathon.food.data

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.resourceAsFlow
import com.jdagnogo.welovemarathon.food.domain.restaurant.Restaurant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface FoodRepository {
    suspend fun getRestaurants(forceFetch: Boolean = false): Flow<Resource<List<Restaurant>>>
}

class FoodRepositoryIml @Inject constructor(private val foodData: FoodData) : FoodRepository {
    override suspend fun getRestaurants(forceFetch: Boolean): Flow<Resource<List<Restaurant>>> {
        with(foodData) {
            return resourceAsFlow(
                forceFetch = forceFetch,
                fetchFromLocal = {
                    restaurantDao.getAll().map { restaurantMapper.toRestaurants(it) }
                },
                networkCall = { restaurantRemoteData.getRestaurants() },
                saveCallResource = { restaurants ->
                    val entities = restaurantMapper.toRestaurantsEntities(restaurants)
                    restaurantDao.updateBlogs(entities)
                },
                checkDataFreshness = { false }
            )
        }
    }
}
