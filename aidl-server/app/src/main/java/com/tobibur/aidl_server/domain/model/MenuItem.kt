package com.tobibur.aidl_server.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuItem(
    val id: Int,
    val title: String,
    var isChecked: Boolean
) : Parcelable

