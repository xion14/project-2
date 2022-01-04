package id.com.android.laundry.feature

import id.com.android.laundry.model.ModelPesanan

interface InterfaceChoosePesanan {
    fun onPesanClicked(position: Int, content: ModelPesanan, reguler: String)
    fun onDetailClilcked()
}