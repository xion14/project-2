package id.com.android.laundry.feature.userlayer.fragment

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.com.android.laundry.R
import id.com.android.laundry.adapter.AdapterDetailLaundry
import id.com.android.laundry.feature.ActivityBase
import id.com.android.laundry.feature.presenterlayer.PresenterHome
import id.com.android.laundry.feature.userlayer.activity.EditHargaActivity
import id.com.android.laundry.feature.userlayer.activity.ui.EditUserActivity
import id.com.android.laundry.feature.userlayer.activity.ui.LoginActivity
import id.com.android.laundry.feature.viewlayer.ViewHome
import id.com.android.laundry.model.ModelHarga
import id.com.android.laundry.model.ModelLogin
import id.com.android.laundry.tools.UtilConstant
import id.com.android.laundry.tools.UtilImage
import kotlinx.android.synthetic.main.activity_laundry_detail.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.ivImage
import kotlinx.android.synthetic.main.fragment_profile.rvDetailLaundry
import java.lang.reflect.Type
import javax.inject.Inject

class FragmentProfile : FragmentBase(), ViewHome {

    @Inject
    lateinit var presenterHome: PresenterHome
    private lateinit var adapteDetailLaundry: AdapterDetailLaundry
    companion object {
        val LAYOUT_RESOURCE: Int = id.com.android.laundry.R.layout.fragment_profile
        val CLASS_TAG: String = "Fragment Profile"
    }
    var harga : String = ""
    private lateinit var progressDialog : ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializePresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(LAYOUT_RESOURCE, container, false)
        return inflater.inflate(LAYOUT_RESOURCE, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenterHome.attachView(this)
        initializeCollection()
        initializeProgressDialog()
        initClick()

    }
    private fun initializeProgressDialog() {
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Please Wait")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)
    }
    private fun initializeCollection() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        adapteDetailLaundry =  AdapterDetailLaundry(context)
        rvDetailLaundry?.layoutManager = layoutManager
        rvDetailLaundry?.adapter = adapteDetailLaundry
    }
    private fun initClick() {
        profileBtnEditHarga.setOnClickListener {
            val intent          = Intent(context, EditHargaActivity::class.java)
            intent.putExtra(UtilConstant.EDIT_HARGA,harga)
            startActivity(intent)
        }
        profileBtnEditUser.setOnClickListener {
            val intent          = Intent(context, EditUserActivity::class.java)
            startActivity(intent)
        }

//        presenterHome.updateUser()
    }


    private fun initializePresenter() {
        (activity as ActivityBase).componentActivity?.inject(this)
    }

    override fun showRole(data: ModelLogin?) {
        progressDialog.dismiss()
        etProfileUsername.setText(data?.username)
        etProfileName.setText(data?.fullname)
        etProfileAlamat.setText(data?.alamat)
        etProfileHandphone.setText(data?.phone)
        tvProfileDate.text = data?.birthday
        tvProfileGender.text = data?.gender
        tvProfileKurir.text = data?.kurir
        harga = data?.harga.toString()
        if(data?.level==UtilConstant.LEVEL_LAUNDRY){
            profileBtnEditHarga.visibility = View.VISIBLE
            layout_profile_paket.visibility = View.VISIBLE
            try {
                parsingJSON(harga)
            } catch (e: Exception) {
            }
        }else{
            layout_profile_paket.visibility = View.GONE
            profileBtnEditHarga.visibility = View.GONE
        }

        if(data?.foto==""||data?.foto==null){
            ivImage.setImageDrawable(context?.resources?.getDrawable(R.drawable.ic_profile_colour))
        }else{
            UtilImage.loadImageWithoutPlaceholder(ivImage,
                UtilConstant.HOST+data?.foto,context)
        }

    }

    private fun parsingJSON(json: String) {
        var arrayHarga : ArrayList<ModelHarga> = ArrayList()
        val gson = Gson()
        val jsonOutput = json
        val listType: Type = object : TypeToken<List<ModelHarga?>?>() {}.getType()
        val posts: List<ModelHarga> = gson.fromJson(jsonOutput, listType)

        arrayHarga.addAll(posts)
        adapteDetailLaundry.collectionHarga = arrayHarga
        adapteDetailLaundry.notifyDataSetChanged()
    }

    override fun notLogin() {

    }


    override fun onResume() {
        super.onResume()
        progressDialog.show()
        presenterHome.updateUser()
    }


}