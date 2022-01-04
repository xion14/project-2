package id.com.android.laundry.feature.userlayer.activity.ui

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import id.com.android.laundry.R
import id.com.android.laundry.adapter.AdapterPaginationTransaction
import id.com.android.laundry.feature.ActivityBase
import id.com.android.laundry.feature.presenterlayer.PresenterLogin
import id.com.android.laundry.feature.userlayer.fragment.FragmentTransaction
import id.com.android.laundry.feature.viewlayer.ViewLogin
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register_laundry.*
import kotlinx.android.synthetic.main.activity_transaction.*
import javax.inject.Inject

class TransactionActivity : ActivityBase() {

    companion object {
        val LAYOUT_RESOURCE : Int   = R.layout.activity_transaction
        val CLASS_TAG : String      = "Activity Transaction"
    }
    private lateinit var adapterCollectionTransaction        : AdapterPaginationTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT_RESOURCE)
        componentActivity?.inject(this)
        initializeTabNavigation()
        initializeToolbar()
    }


    private fun initializeToolbar() {
        setSupportActionBar(view_content_detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun initializeTabNavigation() {
        view_transaksi_pager?.scrollEnable = false
        view_transaksi_tablayout.setupWithViewPager(view_transaksi_pager)
        adapterCollectionTransaction                 = AdapterPaginationTransaction(this, supportFragmentManager)
        view_transaksi_pager.adapter  = adapterCollectionTransaction
        view_transaksi_pager?.addOnPageChangeListener(object : androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                onPageChanged(position)
            }
        })

        for (i in 0 until 3) {
            val fragment = FragmentTransaction()
            fragment.statusPage = i.toString()
            var title : String = ""
            if(i==0){
                title = "Pending"
            }else if(i==1){
                title = "Diproses"
            }else{
                title = "Selesai"
            }
            adapterCollectionTransaction.addFragment(fragment, title)
        }
        adapterCollectionTransaction.notifyDataSetChanged()
    }

    private fun onPageChanged(position: Int) {
        val fragment = adapterCollectionTransaction?.getItem(position) as FragmentTransaction?
        fragment?.loadTransaction(position.toString())
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
}