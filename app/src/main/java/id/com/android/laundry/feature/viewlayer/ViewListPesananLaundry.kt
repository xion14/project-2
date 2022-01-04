package id.com.android.laundry.feature.viewlayer

import id.com.android.laundry.model.ModelListPesananLaundry


interface ViewListPesananLaundry : ViewBase {
    fun showLoadingProcess()
    fun showError()
    fun showListPesanan(body: ArrayList<ModelListPesananLaundry>?)
    fun showListPesananUser(body: ArrayList<ModelListPesananLaundry>?)
    fun showToast(toast: String)
    fun showSuccessAccept(toString: String, position: Int, accept: String)
    fun showSuccessUpload(toast: String)
    fun showRole(role : String?)
    fun successDelete(position : Int)
}

