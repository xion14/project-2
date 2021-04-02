package id.com.android.weatherfinder.injector.component

import dagger.Component
import id.com.android.weatherfinder.feature.ActivityBase
import id.com.android.weatherfinder.feature.userlayer.activity.ui.SplashActivity
import id.com.android.weatherfinder.injector.module.ModuleActivity
import id.com.android.weatherfinder.injector.scope.PerActivity

@PerActivity
@Component(dependencies = [(ComponentApplication::class)], modules = [(ModuleActivity::class)])
interface ComponentActivity {
    fun inject (activityBase                : ActivityBase)
    fun inject (activitySplashActivity      : SplashActivity)
}
