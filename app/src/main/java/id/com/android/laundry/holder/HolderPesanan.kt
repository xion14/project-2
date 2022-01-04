package id.com.android.laundry.holder

import android.content.Context
import android.view.View
import id.com.android.laundry.R
import id.com.android.laundry.feature.InterfaceChoosePesanan
import id.com.android.laundry.model.ModelPesanan
import id.com.android.laundry.tools.UtilConstant
import kotlinx.android.synthetic.main.holder_pesanan.view.*


class HolderPesanan(itemView: View?, context: Context?, interfaceChoosePesanan: InterfaceChoosePesanan) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!) {

    companion object {
        val LAYOUT_RESOURCE: Int = id.com.android.laundry.R.layout.holder_pesanan
    }

    private var itemContext = context
    private var interfaceChoosePesanan = interfaceChoosePesanan

    fun bindingContent(content: ModelPesanan?, position: Int) {
        content?.name.let { itemView.view_holder_edit_harga_name.text = it }
        content?.reguler.let {itemView.view_holder_edit_harga_reguler.text =  "Regular : Rp "+it+" "+content?.type }
        content?.express.let { itemView.view_holder_edit_harga_express.text = "Express : Rp "+it+" "+content?.type }
        itemView.view_holder_pesanan_check.setImageDrawable(itemContext?.resources?.getDrawable(R.drawable.ic_uncheck))

        itemView.view_holder_pesanan_reguler.setBackgroundDrawable(itemContext?.resources?.getDrawable(R.drawable.background_rounded_gray))
        itemView.view_holder_pesanan_express.setBackgroundDrawable(itemContext?.resources?.getDrawable(R.drawable.background_rounded_gray))

//        if(content?.clicked!!){
//            itemView.view_holder_pesanan_check.setImageDrawable(itemContext?.resources?.getDrawable(R.drawable.ic_checked))
//        }
//        itemView?.view_holder_pesanan_check.setOnClickListener {
//           if(!content?.clicked!!){
//               itemView.view_holder_pesanan_check.setImageDrawable(itemContext?.resources?.getDrawable(R.drawable.ic_checked))
//               interfaceChoosePesanan.onPesanClicked(position, content, UtilConstant.REGULER)
//           }
//        }

        if(content?.regulerClicked!!){
            itemView.view_holder_pesanan_reguler.setBackgroundDrawable(itemContext?.resources?.getDrawable(R.drawable.background_rounded_green))
        }else{
            itemView.view_holder_pesanan_reguler.setBackgroundDrawable(itemContext?.resources?.getDrawable(R.drawable.background_rounded_gray))
        }

        if(content?.expressClicked!!){
            itemView.view_holder_pesanan_express.setBackgroundDrawable(itemContext?.resources?.getDrawable(R.drawable.background_rounded_green))
        }else{
            itemView.view_holder_pesanan_express.setBackgroundDrawable(itemContext?.resources?.getDrawable(R.drawable.background_rounded_gray))
        }

        itemView?.view_holder_pesanan_reguler.setOnClickListener {
            if(!content?.regulerClicked!!){
                itemView.view_holder_pesanan_reguler.setBackgroundDrawable(itemContext?.resources?.getDrawable(R.drawable.background_rounded_green))
                itemView.view_holder_pesanan_express.setBackgroundDrawable(itemContext?.resources?.getDrawable(R.drawable.background_rounded_gray))
                interfaceChoosePesanan.onPesanClicked(position,content,UtilConstant.REGULER)
            }
        }

        itemView?.view_holder_pesanan_express.setOnClickListener {
            if(!content?.expressClicked!!){
                itemView.view_holder_pesanan_express.setBackgroundDrawable(itemContext?.resources?.getDrawable(R.drawable.background_rounded_green))
                itemView.view_holder_pesanan_reguler.setBackgroundDrawable(itemContext?.resources?.getDrawable(R.drawable.background_rounded_gray))
                interfaceChoosePesanan.onPesanClicked(position,content,UtilConstant.EXPRESS)
            }
        }
    }
}