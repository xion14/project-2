package id.com.android.laundry.holder

import android.content.Context
import android.view.View
import id.com.android.laundry.feature.InterfaceEditHarga
import id.com.android.laundry.feature.InterfaceListPesananLaundry
import id.com.android.laundry.model.ModelHarga
import id.com.android.laundry.model.ModelListPesananLaundry
import kotlinx.android.synthetic.main.holder_edit_harga.view.*
import kotlinx.android.synthetic.main.holder_list_laundry.view.*
import kotlinx.android.synthetic.main.holder_list_pesanan_laundry.view.*


class HolderListPesananLaundry(itemView: View?, context: Context?, interfaceListPesananLaundry: InterfaceListPesananLaundry) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!) {

    companion object {
        val LAYOUT_RESOURCE: Int = id.com.android.laundry.R.layout.holder_list_pesanan_laundry
    }

    private var itemContext = context
    private var interfaceEditHarga = interfaceListPesananLaundry

    fun bindingContent(content: ModelListPesananLaundry?, position: Int) {
        content?.nama_user.let {itemView.view_holder_pesanan_laundry_name.text =  it }
        itemView?.view_holder_pesanan_laundry_remove.setOnClickListener {
            interfaceEditHarga.onPesananRemove(position, content!!)
        }
        itemView?.view_holder_pesanan_laundry_accept.setOnClickListener {
            interfaceEditHarga.onPesananAccept(position, content!!)
        }
        itemView?.view_holder_list_pesanan_detail.setOnClickListener {
            interfaceEditHarga.onPesananDetail(position, content!!)
        }
        if(content?.isAccept=="1"){
            itemView?.visibility = View.GONE
        }
    }
}