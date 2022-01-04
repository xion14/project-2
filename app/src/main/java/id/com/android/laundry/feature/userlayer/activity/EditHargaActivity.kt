package id.com.android.laundry.feature.userlayer.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import id.com.android.laundry.R
import id.com.android.laundry.adapter.AdapterDataLaundry
import id.com.android.laundry.feature.ActivityBase
import id.com.android.laundry.feature.InterfaceEditHarga
import id.com.android.laundry.feature.presenterlayer.PresenterEditHarga
import id.com.android.laundry.feature.viewlayer.ViewEditHarga
import id.com.android.laundry.model.ModelHarga
import id.com.android.laundry.tools.UtilConstant
import kotlinx.android.synthetic.main.activity_edit_harga.*
import javax.inject.Inject

class EditHargaActivity : ActivityBase(),ViewEditHarga,InterfaceEditHarga {

    @Inject
    lateinit var presenterEditHarga : PresenterEditHarga
    private lateinit var progressDialog : ProgressDialog
    private lateinit var adapterEditHarga: AdapterDataLaundry
    var harga : String = ""
    private var edit : Boolean = false
    var idLaundry : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_harga)
        componentActivity?.inject(this)
        presenterEditHarga.attachView(this)
        initializeCollection()
        initializeProgressDialog()
        initData()
        initClick()
        initializeToolbar()
    }

    private fun initializeToolbar() {
        setSupportActionBar(view_content_detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun initClick() {
        btnTambahHarga.setOnClickListener {
            if(etEditHargaName.text.toString()==""){
                Toast.makeText(this,"Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if(etEditHargaReg.text.toString()==""){
                Toast.makeText(this,"Harga reguler tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if(etEditHargaExpres.text.toString()==""){
                Toast.makeText(this,"Harga express tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else{
                val selectedId: Int = radioHarga.getCheckedRadioButtonId()
                var radioButton = findViewById<View>(selectedId) as RadioButton

                var modelHarga = ModelHarga(etEditHargaName.text.toString(),etEditHargaReg.text.toString(),etEditHargaExpres.text.toString(),radioButton.text.toString())
                adapterEditHarga.collectionHarga?.add(modelHarga)
                adapterEditHarga.notifyDataSetChanged()

                etEditHargaName.setText("")
                etEditHargaReg.setText("")
                etEditHargaExpres.setText("")
                edit = true
                btnSaveHarga.alpha = 1F
            }
        }

        btnSaveHarga.setOnClickListener {
            if(edit){
                progressDialog.show()
                presenterEditHarga.updateHarga(adapterEditHarga.collectionHarga)
            }
        }
    }

    private fun initData() {

        harga = intent.getStringExtra(UtilConstant.EDIT_HARGA)
        if(harga==""||harga=="null"||harga==null){

        }else{
            progressDialog.show()
            presenterEditHarga.parsingJSON(harga)
        }

    }


    override fun showData(arrayHarga: ArrayList<ModelHarga>) {
        progressDialog.dismiss()
        adapterEditHarga.collectionHarga = arrayHarga
        adapterEditHarga.notifyDataSetChanged()
    }

    override fun showToast(message: String) {
        progressDialog.dismiss()
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show()
    }

    private fun initializeCollection() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        adapterEditHarga =  AdapterDataLaundry(this,this)
        rvEditHarga?.layoutManager = layoutManager
        rvEditHarga?.adapter = adapterEditHarga

    }

    private fun initializeProgressDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)
    }

    override fun onHargaDelete(position: Int) {
        adapterEditHarga.collectionHarga?.removeAt(position)
        adapterEditHarga.notifyItemRemoved(position) //notifies changes in adapter, in this case use the notifyItemMoved
        adapterEditHarga.notifyDataSetChanged()
        edit = true
        btnSaveHarga.alpha = 1F
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return false
    }


}