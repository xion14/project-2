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
import id.com.android.laundry.feature.ActivityBase
import id.com.android.laundry.feature.presenterlayer.PresenterLogin
import id.com.android.laundry.feature.viewlayer.ViewLogin
import id.com.android.laundry.tools.UtilConstant
import kotlinx.android.synthetic.main.activity_edit_admin.*
import kotlinx.android.synthetic.main.activity_list_user.*
import javax.inject.Inject

class EditAdminActivity : ActivityBase(), ViewLogin {

    @Inject
    lateinit var presenterLogin : PresenterLogin

    companion object {
        val LAYOUT_RESOURCE : Int   = R.layout.activity_edit_admin
        val CLASS_TAG : String      = "Activity Login"
    }

    private lateinit var progressDialog : ProgressDialog
    var id : String = ""
    var name : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT_RESOURCE)
        componentActivity?.inject(this)
        presenterLogin.attachView(this)
        id = intent.getStringExtra(UtilConstant.USER_ID)
        name = intent.getStringExtra(UtilConstant.USER_NAME)
        etUsername.setText(name)
        initializeProgressDialog()
        initializeClick()
        initializeToolbar()
    }

    private fun initializeProgressDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)
    }

    private fun initializeClick() {
        ubahButton.setOnClickListener {
            progressDialog.show()
            presenterLogin.edit(etUsername.text.toString(), etPassword.text.toString(),id)
        }

    }

    override fun showLoadingProcess() {

    }

    override fun showFailedToast(message: String) {
        progressDialog.dismiss()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showSuccessLogin() {
        progressDialog.dismiss()
        Toast.makeText(this, "Sukses mengubah user", Toast.LENGTH_SHORT).show()

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

    private fun initializeToolbar() {
        setSupportActionBar(view_content_detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }



}