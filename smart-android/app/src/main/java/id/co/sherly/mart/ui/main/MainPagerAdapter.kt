/*
 * Copyright 2023 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.co.sherly.mart.ui.account.AccountFragment
import id.co.sherly.mart.ui.category.CategoryFragment
import id.co.sherly.mart.ui.customer.CustomerFragment
import id.co.sherly.mart.ui.history.HistoryFragment
import id.co.sherly.mart.ui.home.HomeFragment
import id.co.sherly.mart.ui.supplier.SupplierFragment

class MainPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = 8

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return HomeFragment()
            1 -> return HomeFragment()
            2 -> return HomeFragment()
            3 -> return HomeFragment()
            4 -> return CategoryFragment()
            5 -> return HomeFragment()
            6 -> return SupplierFragment()
            7 -> return CustomerFragment()
        }
        return HomeFragment()
    }
}