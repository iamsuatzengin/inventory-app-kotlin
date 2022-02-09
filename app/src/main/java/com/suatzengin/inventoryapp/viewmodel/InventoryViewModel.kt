package com.suatzengin.inventoryapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.suatzengin.inventoryapp.model.Item
import com.suatzengin.inventoryapp.data.ItemDatabase
import com.suatzengin.inventoryapp.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class InventoryViewModel(application: Application) : AndroidViewModel(application) {
    val allItem: LiveData<List<Item>>
    private val repository: InventoryRepository

    init {
        val itemDao = ItemDatabase.getDatabase(application).itemDao()
        repository = InventoryRepository(itemDao)
        allItem = repository.getAllItem().asLiveData()
    }

    fun getItem(id: Int): LiveData<Item> {
        return repository.getItemById(id).asLiveData()
    }

    private fun insert(item: Item) {
        viewModelScope.launch {
            repository.insert(item)
        }
    }

    fun update(item: Item) {
        viewModelScope.launch {
            repository.update(item)
        }
    }

    fun delete(item: Item) {
        viewModelScope.launch {
            repository.delete(item)
        }
    }

    fun addNewItem(name: String, price: Double, count: Int) {
        val newItem = Item(
            itemName = name, itemPrice = price, itemCount = count
        )
        insert(newItem)
    }

    fun sellItem(item: Item) {

        if (item.itemCount > 0) {
            val newItem = item.copy(itemCount = item.itemCount - 1)
            update(newItem)
        }
    }
    fun isEntryValid(name: String, price: String, count: String) : Boolean{
        if(name.isBlank() || price.isBlank() || count.isBlank()){
            return false
        }
        return true
    }

}