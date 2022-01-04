package id.com.android.laundry.feature.presenterlayer

import android.graphics.Bitmap
import android.util.Base64
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import id.com.android.laundry.feature.viewlayer.ViewRegister
import id.com.android.laundry.model.ModelLogin
import id.com.android.laundry.repository.RepositorySession
import id.com.android.laundry.repository.RepositorySettings
import org.json.JSONObject
import org.json.JSONTokener
import java.io.ByteArrayOutputStream
import javax.inject.Inject


class PresenterRegister : PresenterBase<ViewRegister> {

    private var repositorySettings: RepositorySettings
    private var modelUser : ModelLogin? = null
    private var mRepositorySession : RepositorySession?     = null

    @Inject
    constructor(repositorySetting: RepositorySettings, mRepositorySession: RepositorySession) {
        this.repositorySettings     = repositorySetting
        this.mRepositorySession = mRepositorySession
    }

    fun regis(
        username: String,
        name: String,
        password: String,
        phone: String,
        photo: Bitmap?,
        alamat: String,
        birthday: String,
        gender: String,
        kurir: String,
        level: String
    ) {
        if(photo==null){
            AndroidNetworking.post("https://onlinelaundryrianto.000webhostapp.com/api_laundry/api_usernew.php")
                .addBodyParameter("username", username)
                .addBodyParameter("nama", name)
                .addBodyParameter("level", level)
                .addBodyParameter("password", password)
                .addBodyParameter("id", "0")
                .addBodyParameter("phone", phone)
                .addBodyParameter("alamat", alamat)
                .addBodyParameter("birthday", birthday)
                .addBodyParameter("gender",gender)
                .addBodyParameter("kurir",kurir)
                .addBodyParameter("harga","")
                .setTag("Register")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(object : StringRequestListener {
                    override fun onResponse(response: String) {
                        val json = JSONTokener(response).nextValue() as JSONObject
                        var success = json?.get("success") as Int

                        if(success==1){
                            viewLayer?.showSuccessRegis()
                        }else{
                            try {
                                var message = json.get("message") as String
                                viewLayer?.showFailedToast(message)

                            } catch (e: Exception) {
                            }
                        }
                    }

                    override fun onError(anError: ANError) {
                        viewLayer?.showFailedToast("Failed to connect")
                    }
                })
        }else{
            var image = convertImage(photo)

            AndroidNetworking.post("https://onlinelaundryrianto.000webhostapp.com/api_laundry/api_usernew.php")
                .addBodyParameter("username", username)
                .addBodyParameter("nama", name)
                .addBodyParameter("level", level)
                .addBodyParameter("password", password)
                .addBodyParameter("id", "0")
                .addBodyParameter("phone", phone)
                .addBodyParameter("foto", image)
                .addBodyParameter("alamat", alamat)
                .addBodyParameter("birthday", birthday)
                .addBodyParameter("gender",gender)
                .addBodyParameter("kurir",kurir)
                .addBodyParameter("harga","")
                .setTag("Register")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(object : StringRequestListener {
                    override fun onResponse(response: String) {
                        val json = JSONTokener(response).nextValue() as JSONObject
                        var success = json?.get("success") as Int

                        if(success==1){
                            viewLayer?.showSuccessRegis()
                        }else{
                            try {
                                var message = json.get("message") as String
                                viewLayer?.showFailedToast(message)

                            } catch (e: Exception) {
                            }
                        }
                    }

                    override fun onError(anError: ANError) {
                        viewLayer?.showFailedToast("Failed to connect")
                    }
                })
        }

    }


    fun regisLaundry(
        username: String,
        name: String,
        password: String,
        phone: String,
        photo: Bitmap?,
        alamat: String,
        birthday: String,
        gender: String,
        kurir: String,
        level: String,
        harga: String,
        namabank: String,
        rekening_input: String
    ) {
        var rekening = rekening_input
        var bank = namabank
        if(rekening=="") {
            rekening = "-"
        }
        if(bank ==""){
            bank = "-"
        }
        if(photo==null){

            AndroidNetworking.post("https://onlinelaundryrianto.000webhostapp.com/api_laundry/api_user_auth.php")
                .addBodyParameter("username", username)
                .addBodyParameter("nama", name)
                .addBodyParameter("level", level)
                .addBodyParameter("password", password)
                .addBodyParameter("id", "0")
                .addBodyParameter("phone", phone)
                .addBodyParameter("alamat", alamat)
                .addBodyParameter("birthday", birthday)
                .addBodyParameter("gender",gender)
                .addBodyParameter("kurir",kurir)
                .addBodyParameter("harga",harga)
                .addBodyParameter("bank",bank)
                .addBodyParameter("rekening",rekening)
                .setTag("Register")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(object : StringRequestListener {
                    override fun onResponse(response: String) {
                        val json = JSONTokener(response).nextValue() as JSONObject
                        var success = json?.get("success") as Int

                        if(success==1){
                            viewLayer?.showSuccessRegis()
                        }else{
                            try {
                                var message = json.get("message") as String
                                viewLayer?.showFailedToast(message)

                            } catch (e: Exception) {
                            }
                        }
                    }

                    override fun onError(anError: ANError) {
                        viewLayer?.showFailedToast("Failed to connect")
                    }
                })
        }else{
            var image = convertImage(photo)

            AndroidNetworking.post("https://onlinelaundryrianto.000webhostapp.com/api_laundry/api_user_auth.php")
                .addBodyParameter("username", username)
                .addBodyParameter("nama", name)
                .addBodyParameter("level", level)
                .addBodyParameter("password", password)
                .addBodyParameter("id", "0")
                .addBodyParameter("phone", phone)
                .addBodyParameter("foto", image)
                .addBodyParameter("alamat", alamat)
                .addBodyParameter("birthday", birthday)
                .addBodyParameter("gender",gender)
                .addBodyParameter("kurir",kurir)
                .addBodyParameter("harga",harga)
                .addBodyParameter("bank",bank)
                .addBodyParameter("rekening",rekening)
                .setTag("Register")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(object : StringRequestListener {
                    override fun onResponse(response: String) {
                        val json = JSONTokener(response).nextValue() as JSONObject
                        var success = json?.get("success") as Int

                        if(success==1){
                            viewLayer?.showSuccessRegis()
                        }else{
                            try {
                                var message = json.get("message") as String
                                viewLayer?.showFailedToast(message)

                            } catch (e: Exception) {
                            }
                        }
                    }

                    override fun onError(anError: ANError) {
                        viewLayer?.showFailedToast("Failed to connect")
                    }
                })
        }

    }


