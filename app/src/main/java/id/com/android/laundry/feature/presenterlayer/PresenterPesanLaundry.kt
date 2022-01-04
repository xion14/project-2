package id.com.android.laundry.feature.presenterlayer

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.com.android.laundry.controller.ControllerEndpoint
import id.com.android.laundry.feature.viewlayer.ViewPesanLaundry
import id.com.android.laundry.model.ModelHarga
import id.com.android.laundry.model.ModelLogin
import id.com.android.laundry.model.ModelMessage
import id.com.android.laundry.repository.RepositorySession
import id.com.android.laundry.repository.RepositorySettings
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import javax.inject.Inject

class PresenterPesanLaundry : PresenterBase<ViewPesanLaundry> {

    private var serviceApi: ControllerEndpoint
    private var repositorySettings: RepositorySettings?
    private var mRepositorySession : RepositorySession?     = null
    private var modelUser : ModelLogin? = null

    @Inject
    constructor(repositorySetting: RepositorySettings, mRepositorySession: RepositorySession, controllerServiceApi: ControllerEndpoint) {
        this.repositorySettings     = repositorySetting
        this.mRepositorySession = mRepositorySession
        this.serviceApi = controllerServiceApi
    }


    fun parsingJSON(json: String){

        var arrayHarga : ArrayList<ModelHarga> = ArrayList()
        val gson = Gson()
        val jsonOutput = json
        val listType: Type = object : TypeToken<List<ModelHarga?>?>() {}.getType()
        val posts: List<ModelHarga> = gson.fromJson<List<ModelHarga>>(jsonOutput, listType)

        arrayHarga.addAll(posts)
        viewLayer?.showData(arrayHarga)
    }



    fun requestLaundry(
        idLaundry: String,
        namaLaundry: String,
        pesanan: String,
        kurir: String,
        hargaTotal: String,
        jumlahPesanan: String,
        bankLaundry: String,
        rekeningLaundry: String
    ){
        modelUser = mRepositorySession?.userSession
        Log.d("tesuser", modelUser?.alamat + modelUser?.foto)
        serviceApi.postPesanan(
            idLaundry,
            namaLaundry,
            modelUser?.id.toString(),
            modelUser?.username.toString(),
            modelUser?.phone.toString(),
            modelUser?.alamat.toString(),
            modelUser?.foto.toString(),
            pesanan,
            kurir,
            hargaTotal,
            "0",
            "0",
            bankLaundry,
            rekeningLaundry

        ).enqueue(object : Callback<ModelMessage> {
            override fun onFailure(call: Call<ModelMessage>, t: Throwable) {
                Log.d("failed","asd")
                viewLayer?.showError("Gagal request pesanan")
            }

            override fun onResponse(
                call: Call<ModelMessage>,
                response: Response<ModelMessage>
            ) {
                if(response.isSuccessful){
                    viewLayer?.showSuccess(response.body()?.message.toString())
                }else{
                    viewLayer?.showError("Gagal request pesanan")
                }
            }
        })
    }


}