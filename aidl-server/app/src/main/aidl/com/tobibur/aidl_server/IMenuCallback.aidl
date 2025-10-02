package com.tobibur.aidl_server;

import com.tobibur.aidl_server.domain.model.MenuItem;

interface IMenuCallback {
    void onMenuItemSelected(in List<MenuItem> menuItems);
}