    fun convertImage(bitmap: Bitmap): String? {
        val byteArrayOutputStreamObject: ByteArrayOutputStream
        byteArrayOutputStreamObject = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject)
        val byteArrayVar = byteArrayOutputStreamObject.toByteArray()
        return Base64.encodeToString(byteArrayVar, Base64.DEFAULT)
    }

    fun getUser(){
        modelUser = mRepositorySession?.userSession
        viewLayer?.showUser(modelUser)
    }

    fun updateRoleUser(
        id: String?,
        username: String,
        name: String,
        pass: String,
        nohp: String,
        alamat: String
    ) {
        AndroidNetworking.post("https://onlinelaundryrianto.000webhostapp.com/api_laundry/api_edit_profile_user.php")
            .addBodyParameter("id",id)
            .addBodyParameter("username", username)
            .addBodyParameter("fullname", name)
            .addBodyParameter("pass", pass)
            .addBodyParameter("phone", nohp)
            .addBodyParameter("alamat", alamat)
            .addBodyParameter("opsi" ,"update")
            .setTag("Register")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsString(object : StringRequestListener {
                override fun onResponse(response: String) {
                    if(response.contains("Berhasil mengubah user")){
                        viewLayer?.showSuccessRegis()
                    }else{
                        viewLayer?.showFailedToast("Gagal mengubah user")
                    }
                }

                override fun onError(anError: ANError) {
                    viewLayer?.showFailedToast("Failed to connect")
                }
            })
    }

    fun updateRoleLaundry(
        id: String?,
        username: String,
        name: String,
        pass: String,
        nohp: String,
        alamat: String,
        bank: String,
        rekening: String,
        kurir: String
    ) {
        AndroidNetworking.post("https://onlinelaundryrianto.000webhostapp.com/api_laundry/api_edit_profile_laundry.php")
            .addBodyParameter("id",id)
            .addBodyParameter("username", username)
            .addBodyParameter("fullname", name)
            .addBodyParameter("pass", pass)
            .addBodyParameter("phone", nohp)
            .addBodyParameter("alamat", alamat)
            .addBodyParameter("bank", bank)
            .addBodyParameter("rekening", rekening)
            .addBodyParameter("kurir", kurir)
            .addBodyParameter("opsi" ,"update")
            .setTag("Register")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsString(object : StringRequestListener {
                override fun onResponse(response: String) {
                    if(response.contains("Berhasil mengubah user")){
                        viewLayer?.showSuccessRegis()
                    }else{
                        viewLayer?.showFailedToast("Gagal mengubah user")
                    }
                }

                override fun onError(anError: ANError) {
                    viewLayer?.showFailedToast("Failed to connect")
                }
            })
    }

    fun updateProfilePicture(bitmap: Bitmap) {
        var image = convertImage(bitmap)
        AndroidNetworking.post("https://onlinelaundryrianto.000webhostapp.com/api_laundry/api_update_profile_picture.php")

            .addBodyParameter("foto",image)
            .addBodyParameter("id",modelUser?.id)
            .addBodyParameter("opsi","update")
            .setTag("Register")
            .setPriority(Priority.HIGH)
            .build()
            .getAsString(object : StringRequestListener {
                override fun onResponse(response: String) {
                    if(response.contains("Berhasil")){
                        viewLayer?.showSuccessRegis()

                    }else{
                        viewLayer?.showFailedToast("Gagal upload profile picture")

                    }

                }

                override fun onError(anError: ANError) {
                    viewLayer?.showFailedToast("Gagal upload profile picture")

                }
            })
    }


}
