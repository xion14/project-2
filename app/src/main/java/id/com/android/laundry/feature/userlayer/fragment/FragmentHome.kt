package id.com.android.laundry.feature.userlayer.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.com.android.laundry.controller.OnSwipeTouchListener
import id.com.android.laundry.feature.ActivityBase
import id.com.android.laundry.feature.presenterlayer.PresenterHome
import id.com.android.laundry.feature.userlayer.activity.ui.*
import id.com.android.laundry.feature.viewlayer.ViewHome
import id.com.android.laundry.model.ModelLogin
import id.com.android.laundry.tools.UtilConstant
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class FragmentHome : FragmentBase(), ViewHome {

    @Inject
    lateinit var presenterHom: PresenterHome

    companion object {
        val LAYOUT_RESOURCE: Int = id.com.android.laundry.R.layout.fragment_home
        val CLASS_TAG: String = "Fragment Home"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializePresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(LAYOUT_RESOURCE, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenterHom.attachView(this)
        presenterHom.getRole()
    }




    private fun initializePresenter() {
        (activity as ActivityBase).componentActivity?.inject(this)
    }

    override fun showRole(content: ModelLogin?) {
        if(content?.level==UtilConstant.LEVEL_LAUNDRY){
            layout_menu_laundry.visibility = View.VISIBLE
            layout_menu_user.visibility = View.GONE
            fragment_home_button_lihat_pesanan_laundry.setOnClickListener {
                val intent = Intent(context, PesananUserActivity::class.java)
                intent.putExtra(UtilConstant.IS_ACCEPT,"0")
                startActivity(intent)
            }
            fragment_home_button_lihat_pesanan_laundry.setOnClickListener {
                val intent = Intent(context, PesananUserActivity::class.java)
                intent.putExtra(UtilConstant.IS_ACCEPT,"0")
                startActivity(intent)
            }


        }else if(content?.level==UtilConstant.LEVEL_USER){
            layout_menu_laundry.visibility = View.GONE
            layout_menu_user.visibility = View.VISIBLE
            fragment_home_button_pesan_user.setOnClickListener {
                val intent = Intent(context, ListLaundryActivity::class.java)
                startActivity(intent)
            }
        }else{
            layout_menu_admin.visibility = View.VISIBLE
            fragment_home_button_transaksi.visibility = View.GONE
            layout_menu_laundry.visibility = View.GONE
            layout_menu_user.visibility = View.GONE
            fragment_home_button_list_user.setOnClickListener {
                val intent = Intent(context, ListUserActivity::class.java)
                intent.putExtra(UtilConstant.ROLE, UtilConstant.LEVEL_USER)
                startActivity(intent)
            }

            fragment_home_button_list_laundry.setOnClickListener {
                val intent = Intent(context, ListUserActivity::class.java)
                intent.putExtra(UtilConstant.ROLE, UtilConstant.LEVEL_LAUNDRY)
                startActivity(intent)
            }

        }
        fragment_home_button_transaksi.setOnClickListener {
            val intent = Intent(context, TransactionChooseActivity::class.java)
            startActivity(intent)
        }
        fragment_home_button_panduan_user.setOnClickListener {
            val intent = Intent(context, BantuanActivity::class.java)
            startActivity(intent)
        }
        fragment_home_button_panduan_laundry.setOnClickListener {
            val intent = Intent(context, BantuanActivity::class.java)
            startActivity(intent)
        }

    }

    override fun notLogin() {

    }


}