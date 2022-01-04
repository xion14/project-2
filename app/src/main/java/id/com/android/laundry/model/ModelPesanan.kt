package id.com.android.laundry.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@SuppressLint("ParcelCreator")
@Parcelize
data class ModelPesanan(
    @SerializedName("name") var name: String?,
    @SerializedName("reguler") var reguler: String?,
    @SerializedName("express") var express: String?,
    @SerializedName("type") var type: String?,
    var regulerClicked : Boolean?,
    var expressClicked : Boolean
) : Parcelable

