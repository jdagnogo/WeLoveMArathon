package com.jdagnogo.welovemarathon.offer.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.offer.data.OfferActivatedEntity.Companion.TABLE
import com.jdagnogo.welovemarathon.offer.domain.OfferActivated

@Keep
@Entity(tableName = TABLE)
data class OfferActivatedEntity(
    @PrimaryKey val id: String = "",
    val activationDate: String? = null,
    val displayCount: Int = 0,
) {
    fun toOfferActivated(): OfferActivated = OfferActivated(
        id, activationDate, displayCount
    )

    companion object {
        const val TABLE = "offerActivated"
    }
}