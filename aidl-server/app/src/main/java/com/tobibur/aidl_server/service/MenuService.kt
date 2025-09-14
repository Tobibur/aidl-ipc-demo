package com.tobibur.aidl_server.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteCallbackList
import com.tobibur.aidl_server.aidl.IMenuCallback
import com.tobibur.aidl_server.aidl.IMenuService

class MenuService : Service() {

    private val listeners = RemoteCallbackList<IMenuCallback>()

    private val binder = object : IMenuService.Stub() {
        override fun selectMenuItem(itemId: Int) {
            // Broadcast to all registered clients
            val n = listeners.beginBroadcast()
            for (i in 0 until n) {
                listeners.getBroadcastItem(i).onMenuItemSelected(itemId)
            }
            listeners.finishBroadcast()
        }

        override fun registerListener(listener: IMenuCallback) {
            listeners.register(listener)
        }

        override fun unregisterListener(listener: IMenuCallback) {
            listeners.unregister(listener)
        }
    }

    override fun onBind(intent: Intent?): IBinder = binder
}
