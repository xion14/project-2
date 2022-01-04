package id.com.android.laundry.feature.viewlayer

import id.com.android.laundry.model.ModelLogin


interface ViewHome : ViewBase {
    fun showRole(level: ModelLogin?)
    fun notLogin()
}