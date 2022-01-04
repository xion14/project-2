package id.com.android.laundry.adapter

import android.content.Context
import id.com.android.laundry.feature.userlayer.fragment.FragmentBase
import java.util.ArrayList


class AdapterPaginationTransaction(var context: Context?, manager: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentPagerAdapter(manager) {

    private val collectionFragmentList: ArrayList<FragmentBase> = ArrayList()
    private val collectionTitleList: ArrayList<String> = ArrayList()

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        return collectionFragmentList[position]
    }

    override fun getCount(): Int {
        return collectionFragmentList.size
    }

    fun addFragment(fragment: FragmentBase, title: String) {
        collectionFragmentList.add(fragment)
        collectionTitleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return collectionTitleList[position] ?: ""
    }
}
