package id.com.android.laundry.feature.userlayer.activity.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.com.android.laundry.R
import id.com.android.laundry.tools.UtilConstant
import id.com.android.laundry.tools.UtilImage
import kotlinx.android.synthetic.main.activity_full_image.*
import kotlinx.android.synthetic.main.activity_pesan_laundry.*

class FullImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_image)

        UtilImage.loadImageWithoutPlaceholder(imageFull, intent.getStringExtra(UtilConstant.BUKTI_FOTO),this)
    }
}