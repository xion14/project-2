package id.com.android.laundry.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.com.android.laundry.feature.InterfaceListLaundry
import id.com.android.laundry.feature.InterfaceListUser
import id.com.android.laundry.holder.HolderListLaundry
import id.com.android.laundry.holder.HolderListUser
import id.com.android.laundry.model.ModelLogin


class AdapterListUser(context: Context?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM_TYPE_CONTENT: Int = 1
        const val CLASS_TAG: String = "Adapter Collection User"
    }

    var collectionData: ArrayList<ModelLogin>? = ArrayList()
    var interfaceListUser : InterfaceListUser? = null
    private var itemContext: Context? = context
    var context: Context? = context


    override fun getItemViewType(position: Int): Int {
        return ITEM_TYPE_CONTENT
    }

    override fun getItemCount(): Int {
        return collectionData?.size!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(itemContext)
        val itemViewThumbnail = layoutInflater.inflate(HolderListUser.LAYOUT_RESOURCE, parent, false)
        return HolderListUser(itemViewThumbnail, itemContext,interfaceListUser)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder?.itemViewType) {
            ITEM_TYPE_CONTENT -> {
                (holder as? HolderListUser)?.bindingContent(position,collectionData?.get(position)!!)
            }
        }
    }


}