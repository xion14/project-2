package id.com.android.laundry.feature.presenterlayer

import android.os.Handler
import android.util.Log
import id.com.android.laundry.controller.ControllerEndpoint
import id.com.android.laundry.feature.viewlayer.ViewHome
import id.com.android.laundry.model.ModelLogin
import id.com.android.laundry.repository.RepositorySession
import id.com.android.laundry.repository.RepositorySettings
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class PresenterHome : PresenterBase<ViewHome> {

    private var splashRunnable: Runnable? = null
    private var splashHandler: Handler? = null
    private var serviceApi: ControllerEndpoint
    private var repositorySettings: RepositorySettings?
    private var mRepositorySession : RepositorySession?     = null

    @Inject
    constructor(controllerServiceApi: ControllerEndpoint,repositorySetting: RepositorySettings, mRepositorySession: RepositorySession) {
        this.repositorySettings     = repositorySetting
        this.mRepositorySession = mRepositorySession
        this.serviceApi = controllerServiceApi
    }


    fun getRole() {
        checkNotNull(mRepositorySession?.userSession){
            viewLayer?.notLogin()
            return
        }
        viewLayer?.showRole(mRepositorySession?.userSession)

    }


    fun updateUser(){
        Log.d("iduser", mRepositorySession?.userSession?.id.toString())
        serviceApi.getUserDetail(mRepositorySession?.userSession?.id.toString()).enqueue(object : Callback<ModelLogin> {
            override fun onFailure(call: Call<ModelLogin>, t: Throwable) {
                viewLayer?.showRole(mRepositorySession?.userSession)

            }

            override fun onResponse(
                call: Call<ModelLogin>,
                response: Response<ModelLogin>
            ) {
                if(response.isSuccessful){
                    mRepositorySession!!.userSession = response.body()
                    viewLayer?.showRole(mRepositorySession?.userSession)
                }else{
                    viewLayer?.showRole(mRepositorySession?.userSession)
                }
            }
        })
    }

}
