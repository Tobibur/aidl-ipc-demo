package com.tobibur.aidl_server.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuItemDao {

    @Query("SELECT * FROM menu_items")
    fun getAllMenuItems(): Flow<List<MenuItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenuItem(entry: MenuItem)

    @Delete
    suspend fun deleteMenuItem(entry: MenuItem)
}