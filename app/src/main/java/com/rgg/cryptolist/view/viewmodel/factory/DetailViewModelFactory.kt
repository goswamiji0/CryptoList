package com.rgg.cryptolist.view.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rgg.cryptolist.repository.MainRepository
import com.rgg.cryptolist.view.viewmodel.CurrencyDetailViewModel
import java.lang.IllegalArgumentException

class DetailViewModelFactory constructor(private val repository: MainRepository, private val symbol: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CurrencyDetailViewModel::class.java)) {
            CurrencyDetailViewModel(this.repository, symbol) as T
        } else {
            throw IllegalArgumentException("CategoryViewModel not found")
        }
    }
}