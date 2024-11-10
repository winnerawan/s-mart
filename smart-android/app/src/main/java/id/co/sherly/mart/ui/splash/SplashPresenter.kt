/*
 * Copyright 2023 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.ui.splash

import id.co.sherly.mart.data.pref.UserPreferences
import id.co.sherly.mart.ui.base.presenter.BasePresenterImpl
import javax.inject.Inject

class SplashPresenter @Inject constructor(
    private val view: SplashContract.View,
    private val preferences: UserPreferences
) : SplashContract.Presenter, BasePresenterImpl() {

   var isLoggedIn = preferences.isLoggedIn
}