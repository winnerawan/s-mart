/*
 * Copyright 2023 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.roughike.bottombar.OnTabSelectListener
import dagger.hilt.android.AndroidEntryPoint
import id.co.sherly.mart.R
import id.co.sherly.mart.databinding.ActivityMainBinding
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var presenter: MainPresenter

    @Inject
    lateinit var mPagerAdapter: MainPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            setupViewPager()
            setUpBottomNavigation()
        }
    }

    private fun setUpBottomNavigation() {
        binding.bottomNavigation.setOnTabSelectListener {
            when(it) {
                R.id.tab_home -> {
                    binding.viewPager.currentItem = 0
                }
                R.id.tab_sale -> {
                    binding.viewPager.currentItem = 1
                }
                R.id.tab_stock -> {
                    binding.viewPager.currentItem = 2
                }
                R.id.tab_purchase -> {
                    binding.viewPager.currentItem = 3
                }
                R.id.tab_category -> {
                    binding.viewPager.currentItem = 4
                }
                R.id.tab_item -> {
                    binding.viewPager.currentItem = 5
                }
                R.id.tab_supplier -> {
                    binding.viewPager.currentItem = 6
                }
                R.id.tab_customer -> {
                    binding.viewPager.currentItem = 7
                }
            }
        }
    }


    private fun setupViewPager() {
        binding.viewPager.adapter = mPagerAdapter
        binding.viewPager.isUserInputEnabled = false
    }

    override fun showMessage(message: String?) {
        //TODO("Not yet implemented")
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }
}