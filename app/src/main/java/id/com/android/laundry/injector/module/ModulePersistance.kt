package id.com.android.laundry.injector.module

import android.app.Application
import dagger.Module
import dagger.Provides
import id.com.android.laundry.controller.ControllerPreference
import id.com.android.laundry.injector.scope.PerApplication
import id.com.android.laundry.repository.RepositoryContent
import id.com.android.laundry.repository.RepositorySession
import id.com.android.laundry.repository.RepositorySettings

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

    @Provides
    @PerApplication
    internal fun providePreferenceSession(controllerPreference: ControllerPreference): RepositorySession {
        return RepositorySession(controllerPreference)
    }


}
