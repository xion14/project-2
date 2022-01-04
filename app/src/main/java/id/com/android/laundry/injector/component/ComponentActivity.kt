package id.com.android.laundry.injector.component

import dagger.Component
import id.com.android.laundry.feature.ActivityBase
import id.com.android.laundry.feature.userlayer.activity.EditHargaActivity
import id.com.android.laundry.feature.userlayer.activity.ui.*
import id.com.android.laundry.feature.userlayer.fragment.FragmentHome
import id.com.android.laundry.feature.userlayer.fragment.FragmentProfile
import id.com.android.laundry.feature.userlayer.fragment.FragmentTransaction
import id.com.android.laundry.injector.module.ModuleActivity
import id.com.android.laundry.injector.scope.PerActivity

@PerActivity
@Component(dependencies = [(ComponentApplication::class)], modules = [(ModuleActivity::class)])
interface ComponentActivity {
    fun inject (activityBase                : ActivityBase)
    fun inject (activitySplashActivity      : SplashActivity)
    fun inject (activityLogin               : LoginActivity)
    fun inject (activityRegister            : RegisterActivity)
    fun inject (activityRegisterLaundry            : RegisterLaundryActivity)
    fun inject (mainActivity                : MainActivity)
    fun inject (editHargaActivity           : EditHargaActivity)
    fun inject (listLaundryActivity         : ListLaundryActivity)
    fun inject (pesanLaundryActivity        : PesanLaundryActivity)
    fun inject (detailActivity: LaundryDetailActivity)
    fun inject (listPesananLaundryActivity: ListPesananLaundryActivity)
    fun inject (pesananActivity: DetailPesananActivity)
    fun inject (pesananUserActivity: PesananUserActivity)
    fun inject (transactionActivity: TransactionActivity)
    fun inject (listUserActivity: ListUserActivity)
    fun inject (adminActivity: EditAdminActivity)
    fun inject (editUserActivity: EditUserActivity)
    fun inject (fragmentHome                : FragmentHome)
    fun inject (fragmentProfile             : FragmentProfile)
    fun inject (fragmentTransaction: FragmentTransaction)
}
