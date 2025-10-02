package com.tobibur.aidl_server.domain.usecase

import com.tobibur.aidl_server.domain.repository.MenuItemRepository

class GetMenuItemByIdUseCase(
    private val menuItemRepository: MenuItemRepository
) {
    suspend operator fun invoke(id: Int) = menuItemRepository.getMenuItemById(id)
}