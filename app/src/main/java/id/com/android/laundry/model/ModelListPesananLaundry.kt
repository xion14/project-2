package id.com.android.laundry.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@SuppressLint("ParcelCreator")
@Parcelize
data class ModelListPesananLaundry(
    @SerializedName("id") var id: String?,
    @SerializedName("id_laundry") var id_laundry: String?,
    @SerializedName("nama_laundry") var nama_laundry: String?,
    @SerializedName("id_user") var id_user: String?,
    @SerializedName("nama_user") var nama_user: String?,
    @SerializedName("no_user") var no_user: String?,
    @SerializedName("alamat_user") var alamat_user: String?,
    @SerializedName("foto_user") var foto_user: String?,
    @SerializedName("pesanan") var pesanan: String?,
    @SerializedName("kurir") var kurir: String?,
    @SerializedName("total_harga") var total_harga: String?,
    @SerializedName("isAccept") var isAccept: String?,
    @SerializedName("bukti") var bukti: String?,
    @SerializedName("bank") var bank: String?,
    @SerializedName("rekening") var rekening: String?

    ) : Parcelable
