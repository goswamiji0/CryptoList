package com.rgg.cryptolist.view.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rgg.cryptolist.repository.MainRepository
import com.rgg.cryptolist.models.Currency
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyDetailViewModel constructor(private val repository: MainRepository, private val symbol: String) : ViewModel() {

    val categoryList = MutableLiveData<Currency>()
    val errorMessage = MutableLiveData<String>()
    fun  getCurrencyDetail(){
        Log.d("rahul", symbol)
        val response = repository.getDetailCurrencies(symbol)
        response.enqueue(object : Callback<Currency> {
            override fun onResponse(call: Call<Currency>, response: Response<Currency>) {
                Log.d("rahul", response.body().toString())
                categoryList.postValue(response.body())
            }

            override fun onFailure(call: Call<Currency>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}