package com.example.digimenu.models.response

import com.google.gson.annotations.SerializedName

data class Response(

    @field:SerializedName("Allergens")
    val allergens: Map<String, AllergenItem?>? = null,

    @field:SerializedName("Products")
    val products: MutableMap<Double, ProductItem?>? = null,

    @field:SerializedName("Rows")
    val rows: List<RowItem?>? = null
)

data class ProductItem(

    @field:SerializedName("ProductId")
    val productId: Double,

    @field:SerializedName("AllergenIds")
    var allergenIds: List<String?>? = null,

    @field:SerializedName("Name")
    var name: String? = null,

    @field:SerializedName("Price")
    var price: Price? = null,

    var allergens: List<AllergenItem?>? = null
)

data class RowItem(

    @field:SerializedName("Name")
    val name: String? = null,

    @field:SerializedName("Days")
    var days: List<DayItem?>? = null

)

data class AllergenItem(

    @field:SerializedName("Id")
    val id: String,

    @field:SerializedName("Label")
    val label: String? = null
)

data class DayItem(

    @field:SerializedName("Weekday")
    val weekday: Int? = null,

    @field:SerializedName("ProductIds")
    var productIds: MutableList<ProductItem?>? = null

)

data class Price(
    @field:SerializedName("Betrag")
    val price: Double? = null
)