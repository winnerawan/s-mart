/*
 * Copyright 2023 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.ui.home

import id.co.sherly.mart.data.pref.UserPreferences
import id.co.sherly.mart.ui.account.auth.Auth
import id.co.sherly.mart.ui.base.presenter.BasePresenterImpl
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val view: HomeContract.View,
    private val preferences: UserPreferences
) : HomeContract.Presenter, BasePresenterImpl() {


    private fun forceSignOut() {
        Auth.getIdentity()?.let {
            Auth.unregisterIdentity(it)
        }
        preferences.isLoggedIn = false
    }



}