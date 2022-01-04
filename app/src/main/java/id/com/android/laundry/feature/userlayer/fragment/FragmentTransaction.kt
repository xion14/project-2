package id.com.android.laundry.feature.userlayer.fragment

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.com.android.laundry.R
import id.com.android.laundry.adapter.AdapterPesananUser
import id.com.android.laundry.feature.ActivityBase
import id.com.android.laundry.feature.InterfaceListPesananLaundry
import id.com.android.laundry.feature.presenterlayer.PresenterListPesananLaundry
import id.com.android.laundry.feature.userlayer.activity.ui.DetailPesananActivity
import id.com.android.laundry.feature.viewlayer.ViewListPesananLaundry
import id.com.android.laundry.model.ModelListPesananLaundry
import id.com.android.laundry.model.ModelLogin
import id.com.android.laundry.tools.UtilConstant
import kotlinx.android.synthetic.main.activity_list_laundry.*
import javax.inject.Inject

class FragmentTransaction: FragmentBase(), ViewListPesananLaundry,
    SwipeRefreshLayout.OnRefreshListener, InterfaceListPesananLaundry {



    private lateinit var content: Context
    @Inject
    lateinit var presenterListLaundry: PresenterListPesananLaundry
    private lateinit var progressDialog : ProgressDialog
    private lateinit var adapterListPesananLaundry: AdapterPesananUser
    var arrayListData : ArrayList<ModelLogin> = ArrayList()

    companion object {
        var LAYOUT_RESOURCE : Int           = R.layout.fragment_transaction

        fun newInstance(position: Int): FragmentTransaction {
            val fragmentMarketWebList = FragmentTransaction()
            val passingBundle = Bundle()
            fragmentMarketWebList.arguments = passingBundle
            return fragmentMarketWebList
        }
    }

    var statusPage = "0"



    //View Market Detail Protocol
    override fun onRefresh() {
        view_collection_recyclerview.post{
            adapterListPesananLaundry.collectionData?.clear()
            adapterListPesananLaundry.notifyDataSetChanged()
        }
        presenterListLaundry.loadTransaction(statusPage)
    }






    fun loadTransaction(isAccept: String){
        statusPage = isAccept
        presenterListLaundry.loadTransaction(isAccept)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializePresenter()
    }

    private fun initializePresenter() {
        (activity as ActivityBase).componentActivity?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(LAYOUT_RESOURCE, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeProgressDialog()
        initializeData()
    }


    override fun onDestroy() {
        presenterListLaundry.detachView()
        super.onDestroy()
    }

    private fun initializeData() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)

        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        adapterListPesananLaundry = AdapterPesananUser(activity)

        view_collection_swiperefreshlayout.setOnRefreshListener(this)
        view_collection_recyclerview?.layoutManager = layoutManager
        view_collection_recyclerview?.adapter = adapterListPesananLaundry

    }

    private fun initializeProgressDialog() {
        progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Please Wait")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)
    }

    override fun showLoadingProcess() {

    }

    override fun showError() {
        Toast.makeText(activity,"Please ty again", Toast.LENGTH_SHORT).show()
    }

    override fun showListPesanan(body: ArrayList<ModelListPesananLaundry>?) {
        adapterListPesananLaundry.collectionData?.clear()
        progressDialog.dismiss()
        body?.reverse()
        adapterListPesananLaundry.collectionData = body
        adapterListPesananLaundry.notifyDataSetChanged()
    }

    override fun showListPesananUser(body: ArrayList<ModelListPesananLaundry>?) {
        adapterListPesananLaundry.collectionData?.clear()
        body?.reverse()
        adapterListPesananLaundry.collectionData = body
        adapterListPesananLaundry.notifyDataSetChanged()
    }

    override fun showToast(toast: String) {
        Toast.makeText(activity,toast, Toast.LENGTH_SHORT).show()

    }

    override fun showSuccessAccept(
        toast: String,
        position: Int,
        accept: String
    ) {
        Toast.makeText(activity, toast, Toast.LENGTH_SHORT).show()
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