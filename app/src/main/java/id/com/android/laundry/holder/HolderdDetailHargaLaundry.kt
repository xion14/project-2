package id.com.android.laundry.holder

import android.content.Context
import android.view.View
import id.com.android.laundry.R
import id.com.android.laundry.model.ModelHarga
import kotlinx.android.synthetic.main.holder_harga.view.*

class HolderdDetailHargaLaundry(itemView: View?, context: Context?) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!) {

    companion object {
        val LAYOUT_RESOURCE: Int = id.com.android.laundry.R.layout.holder_harga
    }

    private var itemContext = context

    fun bindingContent(content: ModelHarga?) {
        content?.name.let { itemView.view_holder_harga_name.text = it }
        content?.reguler.let {itemView.view_holder_harga_reguler.text =  "Regular : Rp "+it+" "+content?.type }
        content?.express.let { itemView.view_holder_harga_express.text = "Express : Rp "+it+" "+content?.type }
    }
}