package com.suatzengin.inventoryapp.data

import androidx.room.*
import com.suatzengin.inventoryapp.model.Item
import kotlinx.coroutines.flow.Flow


@Dao
interface ItemDao {

    @Insert
    suspend fun addItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)

    @Update
    suspend fun updateItem(item: Item)

    @Query("SELECT * FROM item ORDER BY count ASC")
    fun getAllItem(): Flow<List<Item>>

    @Query("SELECT * FROM item WHERE id = :id")
    fun getItemById(id: Int): Flow<Item>
}