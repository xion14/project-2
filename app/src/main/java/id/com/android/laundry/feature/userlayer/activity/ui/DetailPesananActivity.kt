package id.com.android.laundry.feature.userlayer.activity.ui

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import id.com.android.laundry.adapter.AdapterDetailLaundry
import id.com.android.laundry.feature.ActivityBase
import id.com.android.laundry.feature.presenterlayer.PresenterListPesananLaundry
import id.com.android.laundry.feature.viewlayer.ViewListPesananLaundry
import id.com.android.laundry.model.ModelListPesananLaundry
import id.com.android.laundry.model.ModelPesanan
import id.com.android.laundry.tools.UtilConstant
import id.com.android.laundry.tools.UtilImage
import kotlinx.android.synthetic.main.activity_detail_pesanan.*
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject


class DetailPesananActivity :  ActivityBase(), ViewListPesananLaundry {


    companion object {
        val LAYOUT_RESOURCE : Int   = id.com.android.laundry.R.layout.activity_detail_pesanan
        val CLASS_TAG : String      = "Activity Pesan Laundry"
    }

    private lateinit var adapteDetailLaundry: AdapterDetailLaundry
    private lateinit var progressDialog : ProgressDialog
    private lateinit var content: Context
    @Inject
    lateinit var presenterListLaundry: PresenterListPesananLaundry
    var arrayListHarga : ArrayList<ModelPesanan> = ArrayList()
    var idUser : String = ""
    var namaUser : String = ""
    var alamatUser: String = ""
    var hpUser : String = ""
    var paketUser : String = ""
    var kurirUser : String = ""
    var hargaUser : String = ""
    var fotoUser : String = ""
    var id : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT_RESOURCE)
        this.content = this
        initializePresenter()
        initializeToolbar()
        initProgressDialog()
        initData()

    }

    private fun initializeToolbar() {
        setSupportActionBar(view_content_detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun initializePresenter() {
        componentActivity?.inject(this)
        presenterListLaundry.attachView(this)
    }


    private fun initData() {
        id = intent.getStringExtra(UtilConstant.PESANAN_ID)
        idUser = intent.getStringExtra(UtilConstant.USER_ID)
        namaUser = intent.getStringExtra(UtilConstant.USER_NAME)
        hpUser = intent.getStringExtra(UtilConstant.USER_HP)
        paketUser = intent.getStringExtra(UtilConstant.USER_PAKET)
        kurirUser = intent.getStringExtra(UtilConstant.USER_KURIR)
        alamatUser = intent.getStringExtra(UtilConstant.USER_ALAMAT)
//        hargaUser = intent.getStringExtra(UtilConstant.USER_HARGA)
        fotoUser = intent.getStringExtra(UtilConstant.USER_FOTO)
        UtilImage.loadImageWithoutPlaceholder(ivImage,
            UtilConstant.HOST+fotoUser,this)
        tvDetailPesananName.text = namaUser
        tvDetailPesananNoHP.text = hpUser
        tvDetailPesananAlamat.text = alamatUser
        tvDetailPesananHarga.text = "Rp "+hargaUser
        tvDetailPesananKurir.text = kurirUser
        parsingJSON(paketUser)
        btnDetailPesananTerima.setOnClickListener {
            progressDialog.show()
            presenterListLaundry.acceptPesananDetaill(id)
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

        try {
            val jsonObject = JSONObject(json)
            var name= jsonObject.get("name")
            var jenis= jsonObject.get("jenis")
            var jumlah= jsonObject.get("jumlah")
            var harga = jsonObject.get("harga")
            var harga_total = jsonObject.get("harga_total")
            tvDetailPesananPaket.text = name.toString()
            tvDetailPesananBerat.text = "$jumlah $jenis"
//            tvDetailPesananHarga.text = harga_total.toString()
            tvDetailPesananHarga.text = "Rp "+harga + " | Total Rp " + harga_total.toString()

        } catch (err: JSONException) {
            Log.d("Error", err.toString())
        }
    }

    override fun showLoadingProcess() {
        progressDialog.show()
    }

    override fun showError() {
        progressDialog.dismiss()
        Toast.makeText(this,"Gagal",Toast.LENGTH_SHORT).show()
    }

    override fun showListPesanan(body: ArrayList<ModelListPesananLaundry>?) {

    }

    override fun showListPesananUser(body: ArrayList<ModelListPesananLaundry>?) {

    }

    override fun showToast(toast: String) {
        progressDialog.dismiss()
        Toast.makeText(this,toast,Toast.LENGTH_SHORT).show()
    }

    override fun showSuccessAccept(
        toast: String,
        position: Int,
        accept: String
    ) {
        progressDialog.dismiss()
        Toast.makeText(this, toast,Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun showSuccessUpload(toast: String) {

    }

    override fun showRole(role: String?) {

    }

    override fun successDelete(position: Int) {

    }



}