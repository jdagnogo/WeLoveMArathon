package com.jdagnogo.welovemarathon.shopping.data

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.shopping.domain.Shopping
import javax.inject.Inject

@Keep
class ShoppingMapper @Inject constructor() {
    fun toShoppings(shoppingEntities: List<ShoppingEntity>): List<Shopping> {
        return shoppingEntities.map {
            it.toShopping()
        }
    }

    fun toShoppingsEntities(shoppings: List<Shopping>): List<ShoppingEntity> {
        return shoppings.map {
            it.toShoppingEntity()
        }
    }
}
