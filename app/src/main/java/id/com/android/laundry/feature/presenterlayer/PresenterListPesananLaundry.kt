package id.com.android.laundry.feature.presenterlayer

import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import id.com.android.laundry.controller.ControllerEndpoint
import id.com.android.laundry.feature.viewlayer.ViewListPesananLaundry
import id.com.android.laundry.model.ModelListPesananLaundry
import id.com.android.laundry.model.ModelLogin
import id.com.android.laundry.model.ModelMessage
import id.com.android.laundry.repository.RepositorySession
import id.com.android.laundry.repository.RepositorySettings
import id.com.android.laundry.tools.UtilConstant
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import javax.inject.Inject


class PresenterListPesananLaundry : PresenterBase<ViewListPesananLaundry> {
    private var serviceApi: ControllerEndpoint
    private var repositorySettings: RepositorySettings?
    private var mRepositorySession : RepositorySession?     = null
    private var modelUser : ModelLogin? = null
    @Inject
    constructor(controllerServiceApi: ControllerEndpoint, repositorySetting: RepositorySettings, mRepositorySession: RepositorySession) {
        this.serviceApi = controllerServiceApi
        this.repositorySettings     = repositorySetting
        this.mRepositorySession = mRepositorySession
    }


    fun getRole(){
        modelUser = mRepositorySession?.userSession
        viewLayer?.showRole(modelUser?.level)
    }
    fun loadTransaction(isAccept : String){
        modelUser = mRepositorySession?.userSession
        if(modelUser?.level == UtilConstant.LEVEL_USER){
            serviceApi.getPesananLaundryUser(modelUser?.id.toString(),isAccept).enqueue(object : Callback<ArrayList<ModelListPesananLaundry>> {
                override fun onFailure(call: Call<ArrayList<ModelListPesananLaundry>>, t: Throwable) {
                    Log.d("failed","asd")
                    viewLayer?.showError()
                }

                override fun onResponse(
                    call: Call<ArrayList<ModelListPesananLaundry>>,
                    response: Response<ArrayList<ModelListPesananLaundry>>
                ) {
                    if(response.isSuccessful){
                        viewLayer?.showError()
                        viewLayer?.showListPesananUser(response.body())
                    }else{
                        viewLayer?.showError()

                    }
                }
            })
        }else{
            serviceApi.getPesananLaundry(modelUser?.id.toString(),isAccept).enqueue(object : Callback<ArrayList<ModelListPesananLaundry>> {
                override fun onFailure(call: Call<ArrayList<ModelListPesananLaundry>>, t: Throwable) {
                    Log.d("failed","asd")
                    viewLayer?.showError()
                }

                override fun onResponse(
                    call: Call<ArrayList<ModelListPesananLaundry>>,
                    response: Response<ArrayList<ModelListPesananLaundry>>
                ) {
                    if(response.isSuccessful){
                        viewLayer?.showListPesananUser(response.body())
                    }else{
                        viewLayer?.showError()

                    }
                }
            })
        }
    }

    fun loadPesanan() {
        viewLayer?.showLoadingProcess()
        modelUser = mRepositorySession?.userSession
        serviceApi.getPesananLaundry(modelUser?.id.toString(),"1").enqueue(object : Callback<ArrayList<ModelListPesananLaundry>> {
            override fun onFailure(call: Call<ArrayList<ModelListPesananLaundry>>, t: Throwable) {
                Log.d("failed","asd")
                viewLayer?.showError()
            }

            override fun onResponse(
                call: Call<ArrayList<ModelListPesananLaundry>>,
                response: Response<ArrayList<ModelListPesananLaundry>>
            ) {
                if(response.isSuccessful){
                    viewLayer?.showListPesanan(response.body())
                }else{
                    viewLayer?.showError()

                }
            }
        })
    }

