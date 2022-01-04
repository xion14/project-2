package id.com.android.laundry.feature.userlayer.activity.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import id.com.android.laundry.R
import id.com.android.laundry.tools.UtilConstant
import kotlinx.android.synthetic.main.activity_detail_pesanan.*
import kotlinx.android.synthetic.main.activity_transaction_choose.*
import kotlinx.android.synthetic.main.activity_transaction_choose.view_content_detail_toolbar
import kotlinx.android.synthetic.main.fragment_home.*

class TransactionChooseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_choose)
        initializeToolbar()
        buttonPending.setOnClickListener {
            val intent = Intent(this, PesananUserActivity::class.java)
            intent.putExtra(UtilConstant.IS_ACCEPT,"0")
            startActivity(intent)
        }
        buttonProses.setOnClickListener {
            val intent = Intent(this, PesananUserActivity::class.java)
            intent.putExtra(UtilConstant.IS_ACCEPT,"1")
            startActivity(intent)
        }
        buttonSelesai.setOnClickListener {
            val intent = Intent(this, PesananUserActivity::class.java)
            intent.putExtra(UtilConstant.IS_ACCEPT,"2")
            startActivity(intent)
        }

    }
    private fun initializeToolbar() {
        setSupportActionBar(view_content_detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
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



