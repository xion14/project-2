package id.com.android.laundry.feature.userlayer.activity.ui

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fxn.pix.Pix
import id.com.android.laundry.R
import id.com.android.laundry.adapter.AdapterPesananUser
import id.com.android.laundry.feature.ActivityBase
import id.com.android.laundry.feature.InterfaceListPesananLaundry
import id.com.android.laundry.feature.presenterlayer.PresenterListPesananLaundry
import id.com.android.laundry.feature.viewlayer.ViewListPesananLaundry
import id.com.android.laundry.model.ModelListPesananLaundry
import id.com.android.laundry.model.ModelLogin
import id.com.android.laundry.tools.UtilConstant
import kotlinx.android.synthetic.main.activity_list_laundry.*
import kotlinx.android.synthetic.main.view_no_data.*
import java.io.File
import javax.inject.Inject


class PesananUserActivity : ActivityBase(), ViewListPesananLaundry,
    SwipeRefreshLayout.OnRefreshListener, InterfaceListPesananLaundry {


    private lateinit var content: Context

    @Inject
    lateinit var presenterListLaundry: PresenterListPesananLaundry
    private lateinit var progressDialog: ProgressDialog
    private lateinit var adapterListPesananLaundry: AdapterPesananUser
    var arrayListData: ArrayList<ModelLogin> = ArrayList()
    var isAccept: String = ""
    private var bitmap: Bitmap? = null
    var idUpload: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_pesanan_laundry)
        this.content = this
        supportActionBar?.hide()
        isAccept = intent.getStringExtra(UtilConstant.IS_ACCEPT)

        initializePresenter()
        initializeProgressDialog()
        initializeData()
        initializeToolbar()
    }

    private fun initializeToolbar() {
        setSupportActionBar(view_content_detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        if (isAccept == "0") {
            supportActionBar?.title = "Pending"
        } else if (isAccept == "1") {
            supportActionBar?.title = "Proses"
        } else if (isAccept == "2") {
            supportActionBar?.title = "Selesai"
        }
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun initializeData() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        adapterListPesananLaundry = AdapterPesananUser(this)
        adapterListPesananLaundry.interfaceListPesananLaundry = this
        view_collection_swiperefreshlayout.setOnRefreshListener(this)
        view_collection_recyclerview?.layoutManager = layoutManager
        view_collection_recyclerview?.adapter = adapterListPesananLaundry
        presenterListLaundry.getRole()
    }

    private fun initializeProgressDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)
    }

    private fun initializePresenter() {
        componentActivity?.inject(this)
        presenterListLaundry.attachView(this)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onRefresh() {
        view_no_data.visibility = View.GONE
        view_collection_recyclerview.post {
            adapterListPesananLaundry.collectionData?.clear()
            adapterListPesananLaundry.notifyDataSetChanged()
        }
        presenterListLaundry.loadTransaction(isAccept)
        view_collection_swiperefreshlayout?.isRefreshing = false
    }

    override fun showLoadingProcess() {
        progressDialog.show()
    }

    override fun showError() {
        progressDialog.dismiss()
    }

    override fun showListPesanan(body: ArrayList<ModelListPesananLaundry>?) {
        adapterListPesananLaundry.collectionData?.clear()
        progressDialog.dismiss()
        body?.reverse()
        adapterListPesananLaundry.collectionData = body
        adapterListPesananLaundry.notifyDataSetChanged()
    }

    override fun showListPesananUser(body: ArrayList<ModelListPesananLaundry>?) {
        if (body?.size == 0) {
            view_no_data.visibility = View.VISIBLE
        } else {
            adapterListPesananLaundry.collectionData?.clear()
            progressDialog.dismiss()
            body?.reverse()
            adapterListPesananLaundry.collectionData = body
            adapterListPesananLaundry.notifyDataSetChanged()
        }

    }

    override fun showToast(toast: String) {
        progressDialog.dismiss()
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()

    }

    override fun showSuccessAccept(
        toast: String,
        position: Int,
        accept: String
    ) {
        progressDialog.dismiss()
        if (accept == "1") {
            Toast.makeText(this, "Memproses pesanan", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Pesanan telah selesai", Toast.LENGTH_SHORT).show()
        }
        adapterListPesananLaundry.collectionData?.removeAt(position)
        adapterListPesananLaundry.notifyItemRemoved(position) //notifies changes in adapter, in this case use the notifyItemMoved
        adapterListPesananLaundry.notifyDataSetChanged()
    }

    override fun showSuccessUpload(toast: String) {
        progressDialog.dismiss()
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()
        onRefresh()
    }

    override fun showRole(role: String?) {
        adapterListPesananLaundry.role = role.toString()
        presenterListLaundry.loadTransaction(isAccept)

    }

    override fun successDelete(position: Int) {
        progressDialog.dismiss()
        adapterListPesananLaundry.collectionData?.removeAt(position)
        adapterListPesananLaundry.notifyItemRemoved(position) //notifies changes in adapter, in this case use the notifyItemMoved
        adapterListPesananLaundry.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPesananRemove(position: Int, content: ModelListPesananLaundry) {
        val dialogClickListener =
            DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        progressDialog.show()

                        presenterListLaundry.deletePesanan(content, position)
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        dialog.dismiss()
                    }
                }
            }

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Apa anda ingin membatalkan pesanan ini?")
            .setPositiveButton("Ya", dialogClickListener)
            .setNegativeButton("Tidak", dialogClickListener).show()



    }

    override fun onPesananAccept(position: Int, content: ModelListPesananLaundry) {
        progressDialog.show()
        presenterListLaundry.acceptPesanan(content, position)

    }

    override fun onPesananDetail(position: Int, content: ModelListPesananLaundry) {
        val intent = Intent(this.content, DetailPesananActivity::class.java)
        intent.putExtra(UtilConstant.PESANAN_ID, content?.id)
        intent.putExtra(UtilConstant.USER_ID, content?.id_user)
        intent.putExtra(UtilConstant.USER_NAME, content?.nama_user)
        intent.putExtra(UtilConstant.USER_HP, content?.no_user)
        intent.putExtra(UtilConstant.USER_FOTO, content?.foto_user)
        intent.putExtra(UtilConstant.USER_PAKET, content?.pesanan)
        intent.putExtra(UtilConstant.USER_KURIR, content?.kurir)
        intent.putExtra(UtilConstant.USER_TOTAL, content?.total_harga)
        intent.putExtra(UtilConstant.USER_ALAMAT, content?.alamat_user)
        startActivity(intent)
    }

    override fun onUploadBukti(position: Int, content: ModelListPesananLaundry) {
        when {
            Build.VERSION.SDK_INT < Build.VERSION_CODES.M -> {
                idUpload = content.id.toString()
                Pix.start(
                    this@PesananUserActivity,                    //Activity or Fragment Instance
                    100,                //Request code for activity results
                    1
                )    //Number of images to restict selection count)
            }
            checkPermission() -> {
                idUpload = content.id.toString()
                Pix.start(
                    this@PesananUserActivity,                    //Activity or Fragment Instance
                    100,                //Request code for activity results
                    1
                )    //Number of images to restict selection count
            }
            else -> requestPermission()
        }
    }

    override fun onLihatbukti(position: Int, content: ModelListPesananLaundry) {
        val intent = Intent(this.content, FullImageActivity::class.java)
        intent.putExtra(UtilConstant.BUKTI_FOTO, UtilConstant.HOST + content?.bukti)
        startActivity(intent)
    }


    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            100
        )
    }


    fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.CAMERA
        )
        val result1 = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val result2 = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            val returnValue = data?.getStringArrayListExtra(Pix.IMAGE_RESULTS)
            val f = File(returnValue?.get(0))
            val d = BitmapDrawable(content.resources, f.absolutePath).bitmap
            bitmap = d
            progressDialog.show()
            presenterListLaundry.uploadBukti(d, idUpload)
        }
    }




}
