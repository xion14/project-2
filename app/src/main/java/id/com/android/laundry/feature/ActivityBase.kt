package id.com.android.laundry.feature

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import id.com.android.laundry.controller.ControllerApplication
import id.com.android.laundry.injector.component.ComponentActivity
import id.com.android.laundry.injector.component.DaggerComponentActivity
import id.com.android.laundry.injector.module.ModuleActivity

@SuppressLint("Registered")
open class ActivityBase : AppCompatActivity() {


    var componentActivity : ComponentActivity? = null
        get() {
            checkNotNull(field) { return DaggerComponentActivity.builder().componentApplication(ControllerApplication.componentApplication).moduleActivity(ModuleActivity(this)).build() }
            return field
        }
    override fun onDestroy() {
        super.onDestroy()

    }


}