package id.com.android.laundry.feature.presenterlayer

import android.os.Handler
import id.com.android.laundry.feature.viewlayer.ViewHome
import id.com.android.laundry.repository.RepositorySession
import id.com.android.laundry.repository.RepositorySettings
import javax.inject.Inject


class PresenterProfile : PresenterBase<ViewHome> {

    private var splashRunnable: Runnable? = null
    private var splashHandler: Handler? = null
    private var repositorySettings: RepositorySettings?
    private var mRepositorySession : RepositorySession?     = null

    @Inject
    constructor(repositorySetting: RepositorySettings, mRepositorySession: RepositorySession) {
        this.repositorySettings     = repositorySetting
        this.mRepositorySession = mRepositorySession
    }


    fun getRole() {
        checkNotNull(mRepositorySession?.userSession){
            viewLayer?.notLogin()
            return
        }
        viewLayer?.showRole(mRepositorySession?.userSession)
    }



}
