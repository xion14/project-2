package id.com.android.laundry.holder

import android.content.Context
import android.util.Log
import android.view.View
import id.com.android.laundry.R
import id.com.android.laundry.feature.InterfaceListPesananLaundry
import id.com.android.laundry.model.ModelListPesananLaundry
import id.com.android.laundry.tools.UtilConstant
import kotlinx.android.synthetic.main.holder_pesanan_accepted.view.*
import org.json.JSONException
import org.json.JSONObject


class HolderPesananAccepted(
    itemView: View?,
    context: Context?,
    interfaceListPesananLaundry: InterfaceListPesananLaundry?
) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!) {

    companion object {
        val LAYOUT_RESOURCE: Int = id.com.android.laundry.R.layout.holder_pesanan_accepted
    }

    private var itemContext = context
    var interfaceListPesananLaundry = interfaceListPesananLaundry

    fun bindingContent(
        content: ModelListPesananLaundry?,
        position: Int,
        role: String
    ) {
        content?.id.let { itemView?.view_holder_pesanan_accepted_id.text = "Nomor Transaksi : " +it }
        content?.nama_laundry.let { itemView?.view_holder_pesanan_accepted_nama_laundry.text = "Nama Laundry : "+it }
        content?.nama_user.let { itemView?.view_holder_pesanan_accepted_nama_user.text = "Nama User : "+it }
        content?.no_user.let { itemView?.view_holder_pesanan_accepted_hp_user.text = "No User : "+it }
        content?.alamat_user.let { itemView?.view_holder_pesanan_accepted_alamat_user.text = "Alamat User : "+it }
        content?.kurir.let { itemView?.view_holder_pesanan_accepted_kurir_user.text = "Kurir : "+it }
        content?.pesanan.let { parsingJSON(it,itemView) }
        content?.bank.let { itemView?.view_holder_pesanan_accepted_bank.text = it }
        content?.rekening.let { itemView?.view_holder_pesanan_accepted_rekening.text = it }

        itemView?.visibility = View.VISIBLE
        itemView?.holder_pesanan_accepted_status_layout.setBackgroundDrawable(itemContext?.resources?.getDrawable(
            R.drawable.background_rounded_green))

        if(content?.isAccept=="0"){
            itemView.holder_pesanan_accepted_status_layout.setBackgroundDrawable(itemContext?.resources?.getDrawable(
                R.drawable.background_rounded_gray))
            itemView?.holder_pesanan_accepted_status_text.text = "Pending"
        }else if(content?.isAccept=="1"){
            itemView.holder_pesanan_accepted_status_layout.setBackgroundDrawable(itemContext?.resources?.getDrawable(
                R.drawable.background_rounded_yellow))
            itemView?.holder_pesanan_accepted_status_text.text = "Diproses"
        }else if(content?.isAccept=="2"){
            itemView.holder_pesanan_accepted_status_layout.setBackgroundDrawable(itemContext?.resources?.getDrawable(
                R.drawable.background_rounded_green))
            itemView?.holder_pesanan_accepted_status_text.text = "Selesai"
        }

        itemView?.buttonLihatPembayaran.alpha = 1F
        if(content?.bukti!=""&&content?.bukti!=null){
            itemView?.buttonLihatPembayaran.alpha = 1F
            itemView?.buttonLihatPembayaran.setOnClickListener {
                interfaceListPesananLaundry?.onLihatbukti(position, content!!)
            }
        }else{
            itemView?.buttonLihatPembayaran.alpha = 0.5F
            itemView?.buttonLihatPembayaran.setOnClickListener {
null
            }
        }



        if(role==UtilConstant.LEVEL_LAUNDRY){


            itemView?.buttonUploadPembayaran.visibility = View.GONE
            itemView?.holder_pesanan_accepted_status_layout.visibility = View.GONE
            itemView?.holder_pesanan_accepted_change_status_layout.visibility = View.VISIBLE
            if(content?.isAccept=="0"){
                itemView.holder_pesanan_accepted_change_status_layout.setBackgroundDrawable(itemContext?.resources?.getDrawable(
                    R.drawable.background_rounded_gray))
                itemView?.holder_pesanan_accepted_change_status_text.text = "Tekan untuk proses"

                itemView?.holder_pesanan_accepted_change_status_layout.setOnClickListener {
                    interfaceListPesananLaundry?.onPesananAccept(position, content!!)
                }
                itemView?.view_holder_pesanan_accepted_delete.visibility = View.VISIBLE

                itemView?.view_holder_pesanan_accepted_delete.setOnClickListener {
                    interfaceListPesananLaundry?.onPesananRemove(position,content)
                }

            }else if(content?.isAccept=="1"){
                itemView.holder_pesanan_accepted_change_status_layout.setBackgroundDrawable(itemContext?.resources?.getDrawable(
                    R.drawable.background_rounded_yellow))
                itemView?.holder_pesanan_accepted_change_status_layout.setOnClickListener {
                    interfaceListPesananLaundry?.onPesananAccept(position, content!!)
                }
                itemView?.holder_pesanan_accepted_change_status_text.text = "Tekan untuk selesai"

            }else if(content?.isAccept=="2"){
                itemView.holder_pesanan_accepted_change_status_layout.setBackgroundDrawable(itemContext?.resources?.getDrawable(
                    R.drawable.background_rounded_green))
                itemView?.holder_pesanan_accepted_change_status_layout.setOnClickListener {
                    null
                }
                itemView?.holder_pesanan_accepted_change_status_text.text = "Pesanan sudah selesai"

            }

            itemView?.view_holder_pesanan_accepted_delete.setOnClickListener {
                interfaceListPesananLaundry?.onPesananRemove(position,content!!)
            }


        }else{
            itemView?.view_holder_pesanan_accepted_delete.visibility = View.GONE
            itemView?.buttonUploadPembayaran.visibility = View.VISIBLE
            itemView?.holder_pesanan_accepted_status_layout.visibility = View.VISIBLE
            itemView?.holder_pesanan_accepted_change_status_layout.visibility = View.GONE
            itemView?.buttonUploadPembayaran.setOnClickListener {
                interfaceListPesananLaundry?.onUploadBukti(position, content!!)
            }
        }

//        if(content?.isAccept==""||content?.isAccept==null){
//            itemView?.visibility = View.GONE
//        }else if(content.isAccept=="1"){
//
//
//            itemView?.holder_pesanan_accepted_status_layout.setBackgroundDrawable(itemContext?.resources?.getDrawable(
//                R.drawable.background_rounded_green))
//            itemView?.holder_pesanan_accepted_status_text.text = "Diterima"
//        }else if(content.isAccept=="0"){
//            itemView.holder_pesanan_accepted_status_layout.setBackgroundDrawable(itemContext?.resources?.getDrawable(
//                R.drawable.background_rounded_gray))
//            itemView?.holder_pesanan_accepted_status_text.text = "Pending"
//        }



    }

    fun parsingJSON(json: String?, itemView: View){

        try {
            val jsonObject = JSONObject(json)
            var name= jsonObject.get("name")
            var jenis= jsonObject.get("jenis")
            var jumlah= jsonObject.get("jumlah")
            var harga = jsonObject.get("harga")
            var harga_total = jsonObject.get("harga_total")
            itemView?.view_holder_pesanan_accepted_detail.text = "Paket = $name\n"+"Berat = "+ "$jumlah $jenis \n"+ "Harga = "+"Rp "+harga + " | Total Rp " + harga_total.toString()


        } catch (err: JSONException) {
            Log.d("Error", err.toString())
        }
    }
}