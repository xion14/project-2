package id.com.android.weatherfinder.feature

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import id.com.android.weatherfinder.controller.ControllerApplication
import id.com.android.weatherfinder.injector.component.ComponentActivity
import id.com.android.weatherfinder.injector.component.DaggerComponentActivity
import id.com.android.weatherfinder.injector.module.ModuleActivity

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