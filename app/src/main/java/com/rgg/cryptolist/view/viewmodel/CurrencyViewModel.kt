package com.rgg.cryptolist.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rgg.cryptolist.repository.MainRepository
import com.rgg.cryptolist.models.Currency
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyViewModel constructor(private val repository: MainRepository) : ViewModel() {

    val categoryList = MutableLiveData<List<Currency>>()
    val errorMessage = MutableLiveData<String>()
    fun  getCurrency(){
        val response = repository.getAllCurrencies()
        response.enqueue(object : Callback<List<Currency>> {
            override fun onResponse(call: Call<List<Currency>>, response: Response<List<Currency>>) {
                categoryList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Currency>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}