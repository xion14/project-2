package id.com.android.laundry.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import id.com.android.laundry.feature.InterfaceListPesananLaundry
import id.com.android.laundry.holder.HolderPesananAccepted
import id.com.android.laundry.model.ModelListPesananLaundry


class AdapterPesananUser(context: Context?) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM_TYPE_LAUNDRY: Int = 1
        const val CLASS_TAG: String = "Adapter Laundry"
    }

    var collectionData: ArrayList<ModelListPesananLaundry>? = ArrayList()
    private var itemContext: Context? = context
    var context: Context? = context
    var interfaceListPesananLaundry : InterfaceListPesananLaundry? = null
    var role : String = ""


    override fun getItemViewType(position: Int): Int {
        return ITEM_TYPE_LAUNDRY

    }

    override fun getItemCount(): Int {
        return collectionData!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val itemList = layoutInflater.inflate(HolderPesananAccepted.LAYOUT_RESOURCE, parent, false)
        return HolderPesananAccepted(itemList, context,interfaceListPesananLaundry)
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        when (holder?.itemViewType) {
            ITEM_TYPE_LAUNDRY -> {
                (holder as? HolderPesananAccepted)?.bindingContent(collectionData?.get(position),position,role)
            }
        }
    }


}