package id.com.android.laundry.feature.userlayer.activity.ui

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import id.com.android.laundry.R
import id.com.android.laundry.feature.ActivityBase
import id.com.android.laundry.feature.presenterlayer.PresenterLogin
import id.com.android.laundry.feature.viewlayer.ViewLogin
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject


class LoginActivity : ActivityBase(), ViewLogin {

    @Inject
    lateinit var presenterLogin : PresenterLogin

    companion object {
        val LAYOUT_RESOURCE : Int   = R.layout.activity_login
        val CLASS_TAG : String      = "Activity Login"
    }

    private lateinit var progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT_RESOURCE)
        componentActivity?.inject(this)
        presenterLogin.attachView(this)
        initializeProgressDialog()
        initializeClick()
    }

    private fun initializeProgressDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)
    }

    private fun initializeClick() {
        loginTvDaftar.setOnClickListener {
            val dialogClickListener =
                DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {

                            val intent          = Intent(this, RegisterActivity::class.java)
                            startActivity(intent)
                        }
                        DialogInterface.BUTTON_NEGATIVE -> {

                            val intent          = Intent(this, RegisterLaundryActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }

            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage("Daftar sebagai?").setPositiveButton("User", dialogClickListener)
                .setNegativeButton("Laundry", dialogClickListener).show()

        }
        loginButton.setOnClickListener {
            progressDialog.show()
            presenterLogin.login(etUsername.text.toString(), etPassword.text.toString())
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
        val mainActivity = Intent(this, MainActivity::class.java)
        startActivity(mainActivity)
        finish()
    }
}