package id.com.android.laundry.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@SuppressLint("ParcelCreator")
@Parcelize
data class ModelPesananLaundry(
    @SerializedName("name") var name: String?,
    @SerializedName("jenis") var jenis: String?,
    @SerializedName("harga") var harga: String?,
    @SerializedName("harga_total") var harga_total: String?,
) : Parcelable

