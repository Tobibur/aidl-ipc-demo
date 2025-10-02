package com.tobibur.aidl_server.service

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.RemoteCallbackList
import android.util.Log
import com.tobibur.aidl_server.IMenuCallback
import com.tobibur.aidl_server.IMenuService
import com.tobibur.aidl_server.domain.model.MenuItem
import com.tobibur.aidl_server.domain.usecase.GetMenuItemByIdUseCase
import com.tobibur.aidl_server.domain.usecase.UpdateMenuItemUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MenuService : Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val listeners = RemoteCallbackList<IMenuCallback>()

    @Inject
    lateinit var updateMenuItemUseCase: UpdateMenuItemUseCase

    @Inject
    lateinit var getMenuItemByIdUseCase: GetMenuItemByIdUseCase
    private val mainMenuList = mutableListOf<MenuItem>()

    companion object {
        private const val TAG = "MenuService"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: started")
        val menuList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableArrayListExtra("menu_list", MenuItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            (intent?.getParcelableArrayListExtra<MenuItem>("menu_list"))
        }
        mainMenuList.clear()
        if (menuList != null) {
            mainMenuList.addAll(menuList)
        }
        notifyMenuItemsChanged()
        // Use menuList as needed
        return super.onStartCommand(intent, flags, startId)
    }


    private val binder = object : IMenuService.Stub() {
        override fun selectMenuItem(item: MenuItem) {
            // Update the menu item as selected in the database
            serviceScope.launch {
                updateMenuItemUseCase(item)
            }
        }

        override fun registerListener(listener: IMenuCallback) {
            listeners.register(listener)
            listener.onMenuItemSelected(mainMenuList)
        }

        override fun unregisterListener(listener: IMenuCallback) {
            listeners.unregister(listener)
        }
    }

    private fun notifyMenuItemsChanged() {
        // Broadcast to all registered clients
        val n = listeners.beginBroadcast()
        for (i in 0 until n) {
            listeners.getBroadcastItem(i)
                .onMenuItemSelected(mainMenuList)
        }
        listeners.finishBroadcast()
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.d(TAG, "onBind called")
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}
