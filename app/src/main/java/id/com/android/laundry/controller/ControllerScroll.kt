package id.com.android.laundry.controller

import androidx.recyclerview.widget.RecyclerView

class ControllerScroll : androidx.recyclerview.widget.RecyclerView.OnScrollListener {
    private var previousTotal       = 0
    private var loading             = true
    private val visibleThreshold    = 2
    private var currentPage         = 1

    private var linearLayoutManager:   androidx.recyclerview.widget.LinearLayoutManager? = null
    private var gridLayoutManager:     androidx.recyclerview.widget.GridLayoutManager? = null
    private var pageListener:          PageListener? = null
    private var firstVisibleItem:       Int = 0
    private var visibleItemCount:       Int = 0
    private var totalItemCount:         Int = 0

    constructor(linearLayoutManager: androidx.recyclerview.widget.LinearLayoutManager, pageListener: PageListener) {
        this.linearLayoutManager = linearLayoutManager
        this.pageListener = pageListener
    }

    constructor(gridLayoutManager: androidx.recyclerview.widget.GridLayoutManager, pageListener: PageListener) {
        this.gridLayoutManager = gridLayoutManager
        this.pageListener = pageListener
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        visibleItemCount    = recyclerView!!.childCount
        totalItemCount      = if (linearLayoutManager != null) linearLayoutManager!!.itemCount else gridLayoutManager!!.itemCount
        firstVisibleItem    = if (linearLayoutManager != null) linearLayoutManager!!.findFirstVisibleItemPosition() else gridLayoutManager!!.findFirstVisibleItemPosition()

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading         = false
                previousTotal   = totalItemCount
            }
        }
        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            currentPage++
            pageListener!!.onLoadMore(currentPage)
            loading = true
        }
    }

    fun resetLoadmore() {
        loading = true
        previousTotal = 0
        currentPage= 1
    }

    interface PageListener {
        fun onLoadMore(page: Int)
    }

}