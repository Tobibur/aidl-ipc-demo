package com.tobibur.aidl_server.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tobibur.aidl_server.domain.model.MenuItem
import com.tobibur.aidl_server.domain.usecase.UpdateMenuItemUseCase
import com.tobibur.aidl_server.domain.usecase.DeleteMenuItemUseCase
import com.tobibur.aidl_server.domain.usecase.GetMenuItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(
    getMenuItemsUseCase: GetMenuItemsUseCase,
    private val updateMenuItemUseCase: UpdateMenuItemUseCase,
    private val deleteMenuItemUseCase: DeleteMenuItemUseCase
) : ViewModel() {

    val menuItems = getMenuItemsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    fun insertMenuItem(menuItem: MenuItem) {
        viewModelScope.launch {
            updateMenuItemUseCase(menuItem)
        }
    }

    fun deleteMenuItem(menuItem: MenuItem) {
        viewModelScope.launch {
            deleteMenuItemUseCase(menuItem)
        }
    }
}