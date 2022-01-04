package id.com.android.laundry.feature.presenterlayer

import android.util.Log
import id.com.android.laundry.controller.ControllerEndpoint
import id.com.android.laundry.feature.viewlayer.ViewListUser
import id.com.android.laundry.model.ModelLogin
import id.com.android.laundry.repository.RepositorySession
import id.com.android.laundry.repository.RepositorySettings
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class PresenterListUser : PresenterBase<ViewListUser> {
    private var serviceApi: ControllerEndpoint
    private var repositorySettings: RepositorySettings?
    private var mRepositorySession : RepositorySession?     = null

    @Inject
    constructor(controllerServiceApi: ControllerEndpoint, repositorySetting: RepositorySettings, mRepositorySession: RepositorySession) {
        this.serviceApi = controllerServiceApi
        this.repositorySettings     = repositorySetting
        this.mRepositorySession = mRepositorySession
    }


    fun loadListUser(level : String) {
        viewLayer?.showLoadingProcess()
        serviceApi.getUserList(level).enqueue(object : Callback<ArrayList<ModelLogin>> {
            override fun onFailure(call: Call<ArrayList<ModelLogin>>, t: Throwable) {
                Log.d("failed","asd")
                viewLayer?.showToast("Gagal memuat data")
            }

            override fun onResponse(
                call: Call<ArrayList<ModelLogin>>,
                response: Response<ArrayList<ModelLogin>>
            ) {
                if(response.isSuccessful){
                    viewLayer?.showListUser(response.body())
                }else{
                    viewLayer?.showToast("Gagal memuat data")
                }
            }
        })
    }

    fun deleteUser(position: Int, content: ModelLogin?) {
        serviceApi.getDeleteUser(content?.id.toString())
            .enqueue(object : Callback<ResponseBody> { override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.isSuccessful) {
                    viewLayer?.showSuccessDeleteUser(position)
                }
            }

                override fun onFailure(call: Call<ResponseBody>, throwable: Throwable) {
                    viewLayer?.showToast("Failed to connect")
                }
            })
    }

}
