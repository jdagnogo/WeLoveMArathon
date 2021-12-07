package com.jdagnogo.welovemarathon.food.data

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.food.domain.FoodCategory
import com.jdagnogo.welovemarathon.food.domain.restaurant.Food
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

interface FoodRepository {
    fun getFood(type: String, isRecommended: Boolean): List<Food>
}

class FoodRepositoryIml @Inject constructor(
    private val foodData: FoodData,
    private val coroutineScope: CoroutineScope,
) : FoodRepository {

    override fun getFood(
        type: String,
        isRecommended: Boolean,
    ): List<Food> {
        return data.value.data?.filter { data ->
            data.type == type && data.isRecommended == isRecommended
        } ?: listOf()
    }

    private val _data: MutableStateFlow<Resource<List<Food>>> =
        MutableStateFlow(Resource.Loading(listOf()))
    val data: StateFlow<Resource<List<Food>>>
        get() = _data

    init {
        coroutineScope.launch {
            val foods = mutableListOf<Food>()
            FoodCategory.values().forEach { foodCategory ->
                foodData.restaurantRemoteData.getFood(foodCategory.title).data?.let { food ->
                    val toto = food.map {
                        it.type = foodCategory.title
                        it
                    }

                    foods.addAll(toto)
                    foodData.restaurantDao.update(foodCategory.title,
                        foodData.restaurantMapper.toRestaurantsEntities(food))
                }
            }
            foodData.restaurantDao.getAll().collectLatest {
                _data.value = Resource.Success(foodData.restaurantMapper.toRestaurants(it))
            }
        }
    }
}
