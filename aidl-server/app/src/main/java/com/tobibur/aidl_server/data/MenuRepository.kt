package com.tobibur.aidl_server.data

object MenuRepository {
    // Central source of truth
    private val menuList = mutableListOf(
        MenuItem(1, "Pizza", false),
        MenuItem(2, "Burger", false),
        MenuItem(3, "Pasta", false)
    )

    fun getMenuList(): MutableList<MenuItem> = menuList

    fun updateItem(item: MenuItem) {
        val index = menuList.indexOfFirst { it.id == item.id }
        if (index != -1) {
            menuList[index] = item
        }
    }
}