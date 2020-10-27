package com.example.digimenu.models.payload

import com.google.gson.annotations.SerializedName

data class PayloadData(

    var category: String?,

    val montag: List<MenuProductPriceAllergenItem?>?,

    val dienstag: List<MenuProductPriceAllergenItem?>?,

    val mittwoch: List<MenuProductPriceAllergenItem?>?,

    val donnerstag: List<MenuProductPriceAllergenItem?>?,

    val freitag: List<MenuProductPriceAllergenItem?>?,

    val samstag: List<MenuProductPriceAllergenItem?>?

)

data class MenuProductPriceAllergenItem(
    @field:SerializedName("productName")
    val productName: String?,

    @field:SerializedName("price")
    val price: String?,

    @field:SerializedName("allergenLabels")
    val allergenLabels: String?
)