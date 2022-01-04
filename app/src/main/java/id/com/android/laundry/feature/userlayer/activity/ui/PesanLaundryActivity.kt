package id.com.android.laundry.feature.userlayer.activity.ui

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.RadioButton
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import id.com.android.laundry.adapter.AdapterPesanLaundry
import id.com.android.laundry.feature.ActivityBase
import id.com.android.laundry.feature.InterfaceChoosePesanan
import id.com.android.laundry.feature.presenterlayer.PresenterPesanLaundry
import id.com.android.laundry.feature.viewlayer.ViewPesanLaundry
import id.com.android.laundry.model.ModelHarga
import id.com.android.laundry.model.ModelPesanan
import id.com.android.laundry.tools.UtilConstant
import id.com.android.laundry.tools.UtilImage
import kotlinx.android.synthetic.main.activity_pesan_laundry.*
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener
import javax.inject.Inject


class PesanLaundryActivity :  ActivityBase(), ViewPesanLaundry, InterfaceChoosePesanan {


    companion object {
        val LAYOUT_RESOURCE : Int   = id.com.android.laundry.R.layout.activity_pesan_laundry
        val CLASS_TAG : String      = "Activity Pesan Laundry"
    }

    @Inject
    lateinit var presenterPesanLaundry : PresenterPesanLaundry
    private lateinit var adapterPesanLaundry: AdapterPesanLaundry
    private lateinit var progressDialog : ProgressDialog
    private lateinit var content: Context
    var arrayListHarga : ArrayList<ModelPesanan> = ArrayList()
    var edit : Boolean = false
    var idLaundry : String = ""
    var namaLaundry : String = ""
    var fotoLaundry : String = ""
    var bankLaundry : String = ""
    var rekeningLaundry : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT_RESOURCE)
        this.content = this
        componentActivity?.inject(this)
        presenterPesanLaundry.attachView(this)
        initializeCollection()
        initProgressDialog()
        initData()
        initializeToolbar()
    }

    private fun initializeToolbar() {
        setSupportActionBar(view_content_detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun initializeCollection() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        adapterPesanLaundry =  AdapterPesanLaundry(this,this)
        rvPesanLaundry?.layoutManager = layoutManager
        rvPesanLaundry?.adapter = adapterPesanLaundry
    }

    private fun initData() {
        idLaundry = intent.getStringExtra(UtilConstant.LAUNDRY_ID)
        namaLaundry = intent.getStringExtra(UtilConstant.LAUNDRY_NAME)
        fotoLaundry = intent.getStringExtra(UtilConstant.LAUNDRY_FOTO)
        bankLaundry = intent.getStringExtra(UtilConstant.LAUNDRY_BANK)
        rekeningLaundry = intent.getStringExtra(UtilConstant.LAUNDRY_REKENING)
        view_content_detail_toolbar.title = namaLaundry
        tvPesanLaundryStatus.text = "Buka"
        if(intent.getStringExtra(UtilConstant.LAUNDRY_KURIR)=="Tidak"){
            tvPesanLaundryKurir.text = "Tidak Tersedia"
            layout_pesan_laundry_kurir.visibility = View.GONE
        }else{
            tvPesanLaundryKurir.text = "Tersedia"
            layout_pesan_laundry_kurir.visibility = View.VISIBLE
        }

        tvPesanLaundryPhone.text = intent.getStringExtra(UtilConstant.LAUNDRY_HP)
        try {
            presenterPesanLaundry.parsingJSON(intent.getStringExtra(UtilConstant.LAUNDRY_HARGA))
        } catch (e: Exception) {
        }

        btnPesanLaundry.setOnClickListener {
            if(edit){
                if(etPesanLaundryHarga.text.toString()==""){
                    Toast.makeText(this,"Masukkan jumlah kg / pc", Toast.LENGTH_SHORT).show()
                }else{
                   pesanLaundry()
                }

            }
        }

        UtilImage.loadImageWithoutPlaceholder(ivImage,
            UtilConstant.HOST+fotoLaundry,this)

    }

    private fun pesanLaundry() {
        var pesanan : String = ""
        var hargaTotal : String = ""
        var jumlahPesanan : String = ""
        for(i in 0 until adapterPesanLaundry.collectionHarga!!.size){
            if(adapterPesanLaundry.collectionHarga!![i].regulerClicked==true){
                val json = JSONObject()
                try {
                    json.put("name", adapterPesanLaundry.collectionHarga!![i].name)
                    json.put("jenis", UtilConstant.REGULER)
                    json.put("harga",  adapterPesanLaundry.collectionHarga!![i].reguler)
                    jumlahPesanan = etPesanLaundryHarga.text.toString()
                    json.put("jumlah",  jumlahPesanan)
                    hargaTotal = (adapterPesanLaundry.collectionHarga!![i].reguler?.toInt()!! * jumlahPesanan.toInt()).toString()
                    json.put("harga_total",  hargaTotal.toString())
                    pesanan = json.toString()
                } catch (e: JSONException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }
                break
            }
            if(adapterPesanLaundry.collectionHarga!![i].expressClicked){
                val json = JSONObject()
                try {
                    json.put("name", adapterPesanLaundry.collectionHarga!![i].name)
                    json.put("jenis", UtilConstant.EXPRESS)
                    json.put("harga",  adapterPesanLaundry.collectionHarga!![i].express +" / "+adapterPesanLaundry.collectionHarga!![i].type)
                    jumlahPesanan = etPesanLaundryHarga.text.toString()
                    json.put("jumlah",  jumlahPesanan)
                    hargaTotal = (adapterPesanLaundry.collectionHarga!![i].express?.toInt()!! * jumlahPesanan.toInt()).toString()
                    json.put("harga_total",  hargaTotal.toString())
                    pesanan = json.toString()
                } catch (e: JSONException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }
                break
            }
        }
        val selectedId: Int = radio.getCheckedRadioButtonId()
        var radioButton = findViewById<View>(selectedId) as RadioButton
        initDialog(idLaundry,namaLaundry,pesanan,radioButton.text.toString(),hargaTotal,jumlahPesanan,bankLaundry,rekeningLaundry)
//        progressDialog.show()
//
//        presenterPesanLaundry.requestLaundry(idLaundry,namaLaundry,pesanan,radioButton.text.toString(),hargaTotal,jumlahPesanan,bankLaundry,rekeningLaundry)
    }

    private fun initDialog(
        idLaundry: String,
        namaLaundry: String,
        pesanan: String,
        kurir: String,
        hargaTotal: String,
        jumlahPesanan: String,
        bankLaundry: String,
        rekeningLaundry: String
    ) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(id.com.android.laundry.R.layout.dialog_konfirmasi)

        val tvNama: TextView = dialog.findViewById(id.com.android.laundry.R.id.dialog_tv_nama_laundry) as TextView
        val tvKurir: TextView = dialog.findViewById(id.com.android.laundry.R.id.dialog_tv_kurir) as TextView
        val tvNamaPaket: TextView = dialog.findViewById(id.com.android.laundry.R.id.dialog_tv_jenis_paket) as TextView
        val tvHargaPaket: TextView = dialog.findViewById(id.com.android.laundry.R.id.dialog_tv_harga_paket) as TextView
        val tvJumlahPaket: TextView = dialog.findViewById(id.com.android.laundry.R.id.dialog_tv_jumlah_paket) as TextView
        val tvHargaTotal: TextView = dialog.findViewById(id.com.android.laundry.R.id.dialog_tv_harga_total) as TextView
        val buttonPesan: RelativeLayout = dialog.findViewById(id.com.android.laundry.R.id.dialog_button_pesan) as RelativeLayout
        tvNama.text = namaLaundry
        tvKurir.text = kurir
        val json = JSONTokener(pesanan).nextValue() as JSONObject
        var jenis = json?.get("jenis") as String
        tvNamaPaket.text = jenis
        var harga = json?.get("harga") as String
        tvHargaPaket.text = "Rp $harga,-"
        tvJumlahPaket.text = jumlahPesanan
        tvHargaTotal.text = "Rp $hargaTotal,-"
//        buttonPesan.setOnClickListener {
//            Toast.makeText(this,"tes",Toast.LENGTH_SHORT).show()
//        }
        buttonPesan.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
            progressDialog.show()

            presenterPesanLaundry.requestLaundry(idLaundry,namaLaundry,pesanan,kurir,hargaTotal,jumlahPesanan,bankLaundry,rekeningLaundry)
        })

        dialog.show()
    }


    private fun initProgressDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)
    }





    override fun showFailedToast(message: String) {
        progressDialog.dismiss()
        Toast.makeText(content, message, Toast.LENGTH_LONG).show()
    }

    override fun showSuccess(message: String) {
        Toast.makeText(content, message, Toast.LENGTH_LONG).show()
        progressDialog.dismiss()
        finish()
    }

    override fun showData(arrayHarga: ArrayList<ModelHarga>) {
        for(i in 0 until arrayHarga.size){
            arrayListHarga.add(ModelPesanan(arrayHarga[i].name,arrayHarga[i].reguler,arrayHarga[i].express,arrayHarga[i].type,false,false))
        }
        adapterPesanLaundry.collectionHarga?.addAll(arrayListHarga)
        adapterPesanLaundry.notifyDataSetChanged()
    }

    override fun showError(error: String) {
        progressDialog.dismiss()
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
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

    override fun onPesanClicked(position: Int, content: ModelPesanan, clicked: String) {
        edit = true
        adapterPesanLaundry.collectionHarga?.clear()
        for(i in 0 until arrayListHarga.size){
            if(i==position){
                if(clicked==UtilConstant.REGULER){
                    adapterPesanLaundry.collectionHarga?.add(ModelPesanan(content.name,content.reguler,content.express,content.type,true,false))
                }else{
                    adapterPesanLaundry.collectionHarga?.add(ModelPesanan(content.name,content.reguler,content.express,content.type,false,true))

                }
            }else{
                adapterPesanLaundry.collectionHarga?.add(arrayListHarga[i])
            }
        }
        adapterPesanLaundry.notifyDataSetChanged()

        btnPesanLaundry.alpha = 1F
    }

    override fun onDetailClilcked() {

    }



}