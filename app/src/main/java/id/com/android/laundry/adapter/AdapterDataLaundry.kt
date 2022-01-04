package id.com.android.laundry.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import id.com.android.laundry.feature.InterfaceEditHarga
import id.com.android.laundry.holder.HolderHargaEdit
import id.com.android.laundry.model.ModelHarga

class AdapterDataLaundry(context: Context?, interfaceEditHarga: InterfaceEditHarga) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM_TYPE_LAUNDRY: Int = 1
        const val CLASS_TAG: String = "Adapter Laundry"
    }

    var collectionHarga: ArrayList<ModelHarga>? = ArrayList()
    private var itemContext: Context? = context
    var context: Context? = context
    var interfaceEditHarga = interfaceEditHarga


    override fun getItemViewType(position: Int): Int {
        return ITEM_TYPE_LAUNDRY

    }

    override fun getItemCount(): Int {
        return collectionHarga!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(itemContext)
        val itemList = layoutInflater.inflate(HolderHargaEdit.LAYOUT_RESOURCE, parent, false)
        return HolderHargaEdit(itemList, itemContext,interfaceEditHarga)
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        when (holder?.itemViewType) {
            ITEM_TYPE_LAUNDRY -> {
                (holder as? HolderHargaEdit)?.bindingContent(collectionHarga?.get(position),position)
            }
        }
    }


}