    fun loadPesananUser() {
        viewLayer?.showLoadingProcess()
        modelUser = mRepositorySession?.userSession
        if(modelUser?.level == UtilConstant.LEVEL_USER){
            serviceApi.getPesananLaundryUser(modelUser?.id.toString(),"1").enqueue(object : Callback<ArrayList<ModelListPesananLaundry>> {
                override fun onFailure(call: Call<ArrayList<ModelListPesananLaundry>>, t: Throwable) {
                    Log.d("failed","asd")
                    viewLayer?.showError()
                }

                override fun onResponse(
                    call: Call<ArrayList<ModelListPesananLaundry>>,
                    response: Response<ArrayList<ModelListPesananLaundry>>
                ) {
                    if(response.isSuccessful){
                        viewLayer?.showListPesananUser(response.body())
                    }else{
                        viewLayer?.showError()

                    }
                }
            })
        }else{
            serviceApi.getPesananLaundry(modelUser?.id.toString(),"1").enqueue(object : Callback<ArrayList<ModelListPesananLaundry>> {
                override fun onFailure(call: Call<ArrayList<ModelListPesananLaundry>>, t: Throwable) {
                    Log.d("failed","asd")
                    viewLayer?.showError()
                }

                override fun onResponse(
                    call: Call<ArrayList<ModelListPesananLaundry>>,
                    response: Response<ArrayList<ModelListPesananLaundry>>
                ) {
                    if(response.isSuccessful){
                        viewLayer?.showListPesananUser(response.body())
                    }else{
                        viewLayer?.showError()

                    }
                }
            })
        }

    }

    fun acceptPesanan(content: ModelListPesananLaundry, position: Int){
        var isAccept : String = ""
        if(content.isAccept=="0"){
            isAccept="1"
        }else if(content.isAccept=="1"){
            isAccept="2"
        }
        viewLayer?.showLoadingProcess()
        serviceApi.editPesananLaundry(content.id.toString(),isAccept,"update").enqueue(object :
            Callback<ModelMessage> { override fun onResponse(call: Call<ModelMessage>, response: Response<ModelMessage>) {
            if (response.isSuccessful) {
                if(response.body()?.success==1){
                    viewLayer?.showSuccessAccept(response.body()?.message.toString(),position,isAccept)
                }else{
                    viewLayer?.showToast(response.body()?.message.toString())
                }
            } else {
                viewLayer?.showToast(response.body()?.message.toString())

            }
        }

            override fun onFailure(call: Call<ModelMessage>, t: Throwable) {
                viewLayer?.showToast("No internet connection")
            }
        })
    }
    fun acceptPesananDetaill(id: String){
        viewLayer?.showLoadingProcess()
        serviceApi.editPesananLaundry(id,"1","update").enqueue(object :
            Callback<ModelMessage> { override fun onResponse(call: Call<ModelMessage>, response: Response<ModelMessage>) {
            if (response.isSuccessful) {
                if(response.body()?.success==1){
                    viewLayer?.showSuccessAccept(response.body()?.message.toString(), 0, "1")
                }else{
                    viewLayer?.showToast(response.body()?.message.toString())
                }
            } else {
                viewLayer?.showToast(response.body()?.message.toString())

            }
        }

            override fun onFailure(call: Call<ModelMessage>, t: Throwable) {
                viewLayer?.showToast("No internet connection")
            }
        })
    }


    fun uploadBukti(bitmap: Bitmap, idUpload: String) {
        var image = convertImage(bitmap)
        AndroidNetworking.post("https://onlinelaundryrianto.000webhostapp.com/api_laundry/api_update_bukti.php")

            .addBodyParameter("bukti",image)
            .addBodyParameter("id",idUpload)
            .addBodyParameter("opsi","update")
            .setTag("Register")
            .setPriority(Priority.HIGH)
            .build()
            .getAsString(object : StringRequestListener {
                override fun onResponse(response: String) {
                    if(response.contains("Berhasil upload bukti")){
                        viewLayer?.showSuccessUpload("Sukses upload bukti transfer")

                    }else{
                        viewLayer?.showToast("Gagal upload bukti transfer")

                    }

                }

                override fun onError(anError: ANError) {
                    viewLayer?.showToast("Gagal upload bukti transfer")

                }
            })
    }

    fun convertImage(bitmap: Bitmap): String? {
        val byteArrayOutputStreamObject: ByteArrayOutputStream
        byteArrayOutputStreamObject = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject)
        val byteArrayVar = byteArrayOutputStreamObject.toByteArray()
        return Base64.encodeToString(byteArrayVar, Base64.DEFAULT)
    }

    fun deletePesanan(content: ModelListPesananLaundry, position : Int) {
        serviceApi.getDeleteUser(content?.id.toString())
            .enqueue(object : Callback<ResponseBody> { override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.isSuccessful) {
                    viewLayer?.successDelete(position)
                }
            }

                override fun onFailure(call: Call<ResponseBody>, throwable: Throwable) {
                    viewLayer?.showToast("Failed to connect")
                }
            })
    }

}
