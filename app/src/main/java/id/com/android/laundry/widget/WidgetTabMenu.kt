package id.com.android.laundry.widget

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.tabs.TabLayout


class WidgetTabMenu : TabLayout {

    interface MenuListener {
        fun onTabMenuReselected(position: Int)
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setMenuListener(menuListener: MenuListener) {
        val tabSelectedListener = object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) { }
            override fun onTabReselected(tab: TabLayout.Tab) { menuListener.onTabMenuReselected(tab.position) }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
        }
        addOnTabSelectedListener(tabSelectedListener)
    }

}
