package id.com.android.laundry.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class ModelMessage(
    @SerializedName("success") var success: Int?,
    @SerializedName("message") var message: String?
) : Parcelable

