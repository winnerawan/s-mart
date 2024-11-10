/*
 * Copyright 2023 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navigationHome -> {
                    binding.viewPager.currentItem = 0
                }
                R.id.navigationHistory -> {
                    binding.viewPager.currentItem = 1
                }
                R.id.navigationTicket -> {
                    binding.viewPager.currentItem = 2
                }
                R.id.navigationAccount -> {
                    binding.viewPager.currentItem = 3
                }
            }
            return@setOnItemSelectedListener true
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