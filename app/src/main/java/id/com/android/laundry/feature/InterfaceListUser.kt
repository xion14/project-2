package id.com.android.laundry.feature

import id.com.android.laundry.model.ModelLogin


interface InterfaceListUser {
    fun updateUser(position: Int, content: ModelLogin?)
    fun deleteUser(position: Int, content: ModelLogin?)
}