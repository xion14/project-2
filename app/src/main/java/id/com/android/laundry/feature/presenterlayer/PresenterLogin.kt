package id.com.android.laundry.feature.presenterlayer

import id.com.android.laundry.controller.ControllerEndpoint
import id.com.android.laundry.feature.viewlayer.ViewLogin
import id.com.android.laundry.model.ModelLogin
import id.com.android.laundry.model.ModelMessage
import id.com.android.laundry.repository.RepositorySession
import id.com.android.laundry.repository.RepositorySettings
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PresenterLogin : PresenterBase<ViewLogin> {

    private var serviceApi: ControllerEndpoint
    private var repositorySettings: RepositorySettings?
    private var mRepositorySession : RepositorySession?     = null
    private var modelUser : ModelLogin? = null

    @Inject
    constructor(controllerServiceApi: ControllerEndpoint,repositorySetting: RepositorySettings, mRepositorySession: RepositorySession) {
        this.serviceApi = controllerServiceApi
        this.repositorySettings     = repositorySetting
        this.mRepositorySession = mRepositorySession
    }

    fun login(email: String, pass: String) {
        serviceApi.postUser(email,pass).enqueue(object : Callback<ModelLogin> { override fun onResponse(call: Call<ModelLogin>, response: Response<ModelLogin>) {
            if (response.isSuccessful) {
                if(response.body()?.success==1){
                    mRepositorySession!!.userSession = response.body()
                    modelUser = mRepositorySession?.userSession
                    viewLayer?.showSuccessLogin()
                }else{
                    viewLayer?.showFailedToast(response.body()?.message.toString())
                }
            } else {
                viewLayer?.showFailedToast(response.body()?.message.toString())

            }
        }

            override fun onFailure(call: Call<ModelLogin>, throwable: Throwable) {
                viewLayer?.showFailedToast("Failed to connect")
            }
        })
    }

    fun edit(email: String, pass: String, id : String) {
        serviceApi.postEditByAdmin(email,pass,id,"update").enqueue(object : Callback<ModelMessage> { override fun onResponse(call: Call<ModelMessage>, response: Response<ModelMessage>) {
           if(response.isSuccessful){
               viewLayer?.showSuccessLogin()
           }else{
               viewLayer?.showFailedToast("Gagal mengubah data")
           }
        }

            override fun onFailure(call: Call<ModelMessage>, throwable: Throwable) {
                viewLayer?.showFailedToast("Gagal mengubah data")
            }
        })
    }





}
