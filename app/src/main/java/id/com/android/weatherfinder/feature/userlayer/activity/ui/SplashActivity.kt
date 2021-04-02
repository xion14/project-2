
package id.com.android.weatherfinder.feature.userlayer.activity.ui

import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.ScaleAnimation
import id.com.android.weatherfinder.R
import id.com.android.weatherfinder.feature.ActivityBase
import id.com.android.weatherfinder.feature.presenterlayer.PresenterSplash
import id.com.android.weatherfinder.feature.viewlayer.ViewSplash
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity :  ActivityBase(), ViewSplash {

    companion object {
        val LAYOUT_RESOURCE : Int   = R.layout.activity_splash
        val CLASS_TAG : String      = "Activity Splash"
    }

    @Inject
    lateinit var presenterSplash : PresenterSplash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT_RESOURCE)
        componentActivity?.inject(this)
        presenterSplash.attachView(this)
        presenterSplash.viewCreated()
    }

    override fun showLogoAnimation() {
        val anim = ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        anim.interpolator   = DecelerateInterpolator()
        anim.fillAfter      = true
        anim.duration       = 500
        view_splash_logo_imageview.startAnimation(anim)

        Handler().postDelayed({


        }, 2000)


    }

}