package com.suatzengin.inventoryapp.repository

import com.suatzengin.inventoryapp.model.Item
import com.suatzengin.inventoryapp.data.ItemDao
import kotlinx.coroutines.flow.Flow

class InventoryRepository(
    private val itemDao: ItemDao
) {

    fun getAllItem(): Flow<List<Item>> {
        return itemDao.getAllItem()
    }
    fun getItemById(id: Int): Flow<Item> {
        return itemDao.getItemById(id)
    }

    suspend fun insert(item: Item) {
        itemDao.addItem(item)
    }

    suspend fun delete(item: Item) {
        itemDao.deleteItem(item)
    }

    suspend fun update(item: Item) {
        itemDao.updateItem(item)
    }


}