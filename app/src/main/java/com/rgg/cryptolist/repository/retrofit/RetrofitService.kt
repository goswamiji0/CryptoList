package com.rgg.cryptolist.repository.retrofit

import com.rgg.cryptolist.models.Currency
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitService {

    @GET("/sapi/v1/tickers/24hr")
    fun getAllCurrency(): Call<List<Currency>>

    @GET("/sapi/v1/ticker/24hr")
    fun getDetailCurrency(@Query("symbol") symbol: String?): Call<Currency>

    companion object {

        var retrofitService: RetrofitService? = null
        var logging = HttpLoggingInterceptor()

        //Create the Retrofit service instance using the retrofit.
        fun getInstance(): RetrofitService {
            logging.level = HttpLoggingInterceptor.Level.BODY;
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging);
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.wazirx.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}