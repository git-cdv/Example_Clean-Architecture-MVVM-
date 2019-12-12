package com.example.manuel.baseproject.data.datasource.cache

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File
import java.lang.reflect.Type

/**
 * This class is a basic generic cache example
 *
 * @property file the txt file to save and fetch the data
 * @property typeData the object class or typeToken list to deserialize with Gson
 * */
class CacheDataSource<T>(private val file: File, private val gson: Gson, private val typeData: Type) {

    fun getItems(): T? {
        val json = if (file.isFile) {
            file.readText(Charsets.UTF_8)
        } else {
            ""
        }

        return deserializeFromJsonToObject(json)
    }

    private fun deserializeFromJsonToObject(json: String): T? = gson.fromJson(json, typeData)

    fun saveItem(objectToSave: Cacheable): Boolean {
        val listToSave = if (file.isFile) {
            val mutableItems: MutableList<Cacheable> = getItems() as MutableList<Cacheable>
            mutableItems.add(objectToSave)
            mutableItems
        } else {
            val firstItemWhenFileIsEmpty = listOf(objectToSave)
            firstItemWhenFileIsEmpty
        }

        file.writeText(serializeFromObjectToJson(listToSave))

        return isObjectSaved(objectToSave)
    }

    private fun serializeFromObjectToJson(objectToSave: List<Cacheable>): String {
        return gson.toJson(objectToSave)
    }

    private fun isObjectSaved(objectA: Cacheable): Boolean {
        return (getItems() as MutableList<Cacheable>).any { cacheable -> cacheable.id == objectA.id }
    }

    fun removeItem(itemId: Int): Boolean {
        if (file.isFile) {
            val mutableItems: MutableList<Cacheable> = getItems() as MutableList<Cacheable>
            val existItem = mutableItems.any { it.id == itemId }
            if (existItem) {
                val itemToRemove = mutableItems.filter { it.id == itemId }[0]
                mutableItems.remove(itemToRemove)
                file.writeText(serializeFromObjectToJson(mutableItems.toList()))
            }
        }

        return isItemRemoved(itemId)
    }


    private fun isItemRemoved(itemId: Int): Boolean {
        return (getItems() as MutableList<Cacheable>).any { cacheable -> cacheable.id == itemId }.not()
    }
}
