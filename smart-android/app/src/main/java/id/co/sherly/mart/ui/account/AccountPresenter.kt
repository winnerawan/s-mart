/*
 * Copyright 2023 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.ui.account

import id.co.sherly.mart.data.model.response.SignInResponse
import id.co.sherly.mart.data.pref.UserPreferences
import id.co.sherly.mart.network.ProtectedServiceBuilder
import id.co.sherly.mart.network.service.AuthService
import id.co.sherly.mart.ui.account.auth.Auth
import id.co.sherly.mart.ui.base.presenter.BasePresenterImpl
import id.co.sherly.mart.utils.ext.logError
import id.co.sherly.mart.utils.ext.logMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class AccountPresenter @Inject constructor(
    private val view: AccountContract.View,
    private val preferences: UserPreferences
) : AccountContract.Presenter, BasePresenterImpl() {

    override fun authCheck() {
        val service = ProtectedServiceBuilder.buildService(AuthService::class.java)
        val call = service.auth()
        call.enqueue(object : Callback<SignInResponse> {
            override fun onResponse(
                call: Call<SignInResponse>,
                response: Response<SignInResponse>
            ) {
                if (response.code()>400) {
                    forceSignOut()
                    return
                }
                response.body()?.let {
                    view.showUser(it.data?.user)
                }
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                forceSignOut()
                logError(t)
            }

        })
    }

    override fun signOut() {
        val service = ProtectedServiceBuilder.buildService(AuthService::class.java)
        val call = service.signOut()
        call.enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                if (response.code()>400) {
                    forceSignOut()
                    return
                }
                response.body()?.let {
                    logMessage(it)
                    preferences.isLoggedIn = false
                    view.launchOnboardingActivity()
                }
//                if (response.headers.)
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                forceSignOut()
                logError(t)
            }

        })
    }

    private fun forceSignOut() {
        Auth.getIdentity()?.let {
            Auth.unregisterIdentity(it)
        }
        preferences.isLoggedIn = false
        view.launchOnboardingActivity()
    }
}