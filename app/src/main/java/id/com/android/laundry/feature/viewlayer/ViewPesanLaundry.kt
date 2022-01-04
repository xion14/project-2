package id.com.android.laundry.feature.viewlayer

import id.com.android.laundry.model.ModelHarga


interface ViewPesanLaundry : ViewBase {
    fun showFailedToast(message: String)
    fun showSuccess(message: String)
    fun showData(arrayHarga: ArrayList<ModelHarga>)
    fun showError(error : String)
}