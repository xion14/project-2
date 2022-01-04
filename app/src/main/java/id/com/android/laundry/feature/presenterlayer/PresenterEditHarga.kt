package id.com.android.laundry.feature.presenterlayer

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.com.android.laundry.controller.ControllerEndpoint
import id.com.android.laundry.feature.viewlayer.ViewEditHarga
import id.com.android.laundry.model.ModelHarga
import id.com.android.laundry.model.ModelLogin
import id.com.android.laundry.model.ModelMessage
import id.com.android.laundry.repository.RepositorySession
import id.com.android.laundry.repository.RepositorySettings
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import javax.inject.Inject


class PresenterEditHarga : PresenterBase<ViewEditHarga> {

    private var serviceApi: ControllerEndpoint
    private var repositorySettings: RepositorySettings?
    private var mRepositorySession : RepositorySession?     = null

    @Inject
    constructor(repositorySetting: RepositorySettings, mRepositorySession: RepositorySession,controllerServiceApi: ControllerEndpoint) {
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


    fun test(){


        val jsonArray = JSONArray()


        for(i in 0 until 3){
            val json = JSONObject()
            try {
                json.put("name", "Cuci")
                json.put("reguler", "5000")
                json.put("express", "5000")
                json.put("type", "pc")
                jsonArray.put(json)
            } catch (e: JSONException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }

        }
        val studentsObj = JSONObject()
        studentsObj.put("Laundry", jsonArray)


        val jsonStr = studentsObj.toString()

        println("jsonString: $jsonStr")


    }

    fun updateHarga(collectionHarga: ArrayList<ModelHarga>?) {
        val jsonArray = JSONArray()


        for(i in 0 until collectionHarga!!.size){
            val json = JSONObject()
            try {
                json.put("name", collectionHarga[i].name)
                json.put("reguler", collectionHarga[i].reguler)
                json.put("express", collectionHarga[i].express)
                json.put("type", collectionHarga[i].type)
                jsonArray.put(json)
            } catch (e: JSONException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }

        }


        val jsonStr = jsonArray.toString()
        requestEditHarga(jsonStr)
    }

    private fun requestEditHarga(jsonStr: String) {

        serviceApi.editHargaLaundry(mRepositorySession?.userSession?.id.toString(),jsonStr,"update").enqueue(object :
            Callback<ModelMessage> { override fun onResponse(call: Call<ModelMessage>, response: Response<ModelMessage>) {
            if (response.isSuccessful) {
                if(response.body()?.success==1){
                    viewLayer?.showToast(response.body()?.message.toString())
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

}