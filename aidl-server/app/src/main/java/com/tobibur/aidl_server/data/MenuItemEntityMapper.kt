package com.tobibur.aidl_server.data

fun MenuItem.toDomain(): com.tobibur.aidl_server.domain.model.MenuItem {
    return com.tobibur.aidl_server.domain.model.MenuItem(
        id = this.id,
        title = this.title,
        isChecked = this.isChecked
    )
}

fun com.tobibur.aidl_server.domain.model.MenuItem.toEntity(): MenuItem {
    return MenuItem(
        id = this.id,
        title = this.title,
        isChecked = this.isChecked
    )
}