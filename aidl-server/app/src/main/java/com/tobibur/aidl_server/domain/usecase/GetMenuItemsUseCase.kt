package com.tobibur.aidl_server.domain.usecase

import com.tobibur.aidl_server.domain.repository.MenuItemRepository

class GetMenuItemsUseCase(
    private val menuItemRepository: MenuItemRepository
) {
    operator fun invoke() = menuItemRepository.getAllMenuItems()
}