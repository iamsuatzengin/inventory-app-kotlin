package com.suatzengin.inventoryapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.suatzengin.inventoryapp.model.Item
import com.suatzengin.inventoryapp.databinding.RvItemBinding

class RvAdapter(private val onItemClick: (Item) -> Unit) : RecyclerView.Adapter<ItemVHolder>() {

    var itemList: List<Item> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVHolder {
        return ItemVHolder(
            RvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemVHolder, position: Int) {
        val currentItem = itemList[position]
        holder.bind(currentItem, onItemClick)
    }

    override fun getItemCount(): Int = itemList.size
}