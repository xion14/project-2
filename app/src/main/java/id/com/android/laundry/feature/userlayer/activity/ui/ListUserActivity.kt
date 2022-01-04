
package id.com.android.laundry.feature.userlayer.activity.ui

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.com.android.laundry.R
import id.com.android.laundry.adapter.AdapterListUser
import id.com.android.laundry.feature.ActivityBase
import id.com.android.laundry.feature.InterfaceListUser
import id.com.android.laundry.feature.presenterlayer.PresenterListUser
import id.com.android.laundry.feature.viewlayer.ViewListUser
import id.com.android.laundry.model.ModelLogin
import id.com.android.laundry.tools.UtilConstant
import kotlinx.android.synthetic.main.activity_edit_harga.*
import kotlinx.android.synthetic.main.activity_list_user.*
import kotlinx.android.synthetic.main.activity_list_user.view_content_detail_toolbar
import javax.inject.Inject


class ListUserActivity : ActivityBase(), ViewListUser,
    SwipeRefreshLayout.OnRefreshListener, InterfaceListUser {


    private lateinit var content: Context
    @Inject
    lateinit var presenterListUser: PresenterListUser
    private lateinit var progressDialog : ProgressDialog
    private var initCategory = false
    private lateinit var adapterListUser: AdapterListUser
    var arrayListData : ArrayList<ModelLogin> = ArrayList()
    var role : String = ""
    var idFocus : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_user)
        this.content = this
        supportActionBar?.hide()
        role = intent.getStringExtra(UtilConstant.ROLE)
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
        supportActionBar?.title = "List User"
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun initializeData() {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        layoutManager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        adapterListUser = AdapterListUser(this)

        adapterListUser.interfaceListUser = this
        view_collection_swiperefreshlayout.setOnRefreshListener(this)
        view_collection_recyclerview?.layoutManager = layoutManager
        view_collection_recyclerview?.adapter = adapterListUser

        presenterListUser.loadListUser(role)
    }

    private fun initializeProgressDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)
    }

    private fun initializePresenter() {
        componentActivity?.inject(this)
        presenterListUser.attachView(this)
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
        presenterListUser.loadListUser(role)
        view_collection_swiperefreshlayout?.isRefreshing = false
    }

    override fun showLoadingProcess() {
        progressDialog.show()
    }

    override fun showToast(toast: String?) {
        progressDialog.dismiss()
        Toast.makeText(this,toast, Toast.LENGTH_SHORT).show()
    }

    override fun showListUser(body: ArrayList<ModelLogin>?) {
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

    override fun showSuccessDeleteUser(position: Int) {
        progressDialog.dismiss()
        adapterListUser.collectionData?.removeAt(position)
        adapterListUser.notifyItemRemoved(position) //notifies changes in adapter, in this case use the notifyItemMoved
        adapterListUser.notifyDataSetChanged()
    }

    override fun updateUser(position: Int, content: ModelLogin?) {
        val intent = Intent(this, EditAdminActivity::class.java)
        intent.putExtra(UtilConstant.USER_ID, content?.id)
        intent.putExtra(UtilConstant.USER_NAME, content?.username )
        startActivity(intent)
    }

    override fun deleteUser(position: Int, content: ModelLogin?) {
        idFocus = content?.id.toString()
        val dialogClickListener =
            DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        progressDialog.show()
                        presenterListUser.deleteUser(position,content)
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                            dialog.dismiss()

                    }
                }
            }

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Apa anda yakin ingin menghapus user ini?").setPositiveButton("Ya", dialogClickListener)
            .setNegativeButton("Tidak", dialogClickListener).show()
    }


}
