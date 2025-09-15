package com.tobibur.aidl_client.ui.screens

import androidx.lifecycle.ViewModel
import com.tobibur.aidl_client.domain.model.MenuItem
import kotlinx.coroutines.flow.MutableStateFlow

class MainMenuViewModel : ViewModel() {

    private val _menuItemsState = listOf(
        MenuItem(1, "Menu Item 1", false),
        MenuItem(2, "Menu Item 2", true),
        MenuItem(3, "Menu Item 3", false)
    )
    val menuItemsFlow: MutableStateFlow<List<MenuItem>> = MutableStateFlow(_menuItemsState)


    fun insertMenuItem(menuItem: MenuItem) {
        val currentList = menuItemsFlow.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == menuItem.id }
        if (index != -1) {
            currentList[index] = menuItem
            menuItemsFlow.value = currentList
        }
    }

    fun deleteMenuItem(menuItem: MenuItem) {
        val currentList = menuItemsFlow.value.toMutableList()
        currentList.removeAll { it.id == menuItem.id }
        menuItemsFlow.value = currentList
    }
}