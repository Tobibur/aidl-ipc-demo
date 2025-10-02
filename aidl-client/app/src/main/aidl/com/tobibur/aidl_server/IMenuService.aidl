package com.tobibur.aidl_server;

import com.tobibur.aidl_server.IMenuCallback;
import com.tobibur.aidl_server.domain.model.MenuItem;

interface IMenuService {
    void selectMenuItem(inout MenuItem item);
    void registerListener(IMenuCallback listener);
    void unregisterListener(IMenuCallback listener);
}
