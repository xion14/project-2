package id.com.android.laundry.feature.presenterlayer

import android.util.Log
import id.com.android.laundry.controller.ControllerEndpoint
import id.com.android.laundry.feature.viewlayer.ViewListLaundry
import id.com.android.laundry.model.ModelLogin
import id.com.android.laundry.repository.RepositorySession
import id.com.android.laundry.repository.RepositorySettings
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class PresenterListLaundry : PresenterBase<ViewListLaundry> {
    private var serviceApi: ControllerEndpoint
    private var repositorySettings: RepositorySettings?
    private var mRepositorySession : RepositorySession?     = null

    @Inject
    constructor( controllerServiceApi: ControllerEndpoint,repositorySetting: RepositorySettings, mRepositorySession: RepositorySession) {
        this.serviceApi = controllerServiceApi
        this.repositorySettings     = repositorySetting
        this.mRepositorySession = mRepositorySession
    }


    fun loadListLaundry() {
        viewLayer?.showLoadingProcess()
        serviceApi.getLaundry().enqueue(object : Callback<ArrayList<ModelLogin>> {
            override fun onFailure(call: Call<ArrayList<ModelLogin>>, t: Throwable) {
                Log.d("failed","asd")
                viewLayer?.showError()
            }

            override fun onResponse(
                call: Call<ArrayList<ModelLogin>>,
                response: Response<ArrayList<ModelLogin>>
            ) {
                if(response.isSuccessful){
                    viewLayer?.showLaundry(response.body())
                }
            }
        })
    }


}
