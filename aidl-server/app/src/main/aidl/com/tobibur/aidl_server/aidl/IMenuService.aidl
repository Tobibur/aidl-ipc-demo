package com.tobibur.aidl_server.aidl;

import com.tobibur.aidl_server.aidl.IMenuCallback;

interface IMenuService {
    void selectMenuItem(int itemId);
    void registerListener(IMenuCallback listener);
    void unregisterListener(IMenuCallback listener);
}