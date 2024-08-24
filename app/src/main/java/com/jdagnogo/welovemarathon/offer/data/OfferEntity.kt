package com.jdagnogo.welovemarathon.offer.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.offer.data.OfferEntity.Companion.TABLE
import com.jdagnogo.welovemarathon.offer.domain.Offer

@Keep
@Entity(tableName = TABLE)
data class OfferEntity(
    @PrimaryKey val id: String = "",
    val startDate: String = "",
    val title: String = "",
    val endDate: String = "",
    val restaurant: String,
    val promos: Promos,
) {
    fun toOffer(): Offer {
        return Offer(
            id = id,
            startDate = startDate,
            title = title,
            endDate = endDate,
            restaurant = restaurant,
            promos = promos.promos

        )
    }

    companion object {
        const val TABLE = "offer"
    }
}
