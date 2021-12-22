package com.intentsoft.matnuz.repositories

import com.intentsoft.matnuz.api.MatnApi
import com.intentsoft.matnuz.models.CorrectData
import com.intentsoft.matnuz.models.DictionaryItem
import com.intentsoft.matnuz.models.Text
import com.intentsoft.matnuz.models.Transliteration
import retrofit2.Response
import javax.inject.Inject

class MatnRepository @Inject constructor(
    private val matnApi: MatnApi
) {

    suspend fun postText(text: Text): Response<CorrectData> {
        return matnApi.postText(text)
    }

    suspend fun changeToLatin(text: Transliteration): Response<String> {
        return matnApi.changeToLatin(text)
    }

    suspend fun changeToCyrillic(text: Transliteration): Response<String> {
        return matnApi.changeCyrillic(text)
    }

    suspend fun getDictionary(text: String): Response<List<DictionaryItem>> {
        return matnApi.getDictionary(text)
    }
}