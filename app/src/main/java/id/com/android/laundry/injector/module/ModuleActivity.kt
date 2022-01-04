package id.com.android.laundry.injector.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import dagger.Module
import dagger.Provides
import id.com.android.laundry.injector.scope.PerActivity

@Module
class ModuleActivity(private val activity: AppCompatActivity) {

    @Provides
    @PerActivity
    internal fun provideActivity() : Context {
        return activity
    }

    @Provides
    @PerActivity
    internal fun provideFragmentManager() : FragmentManager {
        return activity.supportFragmentManager
    }

}