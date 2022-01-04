package id.com.android.laundry.holder

import android.content.Context
import android.view.View
import id.com.android.laundry.R
import id.com.android.laundry.feature.InterfaceListLaundry
import id.com.android.laundry.model.ModelLogin
import id.com.android.laundry.tools.UtilConstant
import id.com.android.laundry.tools.UtilImage
import kotlinx.android.synthetic.main.holder_list_laundry.view.*

class HolderListLaundry(itemView: View?, context: Context?, interfaceListLaundry: InterfaceListLaundry?) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!) {

    companion object {
        val LAYOUT_RESOURCE: Int = id.com.android.laundry.R.layout.holder_list_laundry
    }

    private var itemContext = context
    private var interfaceListLaundry = interfaceListLaundry

    fun bindingContent(content: ModelLogin?) {
        content?.fullname.let { itemView.view_holder_list_laundry_name.text = "Nama Laundry :$it" }
        content?.alamat.let {itemView.view_holder_list_laundry_alamat.text = "Alamat Laundry :$it" }
        itemView.view_holder_list_laundry_status.text = "Status Laundry : Buka"
        itemView?.view_holder_list_laundry_kurir_status.setImageResource(R.color.colorAccent)
        if(content?.kurir=="Tidak" || content?.kurir=="Tidak tersedia"){
            itemView?.view_holder_list_laundry_kurir_status.setImageResource(R.color.colorRed1)
        }
        content?.kurir.let { itemView.view_holder_list_laundry_kurir.text = "Kurir $it" }
        UtilImage.loadImageWithoutPlaceholder(itemView?.view_holder_list_laundry_photo,
            UtilConstant.HOST+content?.foto,itemContext)
        itemView?.view_holder_list_laundry_pesan.setOnClickListener {
            interfaceListLaundry?.onPesanClicked(content)
        }
        itemView?.view_holder_list_laundry_detail.setOnClickListener {
            interfaceListLaundry?.onDetailClilcked(content)
        }
    }
}