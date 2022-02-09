package com.suatzengin.inventoryapp.adapter

import androidx.recyclerview.widget.RecyclerView
import com.suatzengin.inventoryapp.model.Item
import com.suatzengin.inventoryapp.databinding.RvItemBinding
import com.suatzengin.inventoryapp.model.getFormattedPrice

class ItemVHolder(
    private val binding: RvItemBinding,

) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Item, onItemClick: (Item) -> Unit) {
        binding.tvItemName.text = item.itemName
        binding.tvItemPrice.text = item.getFormattedPrice()
        binding.tvItemCount.text = item.itemCount.toString()

        binding.rvItemId.setOnClickListener {
            onItemClick(item)
        }
    }
}