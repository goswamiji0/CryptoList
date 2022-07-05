package com.rgg.cryptolist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rgg.cryptolist.R
import com.rgg.cryptolist.databinding.ActivityDetailBinding
import com.rgg.cryptolist.view.viewmodel.factory.DetailViewModelFactory
import com.rgg.cryptolist.models.Currency
import com.rgg.cryptolist.repository.MainRepository
import com.rgg.cryptolist.repository.retrofit.RetrofitService
import com.rgg.cryptolist.view.viewmodel.CurrencyDetailViewModel

class DetailActivity : AppCompatActivity() {
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var binding: ActivityDetailBinding
    private lateinit var symbol: String;
    lateinit var viewModel: CurrencyDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        symbol = intent.getStringExtra("SYMBOL")!!
        viewModel =
            ViewModelProvider(
                this,
                DetailViewModelFactory(MainRepository(retrofitService), symbol)
            ).get(
                CurrencyDetailViewModel::class.java
            )


        viewModel.categoryList.observe(this, Observer { it ->
            (it.baseAsset + getString(R.string.inr)).also { binding.symbol.text = it };
            ("₹" + it.openPrice).also { binding.openPriceText.text = it }
            ("₹" + it.lastPrice).also { binding.currentPrice.text = it }
            ("₹" + it.highPrice).also { binding.highPriceText.text = it }
            ("₹" + it.lowPrice).also { binding.lowPriceText.text = it }
            ("₹" + it.lastPrice).also { binding.lastPriceText.text = it }
            binding.volumeText.text = it.volume

            val growth :Double = String.format("%.2f", getGrowthRate(it)).toDouble()
            binding.growthRate.text = "($growth%)";
            if( getGrowthRate(it)>0){
                binding.growthRate.setTextColor(ContextCompat.getColor(this, R.color.green))
            }else{
                binding.growthRate.setTextColor(ContextCompat.getColor(this, R.color.red))
            }

        })

        viewModel.getCurrencyDetail();


    }

    private fun getGrowthRate(currency: Currency): Double {
        return (currency.lastPrice.toDouble() - currency.openPrice.toDouble()) / currency.openPrice.toDouble() * 100
    }


}