package id.com.android.laundry.feature.viewlayer

import id.com.android.laundry.model.ModelListPesananLaundry
import id.com.android.laundry.model.ModelLogin


interface ViewListUser : ViewBase {
    fun showLoadingProcess()
    fun showToast(toast: String?)
    fun showListUser(content : ArrayList<ModelLogin>?)
    fun showSuccessDeleteUser(position : Int)
}
