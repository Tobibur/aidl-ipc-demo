package com.tobibur.aidl_server.domain.usecase

import android.R.attr.name
import com.tobibur.aidl_server.domain.model.MenuItem
import com.tobibur.aidl_server.domain.repository.MenuItemRepository

class AddMenuItemUseCase(
    private val menuItemRepository: MenuItemRepository
) {
    suspend operator fun invoke(menuItem: MenuItem) {
        menuItemRepository.insertMenuItem(menuItem)
    }
}