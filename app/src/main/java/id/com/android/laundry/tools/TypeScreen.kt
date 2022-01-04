package id.com.android.laundry.tools

import androidx.annotation.LongDef
import id.com.android.laundry.R


object TypeScreen {

    const val HOME: Long = 1
    const val PROFILE: Long = 2

    @LongDef(HOME, PROFILE)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class Screen

    fun buildScreenName (@Screen type: Long) : Int {
        return when (type) {
            HOME ->     R.string.home
            else ->     R.string.profile
        }
    }
}
