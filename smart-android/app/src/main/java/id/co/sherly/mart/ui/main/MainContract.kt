/*
 * Copyright 2023 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.ui.main

import id.co.sherly.mart.ui.base.presenter.BasePresenter

interface MainContract {

    interface View {
        fun showMessage(message: String?)
    }

    interface Presenter : BasePresenter {

    }
}