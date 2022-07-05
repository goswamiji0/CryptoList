package com.rgg.cryptolist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rgg.cryptolist.databinding.CurrencyRvItemBinding
import com.rgg.cryptolist.models.Currency

class CategoryAdapter : RecyclerView.Adapter<MainViewHolder>() {

    private lateinit var mClick: OnItemClick
    private var categoryList = mutableListOf<Currency>()
    fun setCategoryList(categoryList: List<Currency>, mClick: OnItemClick) {
        this.mClick = mClick;
        this.categoryList = categoryList.toMutableList()
        notifyDataSetChanged()
    }

    // Clean all elements of the recycler
    fun clear() {
        categoryList.clear();
        notifyDataSetChanged();
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder {
        val inflate = LayoutInflater.from(parent.context);

        val binding = CurrencyRvItemBinding.inflate(inflate, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val context: Context = holder.itemView.context
        val category = categoryList[position]
        holder.binding.message.text = category.baseAsset + "/INR"
        holder.binding.lastPrice.text = "₹" + category.lastPrice

        val growth :Double = String.format("%.2f", getGrowthRate(category)).toDouble()
        holder.binding.growthRate.text = "($growth%)";
        if( getGrowthRate(category)>0){
            holder.binding.growthRate.setTextColor(ContextCompat.getColor(context, R.color.green))
        }else{
            holder.binding.growthRate.setTextColor(ContextCompat.getColor(context, R.color.red))
        }


        holder.binding.root.setOnClickListener {
            mClick.onItemClick(category.symbol)
        }
    }

    private fun getGrowthRate(currency: Currency): Double {
        return (currency.lastPrice.toDouble() - currency.openPrice.toDouble()) / currency.openPrice.toDouble() * 100
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}

class MainViewHolder(val binding: CurrencyRvItemBinding) : RecyclerView.ViewHolder(binding.root)

interface OnItemClick {
    fun onItemClick(symbol: String)
}
