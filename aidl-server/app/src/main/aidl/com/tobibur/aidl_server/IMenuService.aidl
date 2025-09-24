package com.tobibur.aidl_server;

import com.tobibur.aidl_server.IMenuCallback;

interface IMenuService {
    void selectMenuItem(int itemId);
    void registerListener(IMenuCallback listener);
    void unregisterListener(IMenuCallback listener);
}