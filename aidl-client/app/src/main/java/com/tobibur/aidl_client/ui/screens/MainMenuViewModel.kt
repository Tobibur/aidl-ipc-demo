package com.tobibur.aidl_client.ui.screens

import androidx.lifecycle.ViewModel
import com.tobibur.aidl_client.domain.model.MenuItem
import kotlinx.coroutines.flow.MutableStateFlow

class MainMenuViewModel : ViewModel() {

    val menuItemsFlow: MutableStateFlow<List<MenuItem>> = MutableStateFlow(emptyList())


    fun insertMenuItem(menuItem: MenuItem) {
        val currentList = menuItemsFlow.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == menuItem.id }
        if (index != -1) {
            currentList[index] = menuItem
        } else {
            currentList.add(menuItem)
        }
        menuItemsFlow.value = currentList
    }

    fun deleteMenuItem(menuItem: MenuItem) {
        val currentList = menuItemsFlow.value.toMutableList()
        currentList.removeAll { it.id == menuItem.id }
        menuItemsFlow.value = currentList
    }
}