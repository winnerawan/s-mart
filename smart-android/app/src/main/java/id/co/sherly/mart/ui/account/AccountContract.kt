/*
 * Copyright 2023 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.ui.account

import id.co.sherly.mart.data.model.User

interface AccountContract {

    interface View {
        fun showUser(user: User?)
        fun launchOnboardingActivity()
    }

    interface Presenter {
        fun authCheck()
        fun signOut()
    }
}