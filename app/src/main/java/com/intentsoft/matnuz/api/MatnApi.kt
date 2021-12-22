package com.intentsoft.matnuz.api

import com.intentsoft.matnuz.models.CorrectData
import com.intentsoft.matnuz.models.DictionaryItem
import com.intentsoft.matnuz.models.Text
import com.intentsoft.matnuz.models.Transliteration
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MatnApi {

    @POST("correct")
    suspend fun postText(
        @Body text: Text
    ): Response<CorrectData>

    @POST("latin")
    suspend fun changeToLatin(
        @Body text: Transliteration
    ): Response<String>

    @POST("cyrillic")
    suspend fun changeCyrillic(
        @Body text: Transliteration
    ): Response<String>

    @GET("dictionary")
    suspend fun getDictionary(
        @Query("search") text: String
    ): Response<List<DictionaryItem>>
}