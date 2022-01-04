package id.com.android.laundry.feature.viewlayer

import id.com.android.laundry.model.ModelLogin


interface ViewMain : ViewBase {
    fun showRole(level: ModelLogin?)
    fun notLogin()
}