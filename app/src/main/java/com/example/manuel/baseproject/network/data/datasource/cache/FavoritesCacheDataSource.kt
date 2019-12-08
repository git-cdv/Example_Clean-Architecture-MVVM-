package com.example.manuel.baseproject.network.data.datasource.cache

import com.example.manuel.baseproject.network.data.datasource.cache.model.BeerCacheModel
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.io.File
import java.lang.reflect.Type

private const val CLASSNAME = "CLASSNAME"
private const val DATA = "DATA"

class CacheableTypeAdapter : JsonSerializer<Cacheable>, JsonDeserializer<Cacheable> {

    override fun serialize(src: Cacheable?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonObject().apply {
             addProperty(CLASSNAME, src?.javaClass?.name)
            add(DATA, context?.serialize(src))
        }
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Cacheable {
        val jsonObject = json?.asJsonObject
        val className = jsonObject?.get(CLASSNAME)?.asString
        val clazz = getObjectClass(className)
        return context!!.deserialize(jsonObject?.get(DATA), clazz)
    }

    private fun getObjectClass(className: String?): Class<*> {
        try {
            val classAux: String = className ?: ""
            return Class.forName(classAux)
        } catch (e: ClassNotFoundException) {
            throw JsonParseException(e)
        }
    }
}


class FileCacheDataSource<T>(private val file: File) {

    val gson = GsonBuilder().
            //registerTypeAdapter(Cacheable::class.java, CacheableTypeAdapter()).
            setPrettyPrinting().
             create()

    fun get(): T? {
        val json = if (file.isFile) {
            file.readText(Charsets.UTF_8)
        } else {
            ""
        }

        return deserializeFromJsonToObject(json)
    }

    // TODO Se puede crear una capa de datasource que se le pase por parámetro el typo -> object : TypeToken<ArrayList<BeerCacheModel?>?>() {}.type

    var typeClass: Type =  object : TypeToken<ArrayList<BeerCacheModel?>?>() {}.type
    private fun deserializeFromJsonToObject(json: String): T? {

        val listType = object : TypeToken<ArrayList<BeerCacheModel?>?>() {}.type
        return gson.fromJson(json, typeClass)
    }

    fun save(objectToSave: Cacheable): Boolean {
        val listToSave = if (file.isFile) {
            val mutableFavoriteBeers: MutableList<Cacheable> = get() as MutableList<Cacheable>
            mutableFavoriteBeers.add(objectToSave)
            mutableFavoriteBeers
        } else {
            val firstBeerWhenFileIsEmpty = listOf(objectToSave)
            firstBeerWhenFileIsEmpty
        }

        file.writeText(serializeFromObjectToJson(listToSave))

        return isObjectSaved(objectToSave)
    }

    private fun serializeFromObjectToJson(objectToSave: List<Cacheable>): String {
        return gson.toJson(objectToSave)
    }

    private fun isObjectSaved(objectA: Cacheable): Boolean {
        return (get() as MutableList<Cacheable>).any { cacheable -> cacheable.id == objectA.id }
    }

    fun remove(itemId: Int): Boolean {
        if (file.isFile) {
            val mutableFavoriteBeers: MutableList<Cacheable> = get() as MutableList<Cacheable>
            val existBeer = mutableFavoriteBeers.any { it.id == itemId }
            if (existBeer) {
                val beerToRemove = mutableFavoriteBeers.filter { it.id == itemId }[0]
                mutableFavoriteBeers.remove(beerToRemove)
                file.writeText(serializeFromObjectToJson(mutableFavoriteBeers.toList()))
            }
        }

        return isItemRemoved(itemId)
    }


    private fun isItemRemoved(itemId: Int): Boolean {
        return (get() as MutableList<Cacheable>).any { cacheable -> cacheable.id == itemId }.not()
    }
}
