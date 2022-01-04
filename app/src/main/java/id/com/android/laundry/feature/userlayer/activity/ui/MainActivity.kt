package id.com.android.laundry.feature.userlayer.activity.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import id.com.android.laundry.R
import id.com.android.laundry.feature.ActivityBase
import id.com.android.laundry.feature.presenterlayer.PresenterMain
import id.com.android.laundry.feature.userlayer.fragment.FragmentHome
import id.com.android.laundry.feature.userlayer.fragment.FragmentProfile
import id.com.android.laundry.feature.viewlayer.ViewMain
import id.com.android.laundry.model.ModelLogin
import id.com.android.laundry.tools.TypeScreen
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_content.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*
import javax.inject.Inject

class MainActivity : ActivityBase(), NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemReselectedListener,
        ViewMain {

    private var pageScreenPool = WeakHashMap<Long, Fragment>()
    var currentScreen: Long = TypeScreen.HOME
    @Inject
    lateinit var presenterMain : PresenterMain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        componentActivity?.inject(this)
        presenterMain.attachView(this)
        initializeToolbar()
        initializeFragment()
        initializeBottomNavigation()

    }

    private fun initializeFragment() {
        pageScreenPool.put(TypeScreen.HOME, FragmentHome())
        pageScreenPool.put(TypeScreen.PROFILE, FragmentProfile())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    private fun initializeToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbar.title = ""
        val toggle = ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        )
        drawerLayout!!.addDrawerListener(toggle)
        toggle.syncState()
        navigationView!!.setNavigationItemSelectedListener(this)
    }

    @SuppressLint("CommitTransaction")
    private fun initializeBottomNavigation() {
        view_home_bottomnavigationview?.disableShiftMode()
        view_home_bottomnavigationview?.setOnNavigationItemSelectedListener(this)
        view_home_bottomnavigationview?.setOnNavigationItemReselectedListener(this)
        view_home_container_framelayout?.let { supportFragmentManager.beginTransaction().replace(it.id, pageScreenPool[currentScreen]!!).commitAllowingStateLoss() }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                currentScreen = TypeScreen.HOME

            }
            R.id.nav_profile -> {
                currentScreen = TypeScreen.PROFILE
            }

            R.id.nav_logout ->{
                presenterMain.logout()
                val intent          = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()

            }

        }
        drawerLayout!!.closeDrawer(GravityCompat.START)
        view_home_container_framelayout?.let { supportFragmentManager.beginTransaction().replace(it.id, pageScreenPool[currentScreen]!!).commitAllowingStateLoss() }

        return true
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        when (item.itemId) {
            R.id.nav_home -> {
                currentScreen = TypeScreen.HOME

            }
            R.id.nav_profile -> {
                currentScreen = TypeScreen.PROFILE
            }

        }
        drawerLayout!!.closeDrawer(GravityCompat.START)
        view_home_container_framelayout?.let { supportFragmentManager.beginTransaction().replace(it.id, pageScreenPool[currentScreen]!!).commitAllowingStateLoss() }

    }

    override fun showRole(level: ModelLogin?) {

    }

    override fun notLogin() {

    }


}