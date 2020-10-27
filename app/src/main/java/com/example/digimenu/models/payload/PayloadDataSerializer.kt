package com.example.digimenu.models.payload

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class PayloadDataSerializer : JsonSerializer<PayloadData> {
    override fun serialize(
        payloadData: PayloadData,
        type: Type,
        jsonSerializationContext: JsonSerializationContext
    ): JsonElement {
        val jsonObject = JsonObject()
        jsonObject.add("Category", jsonSerializationContext.serialize(payloadData.category))
        jsonObject.add("Montag", jsonSerializationContext.serialize(payloadData.montag))
        jsonObject.add("Dienstag", jsonSerializationContext.serialize(payloadData.dienstag))
        jsonObject.add("Mittwoch", jsonSerializationContext.serialize(payloadData.mittwoch))
        jsonObject.add("Donnerstag", jsonSerializationContext.serialize(payloadData.donnerstag))
        jsonObject.add("Freitag", jsonSerializationContext.serialize(payloadData.freitag))
        jsonObject.add("Samstag", jsonSerializationContext.serialize(payloadData.samstag))

        return jsonObject
    }
}