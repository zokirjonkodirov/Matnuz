package com.intentsoft.matnuz.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.intentsoft.matnuz.ui.fragments.DictionaryFragment
import com.intentsoft.matnuz.ui.fragments.EditFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return EditFragment()
            1 -> return DictionaryFragment()
        }
        return EditFragment()
    }

    companion object {
        private const val NUM_TABS = 2
    }
}