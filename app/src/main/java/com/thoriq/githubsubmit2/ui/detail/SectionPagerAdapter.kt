package com.thoriq.githubsubmit2.ui.detail

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username: String = ""
    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment.newInstance(position + 1, username)
        return fragment
    }
    override fun getItemCount(): Int {
        return 2
    }
}
