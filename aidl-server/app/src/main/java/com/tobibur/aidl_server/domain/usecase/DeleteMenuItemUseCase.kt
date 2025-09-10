package com.tobibur.aidl_server.domain.usecase

import com.tobibur.aidl_server.domain.model.MenuItem
import com.tobibur.aidl_server.domain.repository.MenuItemRepository

class DeleteMenuItemUseCase(
    private val menuItemRepository: MenuItemRepository
) {
    suspend operator fun invoke(menuItem: MenuItem) {
        menuItemRepository.deleteMenuItem(menuItem)
    }
}