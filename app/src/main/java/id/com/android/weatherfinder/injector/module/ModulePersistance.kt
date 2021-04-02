package id.com.android.weatherfinder.injector.module

import android.app.Application
import dagger.Module
import dagger.Provides
import id.com.android.weatherfinder.controller.ControllerPreference
import id.com.android.weatherfinder.injector.scope.PerApplication
import id.com.android.weatherfinder.repository.RepositoryContent
import id.com.android.weatherfinder.repository.RepositorySettings

@Module
class ModulePersistance {

    @Provides
    @PerApplication
    internal fun provideControllerPreference(application: Application): ControllerPreference {
        return ControllerPreference(application)
    }


    @Provides
    @PerApplication
    internal fun providePreferenceSettings(controllerPreference: ControllerPreference): RepositorySettings {
        return RepositorySettings(controllerPreference)
    }

    @Provides
    @PerApplication
    internal fun providePersistanceContent(application: Application): RepositoryContent {
        return RepositoryContent.newInstance(application)
    }



}
