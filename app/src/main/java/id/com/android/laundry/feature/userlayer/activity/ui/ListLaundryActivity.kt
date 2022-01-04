package id.com.android.laundry.feature.userlayer.activity.ui

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.com.android.laundry.R
import id.com.android.laundry.adapter.AdapterListLaundry
import id.com.android.laundry.feature.ActivityBase
import id.com.android.laundry.feature.InterfaceListLaundry
import id.com.android.laundry.feature.presenterlayer.PresenterListLaundry
import id.com.android.laundry.feature.viewlayer.ViewListLaundry
import id.com.android.laundry.model.ModelLogin
import id.com.android.laundry.tools.UtilConstant
import kotlinx.android.synthetic.main.activity_list_laundry.*
import javax.inject.Inject

class ListLaundryActivity : ActivityBase(), ViewListLaundry,
    SwipeRefreshLayout.OnRefreshListener, InterfaceListLaundry {


    private lateinit var content: Context
    @Inject
    lateinit var presenterListLaundry: PresenterListLaundry
    private lateinit var progressDialog : ProgressDialog
    private var initCategory = false
    private lateinit var adapterListUser: AdapterListLaundry
    var arrayListData : ArrayList<ModelLogin> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_laundry)
        this.content = this
        supportActionBar?.hide()
        initializeToolbar()
        initializePresenter()
        initializeProgressDialog()
        initializeData()

    }

    private fun initializeToolbar() {
        setSupportActionBar(view_content_detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = "List Laundry"
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun initializeData() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        adapterListUser = AdapterListLaundry(this)

        adapterListUser.interfaceListLaundry = this
        view_collection_swiperefreshlayout.setOnRefreshListener(this)
        view_collection_recyclerview?.layoutManager = layoutManager
        view_collection_recyclerview?.adapter = adapterListUser

        presenterListLaundry.loadListLaundry()
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

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


    override fun onRefresh() {
        view_collection_recyclerview.post{
            adapterListUser.collectionData?.clear()
            adapterListUser.notifyDataSetChanged()
        }
        presenterListLaundry.loadListLaundry()
        view_collection_swiperefreshlayout?.isRefreshing = false
    }

    override fun showLoadingProcess() {
        progressDialog.show()
    }

    override fun showError() {
        progressDialog.dismiss()
        Toast.makeText(this,"Please ty again",Toast.LENGTH_SHORT).show()
    }

    override fun showLaundry(body: ArrayList<ModelLogin>?) {
        progressDialog.dismiss()
        body?.reverse()
        arrayListData = body!!
        for(i in 0 until body!!.size){
            if(!adapterListUser.collectionData?.contains(body[i])!!){
                adapterListUser.collectionData?.add(body[i])
            }
        }
        adapterListUser.notifyDataSetChanged()
    }

    override fun onPesanClicked(content: ModelLogin?) {
        val intent = Intent(this.content, PesanLaundryActivity::class.java)
        intent.putExtra(UtilConstant.LAUNDRY_ID,content?.id)
        intent.putExtra(UtilConstant.LAUNDRY_NAME,content?.fullname)
        intent.putExtra(UtilConstant.LAUNDRY_KURIR, content?.kurir)
        intent.putExtra(UtilConstant.LAUNDRY_HP, content?.phone)
        intent.putExtra(UtilConstant.LAUNDRY_HARGA, content?.harga)
        intent.putExtra(UtilConstant.LAUNDRY_FOTO, content?.foto)
        intent.putExtra(UtilConstant.LAUNDRY_REKENING, content?.rekening)
        intent.putExtra(UtilConstant.LAUNDRY_BANK, content?.bank)
        startActivity(intent)
    }

    override fun onDetailClilcked(content: ModelLogin?) {
        val intent = Intent(this.content, LaundryDetailActivity::class.java)
        intent.putExtra(UtilConstant.LAUNDRY_ID,content?.id)
        intent.putExtra(UtilConstant.LAUNDRY_NAME,content?.fullname)
        intent.putExtra(UtilConstant.LAUNDRY_KURIR, content?.kurir)
        intent.putExtra(UtilConstant.LAUNDRY_FOTO, content?.foto)
        intent.putExtra(UtilConstant.LAUNDRY_HP, content?.phone)
        intent.putExtra(UtilConstant.LAUNDRY_HARGA, content?.harga)
        intent.putExtra(UtilConstant.LAUNDRY_ALAMAT, content?.alamat)
        intent.putExtra(UtilConstant.LAUNDRY_REKENING, content?.rekening)
        intent.putExtra(UtilConstant.LAUNDRY_BANK, content?.bank)
        startActivity(intent)
    }



}
