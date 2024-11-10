/*
 * Copyright 2023 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.daimajia.swipe.SwipeLayout
import dagger.hilt.android.AndroidEntryPoint
import id.co.sherly.mart.R
import id.co.sherly.mart.databinding.ActivityOnboardingBinding
import id.co.sherly.mart.ui.sign.`in`.SignInActivity
import id.co.sherly.mart.ui.sign.up.SignUpActivity

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity(), OnBoardingContract.View {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //runFadeLogoAnimation()
        binding.apply {

            btnSignIn.setOnClickListener { launchSignInActivity() }
            btnSignUp.setOnClickListener { launchSignUpActivity() }

            binding.swipeLayout.addSwipeListener(object : SwipeLayout.SwipeListener {
                override fun onStartOpen(layout: SwipeLayout?) {

                }

                override fun onOpen(layout: SwipeLayout?) {
                    launchExplorePacketActivity()
                }

                override fun onStartClose(layout: SwipeLayout?) {
                }

                override fun onClose(layout: SwipeLayout?) {
                    layout?.close(true)
                }

                override fun onUpdate(layout: SwipeLayout?, leftOffset: Int, topOffset: Int) {
                }

                override fun onHandRelease(layout: SwipeLayout?, xvel: Float, yvel: Float) {
                }

            })
        }
    }

    override fun onResume() {
        binding.swipeLayout.close()
        super.onResume()
    }

    /**
     */
    override fun launchSignInActivity() {
        startActivity(Intent(this@OnBoardingActivity, SignInActivity::class.java))
    }

    /**
     */
    override fun launchSignUpActivity() {
        startActivity(Intent(this@OnBoardingActivity, SignUpActivity::class.java))
    }

    /**
     * launch explore packet activity
     */
    override fun launchExplorePacketActivity() {
    }

    override fun runFadeLogoAnimation() {
        val fadeIn: Animation = AlphaAnimation(0f, 1f)
        fadeIn.duration = 1000

        val fadeOut: Animation = AlphaAnimation(1f, 0f)
        fadeOut.duration = 1500

        fadeOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                binding.logo.setImageDrawable(ContextCompat.getDrawable(this@OnBoardingActivity, R.drawable.slogan))
                binding.logo.startAnimation(fadeIn)
            }
        })
        binding.logo.startAnimation(fadeOut)
//        fadeIn.setAnimationListener(object : Animation.AnimationListener {
//            override fun onAnimationStart(animation: Animation) {}
//            override fun onAnimationRepeat(animation: Animation) {}
//            override fun onAnimationEnd(animation: Animation) {
//                binding.logo.setImageDrawable(ContextCompat.getDrawable(this@OnBoardingActivity, R.drawable.logo))
//                binding.logo.startAnimation(fadeIn)
//            }
//        })
//        binding.logo.startAnimation(fadeIn)
    }
}