package id.com.android.laundry.injector.component

import android.app.Application
import dagger.Component
import id.com.android.laundry.controller.ControllerEndpoint
import id.com.android.laundry.controller.ControllerPreference
import id.com.android.laundry.injector.module.ModuleApplication
import id.com.android.laundry.injector.module.ModulePersistance
import id.com.android.laundry.injector.scope.PerApplication
import id.com.android.laundry.repository.RepositoryContent
import id.com.android.laundry.repository.RepositorySession
//import id.com.android.moviedb.repository.RepositoryContent
import id.com.android.laundry.repository.RepositorySettings

@PerApplication
@Component(modules = [(ModuleApplication::class), (ModulePersistance::class)])
interface ComponentApplication {
    fun inject(application: Application)
    fun serviceApi()                    : ControllerEndpoint
    fun servicePresistance()            : ControllerPreference
    fun repositorySettings()            : RepositorySettings
    fun repositoryContent()             : RepositoryContent
    fun repositorySession()             : RepositorySession
}