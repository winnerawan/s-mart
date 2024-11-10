/*
 * Copyright 2023 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.ui.sign.`in`

import id.co.sherly.mart.ui.base.presenter.BasePresenter
import id.co.sherly.mart.ui.base.view.BaseView

interface SignInContract {

    interface View : BaseView {
        fun launchMainActivity()
        fun launchSignUpActivity()
    }

    interface Presenter: BasePresenter {
        fun signIn(username: String, password: String)
    }
}