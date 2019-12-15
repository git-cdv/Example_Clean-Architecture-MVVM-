package com.example.manuel.baseproject.data.datasource.cache

import java.io.File

/**
 * This class is a basic generic example cache to save objects in a list that implements
 * Cacheable interface
 *
 * @see com.example.manuel.baseproject.data.datasource.cache.Cacheable
 *
 * @param T the type of a member in this groups
 * @property file the txt file to save and fetch the data
 * @property jsonConverter the object to serialize / deserialize
 * */
class ListCacheDataSource<T>(private val file: File, private val jsonConverter: JsonConverter<T>) {

    fun getItems(): T? {
        val json = if (file.isFile) file.readText(Charsets.UTF_8) else ""

        return jsonConverter.toObject(json)
    }

    fun saveItem(objectToSave: Cacheable): Boolean {
        val listToSave = if (file.isFile) {
            val mutableItems: MutableList<Cacheable> = getItems() as MutableList<Cacheable>
            mutableItems.add(objectToSave)
            mutableItems
        } else {
            val firstItemWhenFileIsEmpty = listOf(objectToSave)
            firstItemWhenFileIsEmpty
        }

        file.writeText(jsonConverter.toJson(listToSave))

        return isObjectSaved(objectToSave)
    }

    private fun isObjectSaved(cacheableObject: Cacheable): Boolean {
        return (getItems() as MutableList<Cacheable>).any { cacheable -> cacheable.id == cacheableObject.id }
    }

    fun removeItem(itemId: Int): Boolean {
        if (file.isFile) {
            val mutableItems: MutableList<Cacheable> = getItems() as MutableList<Cacheable>
            val existItem = mutableItems.any { it.id == itemId }
            if (existItem) {
                val itemToRemove = mutableItems.filter { it.id == itemId }[0]
                mutableItems.remove(itemToRemove)
                file.writeText(jsonConverter.toJson(mutableItems.toList()))
            }
        }

        return isItemRemoved(itemId)
    }


    private fun isItemRemoved(itemId: Int): Boolean {
        return (getItems() as MutableList<Cacheable>).any { cacheable -> cacheable.id == itemId }.not()
    }
}
