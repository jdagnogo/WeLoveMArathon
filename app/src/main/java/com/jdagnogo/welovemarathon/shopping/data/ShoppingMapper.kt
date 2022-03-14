package com.jdagnogo.welovemarathon.shopping.data

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.shopping.domain.Shopping
import com.jdagnogo.welovemarathon.shopping.domain.ShoppingCategory
import com.jdagnogo.welovemarathon.shopping.domain.ShoppingTag
import javax.inject.Inject

@Keep
class ShoppingMapper @Inject constructor() {
    fun toDomain(shoppingEntities: List<ShoppingEntity>): List<Shopping> {
        return shoppingEntities.map {
            it.toShopping()
        }
    }

    fun toEntities(shoppings: List<Shopping>): List<ShoppingEntity> {
        return shoppings.map {
            it.toShoppingEntity()
        }
    }

    fun toDomainCategories(entities: List<ShoppingCategoryEntity>): List<ShoppingCategory> {
        return entities.map {
            it.toDomainCategory()
        }
    }

    fun toEntitiesCategories(categories: List<ShoppingCategory>): List<ShoppingCategoryEntity> {
        return categories.map {
            it.toCategoryEntity()
        }
    }

    fun toDomainTag(entities: List<ShoppingTagEntity>): List<ShoppingTag> {
        return entities.map {
            it.toDomainCategory()
        }
    }

    fun toEntitiesTag(tag: List<ShoppingTag>): List<ShoppingTagEntity> {
        return tag.map {
            it.toEntity()
        }
    }
}
