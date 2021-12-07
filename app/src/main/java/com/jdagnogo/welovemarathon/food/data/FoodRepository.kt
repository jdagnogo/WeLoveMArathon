package com.jdagnogo.welovemarathon.food.data

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.resourceAsFlow
import com.jdagnogo.welovemarathon.food.domain.FoodCategory
import com.jdagnogo.welovemarathon.food.domain.restaurant.Food
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface FoodRepository {
    suspend fun getRestaurants(forceFetch: Boolean = false): Flow<Resource<List<Food>>>
    suspend fun getDesserts(forceFetch: Boolean = false): Flow<Resource<List<Food>>>
    suspend fun getCoffees(forceFetch: Boolean = false): Flow<Resource<List<Food>>>
}

class FoodRepositoryIml @Inject constructor(private val foodData: FoodData) : FoodRepository {
    override suspend fun getRestaurants(forceFetch: Boolean): Flow<Resource<List<Food>>> {
        return getFood(FoodCategory.RESTAURANT.title,
            { foodData.restaurantRemoteData.getRestaurants() },
            forceFetch)
    }

    override suspend fun getDesserts(forceFetch: Boolean): Flow<Resource<List<Food>>> {
        return getFood(FoodCategory.DESSERT.title,
            { foodData.restaurantRemoteData.getDesserts() },
            forceFetch)
    }

    override suspend fun getCoffees(forceFetch: Boolean): Flow<Resource<List<Food>>> {
        return getFood(FoodCategory.COFFEE.title,
            { foodData.restaurantRemoteData.getCoffees() },
            forceFetch)
    }

    private fun getFood(
        type: String,
        networkCall: suspend () -> Resource<List<Food>>,
        forceFetch: Boolean,
    ): Flow<Resource<List<Food>>> {
        with(foodData) {
            return resourceAsFlow(
                forceFetch = forceFetch,
                fetchFromLocal = {
                    restaurantDao.getFoods(type = type)
                        .map { restaurantMapper.toRestaurants(type = type, it) }
                },
                networkCall = { networkCall() },
                saveCallResource = { restaurants ->
                    val entities = restaurantMapper.toRestaurantsEntities(type = type, restaurants)
                    restaurantDao.update(type = type, entities)
                },
                checkDataFreshness = { false }
            )
        }
    }
}
