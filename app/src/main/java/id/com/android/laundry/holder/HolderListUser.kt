package id.com.android.laundry.holder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.com.android.laundry.R
import id.com.android.laundry.adapter.AdapterDetailLaundry
import id.com.android.laundry.feature.InterfaceListUser
import id.com.android.laundry.model.ModelHarga
import id.com.android.laundry.model.ModelLogin
import id.com.android.laundry.tools.UtilConstant
import id.com.android.laundry.tools.UtilImage
import kotlinx.android.synthetic.main.holder_list_laundry.view.view_holder_list_laundry_kurir_status
import kotlinx.android.synthetic.main.holder_list_laundry.view.view_holder_list_laundry_photo
import kotlinx.android.synthetic.main.holder_list_user.view.*
import java.lang.reflect.Type


class HolderListUser(itemView: View?, context: Context?, interfaceListUser: InterfaceListUser?) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!) {

    companion object {
        val LAYOUT_RESOURCE: Int = R.layout.holder_list_user
    }

    private var itemContext = context
    private var interfaceListUser = interfaceListUser
    private lateinit var adapteDetailLaundry: AdapterDetailLaundry

    fun bindingContent(position : Int,content: ModelLogin?) {
        content?.username.let { itemView?.view_holder_list_user_username.text = it }
        content?.fullname.let { itemView.view_holder_list_user_name.text = "Nama :$it" }
        content?.phone.let {itemView.view_holder_list_user_phone.text = "No Handphone :$it" }
        content?.alamat.let {itemView.view_holder_list_user_alamat.text = "Alamat :$it" }

        itemView?.view_holder_list_user_layout_laundry.visibility = View.GONE
        if(content?.level==UtilConstant.LEVEL_USER){
            itemView?.view_holder_list_user_layout_laundry.visibility = View.GONE
        }else{
            val layoutManager = LinearLayoutManager(itemContext)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            adapteDetailLaundry =  AdapterDetailLaundry(itemContext)
            itemView?.view_holder_list_user_paket_rv?.layoutManager = layoutManager
            itemView?.view_holder_list_user_paket_rv?.adapter = adapteDetailLaundry

            itemView?.view_holder_list_user_layout_laundry.visibility = View.VISIBLE
            content?.bank.let { itemView?.view_holder_list_user_bank.text = it }
            content?.rekening.let { itemView?.view_holder_list_user_rekening.text = it }
            try {
                parsingJSON(content?.harga)
            } catch (e: Exception) {
            }

            itemView?.view_holder_list_laundry_kurir_status.setImageResource(R.color.colorAccent)
            itemView?.view_holder_list_laundry_kurir_status_text.text = "Tersedia"
            if(content?.kurir=="Tidak tersedia" || content?.kurir=="Tidak"){
                itemView?.view_holder_list_laundry_kurir_status.setImageResource(R.color.colorRed1)
                itemView?.view_holder_list_laundry_kurir_status_text.text = "Tidak tersedia"
            }else{
                itemView?.view_holder_list_laundry_kurir_status_text.text = "Tersedia"

            }
        }

        UtilImage.loadImageWithoutPlaceholder(itemView?.view_holder_list_laundry_photo,
            UtilConstant.HOST+content?.foto,itemContext)

        itemView?.view_holder_list_user_update.setOnClickListener {
            interfaceListUser?.updateUser(position,content)
        }
        itemView?.view_holder_list_user_delete.setOnClickListener {
            interfaceListUser?.deleteUser(position,content)
        }
    }

    private fun parsingJSON(harga: String?) {
        var arrayHarga : ArrayList<ModelHarga> = ArrayList()
        val gson = Gson()
        val jsonOutput = harga
        val listType: Type = object : TypeToken<List<ModelHarga?>?>() {}.getType()
        val posts: List<ModelHarga> = gson.fromJson(jsonOutput, listType)

        arrayHarga.addAll(posts)
        adapteDetailLaundry.collectionHarga = arrayHarga
        adapteDetailLaundry.notifyDataSetChanged()
    }


}