package com.tobibur.aidl_server.domain.model

import android.os.Parcel
import android.os.Parcelable

data class MenuItem(
    var id: Int = 0,
    var title: String = "",
    var isChecked: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this() {
        readFromParcel(parcel)
    }

    fun readFromParcel(parcel: Parcel) {
        id = parcel.readInt()
        title = parcel.readString() ?: ""
        isChecked = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeByte(if (isChecked) 1 else 0)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<MenuItem> {
        override fun createFromParcel(parcel: Parcel): MenuItem = MenuItem(parcel)
        override fun newArray(size: Int): Array<MenuItem?> = arrayOfNulls(size)
    }
}
