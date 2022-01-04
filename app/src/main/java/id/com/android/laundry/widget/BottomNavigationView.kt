package id.com.android.laundry.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.BaselineLayout
import id.com.android.laundry.R

class BottomNavigationView : BottomNavigationView {

    constructor(context: Context?) : super(context!!) { initializeWidgetProperty() }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) { initializeWidgetProperty() }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr : Int) : super(context!!, attrs, defStyleAttr) { initializeWidgetProperty() }

    private var typeface: Typeface? = null

    private fun initializeWidgetProperty() {
        typeface = ResourcesCompat.getFont(context, R.font.gotham_book)
    }

    @SuppressLint("RestrictedApi")
    fun disableShiftMode() {
        val menuView = getChildAt(0) as BottomNavigationMenuView
        try {
            val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
            shiftingMode.isAccessible = true
            shiftingMode.setBoolean(menuView, false)
            shiftingMode.isAccessible = false
            for (i in 0 until menuView.childCount) {
                val item = menuView.getChildAt(i) as BottomNavigationItemView
                item.setShifting(false)
                item.setChecked(item.itemData.isChecked)
            }
        }
        catch (e: NoSuchFieldException) { }
        catch (e: IllegalAccessException) { }
    }

    @SuppressLint("RestrictedApi")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        val bottomMenu = getChildAt(0) as ViewGroup
        val bottomMenuChildCount = bottomMenu.childCount
        var item: BottomNavigationItemView
        var itemTitle: View

        for (i in 0 until bottomMenuChildCount) {
            item = bottomMenu.getChildAt(i) as BottomNavigationItemView
            item.setChecked(true)
            itemTitle = item.getChildAt(1)
            ((itemTitle as BaselineLayout).getChildAt(0) as TextView).typeface = typeface
            ((itemTitle as BaselineLayout).getChildAt(1) as TextView).typeface = typeface
        }
    }

}