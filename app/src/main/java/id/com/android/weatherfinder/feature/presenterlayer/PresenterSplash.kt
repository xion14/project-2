package id.com.android.weatherfinder.feature.presenterlayer

import android.os.Handler
import id.com.android.weatherfinder.feature.viewlayer.ViewSplash
import id.com.android.weatherfinder.tools.UtilConstant
import javax.inject.Inject


class PresenterSplash : PresenterBase<ViewSplash> {

    private var splashRunnable: Runnable? = null
    private var splashHandler: Handler? = null

    @Inject
    constructor() {
        splashHandler                       = android.os.Handler()
    }

    fun viewCreated() {
        createSplashRunnable()
    }


    fun viewPaused() {
        splashRunnable?.let { splashHandler?.removeCallbacks(it) }
    }

    fun createSplashRunnable() {
        splashRunnable?.let {
            splashHandler?.removeCallbacks(it)
            splashHandler?.postDelayed(it, UtilConstant.SPLASH_DURATION.toLong())
        }
        viewLayer?.showLogoAnimation()
    }

}
