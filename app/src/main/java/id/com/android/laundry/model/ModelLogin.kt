package id.com.android.laundry.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@SuppressLint("ParcelCreator")
@Parcelize
data class ModelLogin(
    @SerializedName("success") var success: Int?,
    @SerializedName("message") var message: String?,
    @SerializedName("id") var id: String?,
    @SerializedName("username") var username: String?,
    @SerializedName("level") var level: String?,
    @SerializedName("phone") var phone: String?,
    @SerializedName("fullname") var fullname: String?,
    @SerializedName("foto") var foto: String?,
    @SerializedName("alamat") var alamat: String?,
    @SerializedName("birthday") var birthday: String?,
    @SerializedName("kurir") var kurir: String?,
    @SerializedName("gender") var gender: String?,
    @SerializedName("harga") var harga: String?,
    @SerializedName("bank") var bank: String?,
    @SerializedName("rekening") var rekening: String?
) : Parcelable

