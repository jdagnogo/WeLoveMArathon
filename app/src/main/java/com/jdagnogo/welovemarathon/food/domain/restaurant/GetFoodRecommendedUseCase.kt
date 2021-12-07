package com.jdagnogo.welovemarathon.food.domain.restaurant

import com.jdagnogo.welovemarathon.food.data.FoodRepository
import javax.inject.Inject

class GetFoodRecommendedUseCase @Inject constructor(private val repository: FoodRepository) {
    suspend operator fun invoke(type: String): List<Food> {
        return repository.getFood(type = type, true)
    }
}
