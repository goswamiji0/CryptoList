package com.rgg.cryptolist.view.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rgg.cryptolist.repository.MainRepository
import com.rgg.cryptolist.view.viewmodel.CurrencyViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory constructor(private val repository: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CurrencyViewModel::class.java)) {
            CurrencyViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("CategoryViewModel not found")
        }
    }
}