package id.com.android.laundry.feature.userlayer.activity.ui

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.fxn.pix.Pix
import id.com.android.laundry.R
import id.com.android.laundry.feature.ActivityBase
import id.com.android.laundry.feature.presenterlayer.PresenterRegister
import id.com.android.laundry.feature.viewlayer.ViewRegister
import id.com.android.laundry.model.ModelLogin
import id.com.android.laundry.tools.UtilConstant
import kotlinx.android.synthetic.main.activity_register.*
import java.io.File
import java.util.*
import javax.inject.Inject


class RegisterActivity :  ActivityBase(), ViewRegister {


    companion object {
        val LAYOUT_RESOURCE : Int   = id.com.android.laundry.R.layout.activity_register
        val CLASS_TAG : String      = "Activity Register"
    }

    @Inject
    lateinit var presenterRegister : PresenterRegister
    private lateinit var progressDialog : ProgressDialog
    private var bitmap: Bitmap? = null
    private lateinit var content: Context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT_RESOURCE)
        this.content = this
        componentActivity?.inject(this)
        presenterRegister.attachView(this)
        initProgressDialog()
        initializeClick()
        initializeToolbar()
    }

    private fun initializeToolbar() {
        setSupportActionBar(view_content_detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun initializeClick() {
        val myCalendar: Calendar = Calendar.getInstance()

        val date =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabel(year,monthOfYear,dayOfMonth)
            }

        tvRegisUserDate.setOnClickListener {
            DatePickerDialog(
                this@RegisterActivity, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        btnUploadPhoto.setOnClickListener {
            when {
                Build.VERSION.SDK_INT < Build.VERSION_CODES.M -> {
                    initDialogPhoto()
                }
                checkPermission() -> {
                    initDialogPhoto()
                }
                else -> requestPermission()
            }
        }

        button_register.setOnClickListener {
            val selectedId: Int = radio.getCheckedRadioButtonId()
            var radioButton = findViewById<View>(selectedId) as RadioButton

            if(etRegisUserUsername.text.toString() == ""){
                Toast.makeText(content, "Username tidak boleh kosong", Toast.LENGTH_LONG).show()
            }else if(etRegisUserPass.text.toString()==""){
                Toast.makeText(content, "Password tidak boleh kosong", Toast.LENGTH_LONG).show()
            }else if(etRegisUserNama.text.toString()==""){
                Toast.makeText(content, "Nama tidak boleh kosong", Toast.LENGTH_LONG).show()
            }else if(etRegisUserAlamat.text.toString()==""){
                Toast.makeText(content, "Alamat tidak boleh kosong", Toast.LENGTH_LONG).show()
            }else if(etRegisUserNoHP.text.toString()==""){
                Toast.makeText(content, "Nomor Telephone tidak boleh kosong", Toast.LENGTH_LONG).show()
            }else if(tvRegisUserDate.text==""){
                Toast.makeText(content, "Tanggal lahir tidak boleh kosong", Toast.LENGTH_LONG).show()
            }else{
                progressDialog.show()
                presenterRegister.regis(
                    etRegisUserUsername.text.toString(),
                    etRegisUserNama.text.toString(),
                    etRegisUserPass.text.toString(),
                    etRegisUserNoHP.text.toString(),
                    bitmap,
                    etRegisUserAlamat.text.toString(),
                    tvRegisUserDate.text.toString(),
                    radioButton.text.toString(),
                    "Tidak",
                    UtilConstant.LEVEL_USER
                )
            }
        }

    }

    private fun initDialogPhoto() {
        Pix.start(this@RegisterActivity,                    //Activity or Fragment Instance
            100,                //Request code for activity results
            1)    //Number of images to restict selection count

    }

    private fun initProgressDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)
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



    private fun updateLabel(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        tvRegisUserDate.text = "$dayOfMonth / $monthOfYear / $year"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            ivImageRegister.visibility = View.VISIBLE
            val returnValue = data?.getStringArrayListExtra(Pix.IMAGE_RESULTS)
            val f = File(returnValue?.get(0))
            val d = BitmapDrawable(content.resources, f.absolutePath).bitmap
            bitmap = d
            ivImageRegister.setImageBitmap(d)
        }
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

    override fun showFailedToast(message: String) {
        progressDialog.dismiss()
        Toast.makeText(content, message, Toast.LENGTH_LONG).show()
    }

    override fun showSuccessRegis() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        progressDialog.dismiss()
        onBackPressed()
    }

    override fun showUser(modelLogin: ModelLogin?) {

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return false
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val loginActivity = Intent(this.content, LoginActivity::class.java)
        startActivity(loginActivity)
        finish()
    }
}