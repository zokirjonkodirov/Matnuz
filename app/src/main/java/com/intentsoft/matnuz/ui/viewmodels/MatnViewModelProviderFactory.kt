package com.intentsoft.matnuz.ui.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.intentsoft.matnuz.MatnApp.Companion.app
import com.intentsoft.matnuz.repositories.MatnRepository
import javax.inject.Inject

class MatnViewModelProviderFactory @Inject constructor(
    private val matnRepository: MatnRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MatnViewModel(matnRepository) as T
    }
}