/*
 * Copyright 2023 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import id.co.sherly.mart.R
import id.co.sherly.mart.databinding.ActivitySplashBinding
import id.co.sherly.mart.ui.base.view.BaseActivity
import id.co.sherly.mart.ui.main.MainActivity
import id.co.sherly.mart.ui.onboarding.OnBoardingActivity
import javax.inject.Inject


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(), SplashContract.View {

    override fun createBinding(): ActivitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)

    @Inject
    lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runSplashAnimation()
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            decideActivityToLaunch()
        }, 2700)


    }

    /**  check state login, launch onboarding activity if not logged in
     */
    private fun decideActivityToLaunch() {
        if (presenter.isLoggedIn) {
            launchMainActivity()
            return
        }
        launchOnBoardingActivity()
    }

    /** launch onboarding activity
     */
    private fun launchOnBoardingActivity() {
        startActivity(Intent(this@SplashActivity, OnBoardingActivity::class.java))
        finish()
    }

    /** launch main activity
     */
    private fun launchMainActivity() {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
    }

    /** fade out logo and fade in slogan */
    override fun runSplashAnimation() {
        val fadeIn: Animation = AlphaAnimation(0f, 1f)
        fadeIn.duration = 1000

        val fadeOut: Animation = AlphaAnimation(1f, 0f)
        fadeOut.duration = 1500

        fadeOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                binding.logo.setImageDrawable(ContextCompat.getDrawable(this@SplashActivity, R.drawable.slogan))
                binding.logo.startAnimation(fadeIn)
            }
        })
        binding.logo.startAnimation(fadeOut)
    }
}