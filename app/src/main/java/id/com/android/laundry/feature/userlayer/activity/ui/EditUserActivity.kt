package id.com.android.laundry.feature.userlayer.activity.ui

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
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
import id.com.android.laundry.feature.ActivityBase
import id.com.android.laundry.feature.presenterlayer.PresenterRegister
import id.com.android.laundry.feature.viewlayer.ViewRegister
import id.com.android.laundry.model.ModelLogin
import id.com.android.laundry.tools.UtilConstant
import id.com.android.laundry.tools.UtilImage
import kotlinx.android.synthetic.main.activity_edit_user.*
import kotlinx.android.synthetic.main.activity_edit_user.btnUploadPhoto
import kotlinx.android.synthetic.main.activity_edit_user.etRegisJasaAlamat
import kotlinx.android.synthetic.main.activity_edit_user.etRegisJasaNama
import kotlinx.android.synthetic.main.activity_edit_user.etRegisJasaNamaBank
import kotlinx.android.synthetic.main.activity_edit_user.etRegisJasaNoHP
import kotlinx.android.synthetic.main.activity_edit_user.etRegisJasaPass
import kotlinx.android.synthetic.main.activity_edit_user.etRegisJasaRekening
import kotlinx.android.synthetic.main.activity_edit_user.etRegisJasaUsername
import kotlinx.android.synthetic.main.activity_edit_user.ivImageRegister
import kotlinx.android.synthetic.main.activity_edit_user.radio
import kotlinx.android.synthetic.main.activity_edit_user.view_content_detail_toolbar
import kotlinx.android.synthetic.main.activity_edit_user.view_register_laundry_daftar
import java.io.File
import javax.inject.Inject

class EditUserActivity :  ActivityBase(), ViewRegister {


    companion object {
        val LAYOUT_RESOURCE : Int   = id.com.android.laundry.R.layout.activity_edit_user
        val CLASS_TAG : String      = "Activity Register"
    }

    @Inject
    lateinit var presenterRegister : PresenterRegister
    private lateinit var progressDialog : ProgressDialog
    private var bitmap: Bitmap? = null
    private lateinit var content: Context

    var role : String = ""
    var modelUser : ModelLogin? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT_RESOURCE)
        this.content = this
        componentActivity?.inject(this)
        presenterRegister.attachView(this)
        initProgressDialog()
        initializeClick()
        initializeToolbar()
        presenterRegister.getUser()
    }

    private fun initializeToolbar() {
        setSupportActionBar(view_content_detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = "Edit Profile"
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun initializeClick() {

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

        view_register_laundry_daftar.setOnClickListener {
            if(modelUser?.level == UtilConstant.LEVEL_USER || modelUser?.level == "admin"){
                if(etRegisJasaUsername.text.toString() == ""){
                    Toast.makeText(content, "Username tidak boleh kosong", Toast.LENGTH_LONG).show()
                }else if(etRegisJasaNama.text.toString()==""){
                    Toast.makeText(content, "Nama tidak boleh kosong", Toast.LENGTH_LONG).show()
                }else if(etRegisJasaAlamat.text.toString()==""){
                    Toast.makeText(content, "Alamat tidak boleh kosong", Toast.LENGTH_LONG).show()
                }else if(etRegisJasaNoHP.text.toString()==""){
                    Toast.makeText(content, "Nomor Telephone tidak boleh kosong", Toast.LENGTH_LONG).show()
                } else{
                    progressDialog.show()
                    presenterRegister.updateRoleUser(
                        modelUser?.id,
                        etRegisJasaUsername.text.toString(),
                        etRegisJasaNama.text.toString(),
                        etRegisJasaPass.text.toString(),
                        etRegisJasaNoHP.text.toString(),
                        etRegisJasaAlamat.text.toString()
                    )
                }
            }else{
                val selectedId: Int = radio.getCheckedRadioButtonId()
                var radioButton = findViewById<View>(selectedId) as RadioButton

                if(etRegisJasaUsername.text.toString() == ""){
                    Toast.makeText(content, "Username tidak boleh kosong", Toast.LENGTH_LONG).show()
                }else if(etRegisJasaNama.text.toString()==""){
                    Toast.makeText(content, "Nama tidak boleh kosong", Toast.LENGTH_LONG).show()
                }else if(etRegisJasaAlamat.text.toString()==""){
                    Toast.makeText(content, "Alamat tidak boleh kosong", Toast.LENGTH_LONG).show()
                }else if(etRegisJasaNoHP.text.toString()==""){
                    Toast.makeText(content, "Nomor Telephone tidak boleh kosong", Toast.LENGTH_LONG).show()
                }
                else if(etRegisJasaNamaBank.text.toString()==""){
                    Toast.makeText(content, "Nama bank tidak boleh kosong", Toast.LENGTH_LONG).show()
                }
                else if(etRegisJasaRekening.text.toString()==""){
                    Toast.makeText(content, "Nomor rekening tidak boleh kosong", Toast.LENGTH_LONG).show()
                } else{
                    progressDialog.show()
                    presenterRegister.updateRoleLaundry(
                        modelUser?.id,
                        etRegisJasaUsername.text.toString(),
                        etRegisJasaNama.text.toString(),
                        etRegisJasaPass.text.toString(),
                        etRegisJasaNoHP.text.toString(),
                        etRegisJasaAlamat.text.toString(),
                        etRegisJasaNamaBank.text.toString(),
                        etRegisJasaRekening.text.toString(),
                        radioButton.text.toString()
                    )
                }
            }

        }

    }



    private fun initDialogPhoto() {
        Pix.start(this@EditUserActivity,                    //Activity or Fragment Instance
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




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            ivImageRegister.visibility = View.VISIBLE
            val returnValue = data?.getStringArrayListExtra(Pix.IMAGE_RESULTS)
            val f = File(returnValue?.get(0))
            val d = BitmapDrawable(content.resources, f.absolutePath).bitmap
            bitmap = d
            ivImageRegister.setImageBitmap(d)
            progressDialog.show()
            presenterRegister.updateProfilePicture(d)
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
        try {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        } catch (e: Exception) {
        }
        progressDialog.dismiss()
        Toast.makeText(this,"Sukses mengubah user",Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun showUser(modelLogin: ModelLogin?) {
        modelUser = modelLogin
        if(modelLogin?.level==UtilConstant.LEVEL_USER){
            layout_edit_user_laundry.visibility = View.GONE
            etRegisJasaUsername.setText(modelUser?.username)
            etRegisJasaNama.setText(modelUser?.fullname)
            etRegisJasaAlamat.setText(modelUser?.alamat)
            etRegisJasaNoHP.setText(modelUser?.phone)
        }else{
            layout_edit_user_laundry.visibility = View.VISIBLE
            etRegisJasaUsername.setText(modelUser?.username)
            etRegisJasaNama.setText(modelUser?.fullname)
            etRegisJasaAlamat.setText(modelUser?.alamat)
            etRegisJasaNoHP.setText(modelUser?.phone)
            etRegisJasaNamaBank.setText(modelUser?.bank)
            etRegisJasaRekening.setText(modelUser?.rekening)
        }

        UtilImage.loadImageWithoutPlaceholder(ivImageRegister,
            UtilConstant.HOST+modelLogin?.foto,this)


    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
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
}