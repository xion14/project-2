package id.com.android.laundry.holder

import android.content.Context
import android.view.View
import id.com.android.laundry.feature.InterfaceEditHarga
import id.com.android.laundry.model.ModelHarga
import kotlinx.android.synthetic.main.holder_edit_harga.view.*

class HolderHargaEdit(itemView: View?, context: Context?, interfaceEditHarga: InterfaceEditHarga) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!) {

    companion object {
        val LAYOUT_RESOURCE: Int = id.com.android.laundry.R.layout.holder_edit_harga
    }

    private var itemContext = context
    private var interfaceEditHarga = interfaceEditHarga

    fun bindingContent(content: ModelHarga?, position: Int) {
        content?.name.let { itemView.view_holder_edit_harga_name.text = it }
        content?.reguler.let {itemView.view_holder_edit_harga_reguler.text =  "Rp "+it+" "+content?.type }
        content?.express.let { itemView.view_holder_edit_harga_express.text = "Rp "+it+" "+content?.type }
        itemView?.view_holder_edit_harga_delete.setOnClickListener {
            interfaceEditHarga.onHargaDelete(position)
        }
    }
}