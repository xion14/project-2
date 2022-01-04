package id.com.android.laundry.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent


class WidgetPaging : androidx.viewpager.widget.ViewPager {

    interface PagingListener {
        fun onTabSwiped(position: Int)
        fun onTabCountChange()
    }

    var scrollEnable: Boolean = true

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun setPagingListener(pagingListener: PagingListener) {
        val adapterChangeListener = OnAdapterChangeListener { _, _, _ -> pagingListener.onTabCountChange() }
        val pageChangeListener = object : androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                pagingListener.onTabSwiped(position)
            }
        }
        addOnAdapterChangeListener(adapterChangeListener)
        addOnPageChangeListener(pageChangeListener)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (this.scrollEnable) {
            return super.onTouchEvent(ev)
        }
        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (this.scrollEnable) {
            return super.onInterceptTouchEvent(ev)
        }
        return false
    }
}
