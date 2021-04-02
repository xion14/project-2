package id.com.android.weatherfinder.injector.component

import android.app.Application
import dagger.Component
import id.com.android.weatherfinder.controller.ControllerEndpoint
import id.com.android.weatherfinder.controller.ControllerPreference
import id.com.android.weatherfinder.injector.module.ModuleApplication
import id.com.android.weatherfinder.injector.module.ModulePersistance
import id.com.android.weatherfinder.injector.scope.PerApplication
import id.com.android.weatherfinder.repository.RepositoryContent
//import id.com.android.moviedb.repository.RepositoryContent
import id.com.android.weatherfinder.repository.RepositorySettings

@PerApplication
@Component(modules = [(ModuleApplication::class), (ModulePersistance::class)])
interface ComponentApplication {
    fun inject(application: Application)
    fun serviceApi()                    : ControllerEndpoint
    fun servicePresistance()            : ControllerPreference
    fun repositorySettings()            : RepositorySettings
    fun repositoryContent()             : RepositoryContent
}