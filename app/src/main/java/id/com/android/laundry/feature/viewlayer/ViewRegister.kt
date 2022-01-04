package id.com.android.laundry.feature.viewlayer

import id.com.android.laundry.model.ModelLogin


interface ViewRegister : ViewBase {
    fun showFailedToast(message: String)
    fun showSuccessRegis()
    fun showUser(modelLogin : ModelLogin?)
}