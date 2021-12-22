package com.intentsoft.matnuz.ui.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intentsoft.matnuz.MatnApp
import com.intentsoft.matnuz.models.*
import com.intentsoft.matnuz.repositories.MatnRepository
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MatnViewModel @Inject constructor(
    private val matnRepository: MatnRepository
) : ViewModel() {

    val correctData: MutableLiveData<Resource<CorrectData>> = MutableLiveData()
    val latin: MutableLiveData<Resource<String>> = MutableLiveData()
    val cyrillic: MutableLiveData<Resource<String>> = MutableLiveData()
    val dictionaryList: MutableLiveData<Resource<List<DictionaryItem>>> = MutableLiveData()

    fun postText(text: String, output: String) = viewModelScope.launch {
        val editedText = stringToList(text, output)
        val response = matnRepository.postText(editedText)
        correctData.value = handlePostText(response)
    }

    fun changeToLatin(text: Transliteration) = viewModelScope.launch {
        val response = matnRepository.changeToLatin(text)
        latin.value = (handleTransliteration(response))
    }

    fun changeToCyrillic(text: Transliteration) = viewModelScope.launch {
        val response = matnRepository.changeToCyrillic(text)
        cyrillic.value = (handleTransliteration(response))
    }

    fun getDictionary(text: String) = viewModelScope.launch {
        val response = matnRepository.getDictionary(text)
        dictionaryList.value = handleDictionary(response)
    }

    private fun handlePostText(response: Response<CorrectData>): Resource<CorrectData> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.code().toString())
    }

    private fun handleTransliteration(response: Response<String>): Resource<String> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.code().toString())
    }

    private fun handleDictionary(response: Response<List<DictionaryItem>>): Resource<List<DictionaryItem>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.code().toString())
    }

    private fun stringToList(text: String, output: String): Text {
        val list: List<String> = listOf(*text.split(" ").toTypedArray())
        return Text(list, output)
    }

}