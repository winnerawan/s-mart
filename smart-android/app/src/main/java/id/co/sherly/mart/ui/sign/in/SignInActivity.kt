/*
 * Copyright 2023 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.ui.sign.`in`

import android.content.Intent
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import id.co.sherly.mart.databinding.ActivitySignInBinding
import id.co.sherly.mart.ui.base.view.BaseActivity
import id.co.sherly.mart.ui.main.MainActivity
import id.co.sherly.mart.ui.sign.up.SignUpActivity
import javax.inject.Inject

@AndroidEntryPoint
class SignInActivity : BaseActivity<ActivitySignInBinding>(), SignInContract.View {

    override fun createBinding(): ActivitySignInBinding = ActivitySignInBinding.inflate(layoutInflater)

    @Inject
    lateinit var presenter: SignInPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {

            btnSignIn.setOnClickListener {
                val username = textUsername.text.toString()
                val password = textPasswword.text.toString()
                presenter.signIn(username, password)
            }


        }
    }

    override fun launchSignUpActivity() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    override fun launchMainActivity() {
        startActivity(Intent(this@SignInActivity, MainActivity::class.java))
        finishAffinity()
    }
}