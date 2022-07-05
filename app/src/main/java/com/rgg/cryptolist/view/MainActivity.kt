package com.rgg.cryptolist.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rgg.cryptolist.*
import com.rgg.cryptolist.databinding.ActivityMainBinding
import com.rgg.cryptolist.view.viewmodel.factory.ViewModelFactory
import com.rgg.cryptolist.repository.MainRepository
import com.rgg.cryptolist.repository.retrofit.RetrofitService
import com.rgg.cryptolist.utils.CommonDialog
import com.rgg.cryptolist.view.viewmodel.CurrencyViewModel

class MainActivity : AppCompatActivity() {
    private val retrofitService = RetrofitService.getInstance()
    private val adapter = CategoryAdapter()
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: CurrencyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel =
            ViewModelProvider(this, ViewModelFactory(MainRepository(retrofitService))).get(
                CurrencyViewModel::class.java
            )


        //set recyclerview adapter
        binding.recyclerview.adapter = adapter

        getData()
        binding.swipeContainer.setOnRefreshListener {
            adapter.clear()
            viewModel.getCurrency()
        }

        viewModel.getCurrency()
    }

    private fun getData(){
        viewModel.categoryList.observe(this, Observer {
            adapter.setCategoryList(it , object : OnItemClick {
                override fun onItemClick(symbol: String) {

                    val intent= Intent( this@MainActivity, DetailActivity::class.java)
                    intent.putExtra("SYMBOL",symbol)
                    startActivity(intent)
                }
            })

            binding.swipeContainer.isRefreshing = false
        })

        viewModel.errorMessage.observe(this, Observer {
            CommonDialog.showDialog(this@MainActivity,it)

        })
    }
}