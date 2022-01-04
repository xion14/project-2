package id.com.android.laundry.feature.userlayer.activity.ui

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.com.android.laundry.R
import id.com.android.laundry.adapter.AdapterDetailLaundry
import id.com.android.laundry.adapter.AdapterPesanLaundry
import id.com.android.laundry.feature.ActivityBase
import id.com.android.laundry.feature.InterfaceChoosePesanan
import id.com.android.laundry.feature.presenterlayer.PresenterPesanLaundry
import id.com.android.laundry.feature.viewlayer.ViewPesanLaundry
import id.com.android.laundry.model.ModelHarga
import id.com.android.laundry.model.ModelPesanan
import id.com.android.laundry.tools.UtilConstant
import id.com.android.laundry.tools.UtilImage
import kotlinx.android.synthetic.main.activity_laundry_detail.*
import kotlinx.android.synthetic.main.activity_pesan_laundry.view_content_detail_toolbar
import kotlinx.android.synthetic.main.holder_list_laundry.view.*
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Type
import javax.inject.Inject

class LaundryDetailActivity :  ActivityBase() {


    companion object {
        val LAYOUT_RESOURCE : Int   = id.com.android.laundry.R.layout.activity_laundry_detail
        val CLASS_TAG : String      = "Activity Pesan Laundry"
    }

    private lateinit var adapteDetailLaundry: AdapterDetailLaundry
    private lateinit var progressDialog : ProgressDialog
    private lateinit var content: Context
    var arrayListHarga : ArrayList<ModelPesanan> = ArrayList()
    var idLaundry : String = ""
    var namaLaundry : String = ""
    var alamatLaundry : String = ""
    var kurirLaundry : String = ""
    var hpLaundry : String = ""
    var hargaLaundry : String = ""
    var fotoLaundry : String = ""
    var bankLaundry : String = ""
    var rekeningLaundry : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT_RESOURCE)
        this.content = this
        componentActivity?.inject(this)
        initializeCollection()
        initializeToolbar()
        initProgressDialog()
        initData()

    }

    private fun initializeToolbar() {
        setSupportActionBar(view_content_detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Laundry Detail"
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun initializeCollection() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        adapteDetailLaundry =  AdapterDetailLaundry(this)
        rvDetailLaundry?.layoutManager = layoutManager
        rvDetailLaundry?.adapter = adapteDetailLaundry
    }

    private fun initData() {
        idLaundry = intent.getStringExtra(UtilConstant.LAUNDRY_ID)
        namaLaundry = intent.getStringExtra(UtilConstant.LAUNDRY_NAME)
        kurirLaundry = intent.getStringExtra(UtilConstant.LAUNDRY_KURIR)
        hpLaundry = intent.getStringExtra(UtilConstant.LAUNDRY_HP)
        alamatLaundry = intent.getStringExtra(UtilConstant.LAUNDRY_ALAMAT)
        hargaLaundry = intent.getStringExtra(UtilConstant.LAUNDRY_HARGA)
        fotoLaundry = intent.getStringExtra(UtilConstant.LAUNDRY_FOTO)
        bankLaundry = intent.getStringExtra(UtilConstant.LAUNDRY_BANK)
        rekeningLaundry = intent.getStringExtra(UtilConstant.LAUNDRY_REKENING)

        UtilImage.loadImageWithoutPlaceholder(ivImage,
            UtilConstant.HOST+fotoLaundry,this)
        tvLaundryDetailName.text = namaLaundry
        if(kurirLaundry=="Tidak"){
            tvLaundryDetailKurir.text = "Tidak Tersedia"

        }else{
            tvLaundryDetailKurir.text = "Tersedia"
        }
        tvLaundryDetailPhone.text = hpLaundry
        tvLaundryDetailAlamat.text = alamatLaundry
        try {
            parsingJSON(hargaLaundry)
        } catch (e: Exception) {
        }

        btnPesanLaundry.setOnClickListener {
            val intent = Intent(this.content, PesanLaundryActivity::class.java)
            intent.putExtra(UtilConstant.LAUNDRY_ID,idLaundry)
            intent.putExtra(UtilConstant.LAUNDRY_NAME,namaLaundry)
            intent.putExtra(UtilConstant.LAUNDRY_KURIR, kurirLaundry)
            intent.putExtra(UtilConstant.LAUNDRY_HP, hpLaundry)
            intent.putExtra(UtilConstant.LAUNDRY_HARGA,hargaLaundry)
            intent.putExtra(UtilConstant.LAUNDRY_FOTO,fotoLaundry)
            intent.putExtra(UtilConstant.LAUNDRY_BANK,bankLaundry)
            intent.putExtra(UtilConstant.LAUNDRY_REKENING,rekeningLaundry)
            startActivity(intent)
        }
    }

    private fun initProgressDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return false
    }

    fun parsingJSON(json: String){
        var arrayHarga : ArrayList<ModelHarga> = ArrayList()
        val gson = Gson()
        val jsonOutput = json
        val listType: Type = object : TypeToken<List<ModelHarga?>?>() {}.getType()
        val posts: List<ModelHarga> = gson.fromJson(jsonOutput, listType)

        arrayHarga.addAll(posts)
        adapteDetailLaundry.collectionHarga = arrayHarga
        adapteDetailLaundry.notifyDataSetChanged()
    }


}