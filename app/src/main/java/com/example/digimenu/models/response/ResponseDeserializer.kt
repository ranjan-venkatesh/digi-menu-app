package com.example.digimenu.models.response

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ResponseDeserializer() : JsonDeserializer<Response> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext
    ): Response? {
        val allergens = deserializeAllergens(json?.asJsonObject?.get("Allergens"), context)
        val products = deserializeProducts(json?.asJsonObject?.get("Products"), allergens, context)
        val rows = deserializeRows(json?.asJsonObject?.get("Rows"), products, context)


        return Response(
            allergens,
            products,
            rows
        )

    }

    //Deserialize Allergens Body
    fun deserializeAllergens(
        json: JsonElement?,
        context: JsonDeserializationContext
    ): MutableMap<String, AllergenItem?>? {
        var allergens = mutableMapOf<String, AllergenItem?>()
        val mapType = object : TypeToken<Map<String, AllergenItem>>() {}.getType()
        allergens = context.deserialize(json, mapType);
        return allergens
    }

    //Deserialize Products Body
    fun deserializeProducts(
        json: JsonElement?,
        allergens: Map<String, AllergenItem?>?,
        context: JsonDeserializationContext
    ): MutableMap<Double, ProductItem?>? {
        var products = mutableMapOf<Double, ProductItem?>()
        val mapType = object : TypeToken<Map<Double, ProductItem>>() {}.getType()
        products = context.deserialize(json, mapType);

        for ((productId, product) in products) {
            val list = product?.allergenIds
            if (list != null) {
                val filteredAllergenMap = allergens?.filter { (key, value) -> list.contains(key) }
                product?.allergens = filteredAllergenMap?.values?.toList()
            }
        }

        return products
    }


    //Deserialize Rows Body
    fun deserializeRows(
        json: JsonElement?,
        products: Map<Double, ProductItem?>?,
        context: JsonDeserializationContext
    ): List<RowItem?>? {
        var rows = listOf<RowItem?>()
        val listType = object : TypeToken<List<RowItem>>() {}.getType()
        rows = context.deserialize(json, listType);

        for (row in rows) {
            for (day in row?.days!!) {
                day?.productIds =
                    day?.productIds?.map { products?.get(it?.productId) } as MutableList<ProductItem?>?

            }
        }

        return rows
    }
}