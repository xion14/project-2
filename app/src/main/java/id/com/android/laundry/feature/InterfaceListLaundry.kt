package id.com.android.laundry.feature

import id.com.android.laundry.model.ModelLogin

interface InterfaceListLaundry {
    fun onPesanClicked(content: ModelLogin?)
    fun onDetailClilcked(content: ModelLogin?)
}