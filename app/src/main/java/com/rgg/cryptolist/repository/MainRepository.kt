package com.rgg.cryptolist.repository

import com.rgg.cryptolist.repository.retrofit.RetrofitService

class MainRepository(private val retrofitService : RetrofitService) {

    fun getAllCurrencies() = retrofitService.getAllCurrency();
    fun getDetailCurrencies(symbol : String) = retrofitService.getDetailCurrency(symbol);
}