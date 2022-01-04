package id.com.android.laundry.feature

import id.com.android.laundry.model.ModelListPesananLaundry

interface InterfaceListPesananLaundry {
    fun onPesananRemove(position: Int,content: ModelListPesananLaundry)
    fun onPesananAccept(position: Int,content: ModelListPesananLaundry)
    fun onPesananDetail(position: Int,content: ModelListPesananLaundry)
    fun onUploadBukti(position: Int,content: ModelListPesananLaundry)
    fun onLihatbukti(position: Int,content: ModelListPesananLaundry)
}