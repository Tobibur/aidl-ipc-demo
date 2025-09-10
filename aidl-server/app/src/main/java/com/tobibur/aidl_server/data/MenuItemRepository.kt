package com.tobibur.aidl_server.data

import com.tobibur.aidl_server.domain.model.MenuItem
import com.tobibur.aidl_server.domain.repository.MenuItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MenuItemRepositoryImpl(
    private val dao: MenuItemDao
) : MenuItemRepository {
    override fun getAllMenuItems(): Flow<List<MenuItem>> {
        return dao.getAllMenuItems().map { items -> items.map { it.toDomain() } }
    }

    override suspend fun insertMenuItem(item: MenuItem) {
        dao.insertMenuItem(item.toEntity())
    }

    override suspend fun deleteMenuItem(item: MenuItem) {
        dao.deleteMenuItem(item.toEntity())
    }
}