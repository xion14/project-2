package id.com.android.laundry.controller

import id.com.android.laundry.model.ModelListPesananLaundry
import id.com.android.laundry.model.ModelLogin
import id.com.android.laundry.model.ModelMessage
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ControllerEndpoint {

    @FormUrlEncoded
    @POST("api_auth.php")
    fun postUser(@Field("username") username: String, @Field("password") password: String): Call<ModelLogin>

    @FormUrlEncoded
    @POST("api_update_laundry.php")
    fun editHargaLaundry(@Field("id") id: String, @Field("harga") harga: String, @Field("opsi") opsi: String): Call<ModelMessage>

    @FormUrlEncoded
    @POST("api_update_pesanan.php")
    fun editPesananLaundry(@Field("id") id: String, @Field("isAccept") isAccept: String, @Field("opsi") opsi: String): Call<ModelMessage>


    @GET("api_getlaundry.php")
    fun getLaundry(): Call<ArrayList<ModelLogin>>

    @GET("api_get_user_detail.php")
    fun getUserDetail(@Query("id") id:String): Call<ModelLogin>

    @GET("api_get_pesanan_laundry.php")
    fun getPesananLaundry(@Query("id") id:String,
                          @Query("isAccept") isAccept:String): Call<ArrayList<ModelListPesananLaundry>>

    @GET("api_get_pesanan_user.php")
    fun getPesananLaundryUser(@Query("id") id:String,
                              @Query("isAccept") isAccept:String): Call<ArrayList<ModelListPesananLaundry>>

    @FormUrlEncoded
    @POST("api_post_pesanan.php")
    fun postPesanan(@Field("id_laundry") id_laundry: String,
                    @Field("nama_laundry") nama_laundry: String,
                    @Field("id_user") id_user: String,
                    @Field("nama_user") nama_user: String,
                    @Field("no_user") no_user: String,
                    @Field("alamat_user") alamat_user: String,
                    @Field("foto_user") foto_user: String,
                    @Field("pesanan") pesanan: String,
                    @Field("kurir") kurir: String,
                    @Field("total_harga") total_harga: String,
                    @Field("id") id: String,
                    @Field("isAccept") isAccept: String,
                    @Field("bank") bank: String,
                    @Field("rekening") rekening: String): Call<ModelMessage>


    @GET("api_get_list_user.php")
    fun getUserList(@Query("level") level : String): Call<ArrayList<ModelLogin>>

    @GET("api_delete_user.php")
    fun getDeleteUser(@Query("id") id: String): Call<ResponseBody>

    @GET("api_delete_pesanan.php")
    fun getDeletePesanan(@Query("id") id: String): Call<ResponseBody>


    @FormUrlEncoded
    @POST("api_update_user_by_admin.php")
    fun postEditByAdmin(@Field("username") username: String, @Field("password") password: String,@Field("id") id : String, @Field("opsi") opsi : String): Call<ModelMessage>

}