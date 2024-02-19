package com.nyx.common_data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nyx.common_data.repository.product.room.entities.Feedback
import com.nyx.common_data.repository.product.room.entities.Info
import com.nyx.common_data.repository.product.room.entities.Price

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun priceToJson(value: Price?): String = gson.toJson(value)

    @TypeConverter
    fun jsonToPrice(value: String): Price? = gson.fromJson(value, Price::class.java)

    @TypeConverter
    fun feedbackToJson(value: Feedback?): String = gson.toJson(value)

    @TypeConverter
    fun jsonToFeedback(value: String): Feedback? = gson.fromJson(value, Feedback::class.java)

    @TypeConverter
    fun listToString(value: List<String>?): String = gson.toJson(value)

    @TypeConverter
    fun stringToList(value: String): List<String> =
        gson.fromJson(value, object : TypeToken<List<String>>() {}.type)

    @TypeConverter
    fun infoListToJson(value: List<Info>?): String = gson.toJson(value)

    @TypeConverter
    fun jsonToInfoList(value: String): List<Info>? =
        gson.fromJson(value, object : TypeToken<List<Info>>() {}.type)
}