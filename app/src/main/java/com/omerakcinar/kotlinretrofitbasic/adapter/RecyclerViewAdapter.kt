package com.omerakcinar.kotlinretrofitbasic.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.omerakcinar.kotlinretrofitbasic.databinding.RowLayoutBinding
import com.omerakcinar.kotlinretrofitbasic.model.CryptoModel

/*
class RecyclerViewAdapter(private val cryptoList : ArrayList<CryptoModel>, private val listener : Listener) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

    interface Listener {
        fun onItemClick(cryptoModel: CryptoModel)
    }

    private val colors: Array<String> = arrayOf("#13bd27","#29c1e1","#b129e1","#d3df13","#f6bd0c","#a1fb93","#0d9de3","#ffe48f")

    class RowHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RowHolder(binding)
    }

    override fun getItemCount(): Int {
        return cryptoList.count()
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener.onItemClick(cryptoList.get(position))
        }
        holder.itemView.setBackgroundColor(Color.parseColor(colors[position % 8]))

        holder.binding.currencyTV.text = cryptoList.get(position).currency
        holder.binding.priceTV.text = cryptoList.get(position).price

    }


}

 */

class RecyclerViewAdapter(private val cryptoList : ArrayList<CryptoModel>) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {
    class RowHolder (val binding: RowLayoutBinding): RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RowHolder(binding)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.binding.currencyTV.text = cryptoList[position].currency
        holder.binding.priceTV.text = cryptoList[position].price
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.binding.root.context,"${cryptoList[position].currency} clicked.",Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }
}

