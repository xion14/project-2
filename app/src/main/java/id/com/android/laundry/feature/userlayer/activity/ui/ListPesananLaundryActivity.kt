package id.com.android.laundry.feature.userlayer.activity.ui

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.com.android.laundry.R
import id.com.android.laundry.adapter.AdapterListPesananLaundry
import id.com.android.laundry.feature.ActivityBase
import id.com.android.laundry.feature.InterfaceListPesananLaundry
import id.com.android.laundry.feature.presenterlayer.PresenterListPesananLaundry
import id.com.android.laundry.feature.viewlayer.ViewListPesananLaundry
import id.com.android.laundry.model.ModelListPesananLaundry
import id.com.android.laundry.model.ModelLogin
import id.com.android.laundry.tools.UtilConstant
import kotlinx.android.synthetic.main.activity_list_laundry.*
import javax.inject.Inject


class ListPesananLaundryActivity : ActivityBase(), ViewListPesananLaundry,
    SwipeRefreshLayout.OnRefreshListener, InterfaceListPesananLaundry {


    private lateinit var content: Context
    @Inject
    lateinit var presenterListLaundry: PresenterListPesananLaundry
    private lateinit var progressDialog : ProgressDialog
    private lateinit var adapterListPesananLaundry: AdapterListPesananLaundry
    var arrayListData : ArrayList<ModelLogin> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_pesanan_laundry)
        this.content = this
        supportActionBar?.hide()
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
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun initializeData() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        adapterListPesananLaundry = AdapterListPesananLaundry(this)

        adapterListPesananLaundry.interfaceListPesananLaundry = this
        view_collection_swiperefreshlayout.setOnRefreshListener(this)
        view_collection_recyclerview?.layoutManager = layoutManager
        view_collection_recyclerview?.adapter = adapterListPesananLaundry

        presenterListLaundry.loadPesanan()
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
        view_collection_recyclerview.post{
            adapterListPesananLaundry.collectionData?.clear()
            adapterListPesananLaundry.notifyDataSetChanged()
        }
        presenterListLaundry.loadPesanan()
        view_collection_swiperefreshlayout?.isRefreshing = false
    }

    override fun showLoadingProcess() {
        progressDialog.show()
    }

    override fun showError() {
        progressDialog.dismiss()
        Toast.makeText(this,"Please ty again", Toast.LENGTH_SHORT).show()
    }

    override fun showListPesanan(body: ArrayList<ModelListPesananLaundry>?) {
        adapterListPesananLaundry.collectionData?.clear()
        progressDialog.dismiss()
        body?.reverse()
        adapterListPesananLaundry.collectionData = body
        adapterListPesananLaundry.notifyDataSetChanged()
    }

    override fun showListPesananUser(body: ArrayList<ModelListPesananLaundry>?) {

    }

    override fun showToast(toast: String) {
        progressDialog.dismiss()
        Toast.makeText(this,toast, Toast.LENGTH_SHORT).show()

    }

    override fun showSuccessAccept(
        toast: String,
        position: Int,
        accept: String
    ) {
        progressDialog.dismiss()
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()
        adapterListPesananLaundry.collectionData?.removeAt(position)
        adapterListPesananLaundry.notifyItemRemoved(position) //notifies changes in adapter, in this case use the notifyItemMoved
        adapterListPesananLaundry.notifyDataSetChanged()
    }

    override fun showSuccessUpload(toast: String) {

    }

    override fun showRole(role: String?) {

    }

    override fun successDelete(position: Int) {

    }

    override fun onResume() {
        super.onResume()

        presenterListLaundry.loadPesanan()
    }

    override fun onPesananRemove(position: Int, content: ModelListPesananLaundry) {

    }

    override fun onPesananAccept(position: Int, content: ModelListPesananLaundry) {
        progressDialog.show()
        presenterListLaundry.acceptPesanan(content,position)

    }

    override fun onPesananDetail(position: Int, content: ModelListPesananLaundry) {
        val intent = Intent(this.content, DetailPesananActivity::class.java)
        intent.putExtra(UtilConstant.PESANAN_ID, content?.id)
        intent.putExtra(UtilConstant.USER_ID,content?.id_user)
        intent.putExtra(UtilConstant.USER_NAME,content?.nama_user)
        intent.putExtra(UtilConstant.USER_HP, content?.no_user)
        intent.putExtra(UtilConstant.USER_FOTO, content?.foto_user)
        intent.putExtra(UtilConstant.USER_PAKET, content?.pesanan)
        intent.putExtra(UtilConstant.USER_KURIR, content?.kurir)
        intent.putExtra(UtilConstant.USER_TOTAL, content?.total_harga)
        intent.putExtra(UtilConstant.USER_ALAMAT, content?.alamat_user)
        startActivity(intent)
    }

    override fun onUploadBukti(position: Int, content: ModelListPesananLaundry) {
    }

    override fun onLihatbukti(position: Int, content: ModelListPesananLaundry) {

    }


}
