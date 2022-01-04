package id.com.android.laundry.feature.viewlayer

import id.com.android.laundry.model.ModelHarga
import id.com.android.laundry.model.ModelLogin


interface ViewEditHarga : ViewBase {
    fun showData(arrayHarga: ArrayList<ModelHarga>)
    fun showToast(message: String)
}