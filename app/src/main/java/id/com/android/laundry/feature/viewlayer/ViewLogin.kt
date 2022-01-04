package id.com.android.laundry.feature.viewlayer


interface ViewLogin : ViewBase {
    fun showLoadingProcess()
    fun showFailedToast(message: String)
    fun showSuccessLogin()
}