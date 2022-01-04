package id.com.android.laundry.feature.presenterlayer

import android.os.Handler
import id.com.android.laundry.feature.viewlayer.ViewSplash
import id.com.android.laundry.repository.RepositorySession
import id.com.android.laundry.tools.UtilConstant
import javax.inject.Inject


class PresenterSplash : PresenterBase<ViewSplash> {

    private var splashRunnable: Runnable? = null
    private var splashHandler: Handler? = null
    private var mRepositorySession : RepositorySession?     = null
    @Inject
    constructor(mRepositorySession: RepositorySession) {
        splashHandler                       = android.os.Handler()
        this.mRepositorySession = mRepositorySession
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
        checkNotNull(mRepositorySession?.userSession){
            viewLayer?.notLogin()
            return
        }
        viewLayer?.showLogoAnimation()
    }

}
