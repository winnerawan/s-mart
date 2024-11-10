/*
 * Copyright 2023 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.ui.sign.`in`

import id.co.sherly.mart.R
import id.co.sherly.mart.data.model.AuthData
import id.co.sherly.mart.data.model.response.SignInResponse
import id.co.sherly.mart.data.pref.UserPreferences
import id.co.sherly.mart.network.ServiceBuilder
import id.co.sherly.mart.network.service.AuthService
import id.co.sherly.mart.ui.account.auth.Auth
import id.co.sherly.mart.ui.account.auth.Identity
import id.co.sherly.mart.ui.base.presenter.BasePresenterImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SignInPresenter @Inject constructor(
    private val view: SignInContract.View, private val preferences: UserPreferences
) : SignInContract.Presenter, BasePresenterImpl() {

    override fun signIn(username: String, password: String) {
        if (username.isEmpty()) {
            view.showMessage(R.string.message_empty_username)
            return
        }
        if (password.isEmpty()) {
            view.showMessage(R.string.message_empty_password)
            return
        }
        view.showLoading()
        val service = ServiceBuilder.buildService(AuthService::class.java)
        val call = service.signIn(username, password)
        call.enqueue(object : Callback<SignInResponse?> {
            override fun onResponse(
                call: Call<SignInResponse?>, response: Response<SignInResponse?>
            ) {
                response.body()?.let {
                    it.data?.let { auth ->
                        registerIdentity(auth)
                        view.hideLoading()
                        view.launchMainActivity()
                        return
                    }
                }
                view.hideLoading()
                view.showMessage(R.string.message_auth_invalid)
            }

            override fun onFailure(call: Call<SignInResponse?>, t: Throwable) {
                view.hideLoading()
                t.message?.let { view.showMessage(it) }
            }
        })
    }

    private fun registerIdentity(auth: AuthData) {
        Auth.registerIdentity(Identity(auth.user, auth.token))
        preferences.isLoggedIn = true
    }
}