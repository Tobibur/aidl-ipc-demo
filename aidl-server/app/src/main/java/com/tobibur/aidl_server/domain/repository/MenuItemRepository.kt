package com.tobibur.aidl_server.domain.repository

import com.tobibur.aidl_server.domain.model.MenuItem
import kotlinx.coroutines.flow.Flow

interface MenuItemRepository {

    fun getAllMenuItems(): Flow<List<MenuItem>>

    suspend fun insertMenuItem(item: MenuItem)

    suspend fun getMenuItemById(id: Int): MenuItem?

    suspend fun deleteMenuItem(item: MenuItem)
}