package id.com.android.laundry.feature.viewlayer

import id.com.android.laundry.model.ModelListPesananLaundry
import id.com.android.laundry.model.ModelLogin


interface ViewListLaundry : ViewBase {
    fun showLoadingProcess()
    fun showError()
    fun showLaundry(body: ArrayList<ModelLogin>?)
}

