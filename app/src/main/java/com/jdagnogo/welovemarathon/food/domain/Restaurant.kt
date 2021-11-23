package com.jdagnogo.welovemarathon.food.domain

data class Restaurant(
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val number: String = "",
    val location: String = "",
    val locationLink: String = "",
    val place: String = "",
    val website: String = "",
)

fun Restaurant.fakeList(): List<Restaurant> {
    return listOf(
        Restaurant("toto1 jfnejfnj rkjn rgjgn jerg", "restaurant1 frnfn nfnerfbk jfb,r n,erfb, be ", "number", "number grg rg ger r", "location rege rg gr erg ger "),
        Restaurant("toto2", "restaurant2", "number", "location"),
        Restaurant("toto3", "restaurant3", "number", "location"),
        Restaurant("toto4", "restaurant4", "number", "location"),
    )
}
