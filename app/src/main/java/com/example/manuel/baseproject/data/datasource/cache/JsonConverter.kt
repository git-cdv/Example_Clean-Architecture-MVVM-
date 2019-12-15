package com.example.manuel.baseproject.data.datasource.cache

import com.google.gson.GsonBuilder
import java.lang.reflect.Type

class JsonConverter<T>(private val typeData: Type) {

    private val gson = GsonBuilder().setPrettyPrinting().create()

    fun toObject(json: String): T? = gson.fromJson(json, typeData)

    fun toJson(objectToSave: List<Cacheable>): String = gson.toJson(objectToSave)
}