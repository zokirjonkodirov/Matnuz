package com.intentsoft.matnuz.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.intentsoft.matnuz.repositories.MatnRepository
import javax.inject.Inject

class MatnViewModelProviderFactory @Inject constructor(
    private val matnRepository: MatnRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MatnViewModel(matnRepository) as T
    }
}