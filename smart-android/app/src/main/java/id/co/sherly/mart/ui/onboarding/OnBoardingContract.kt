/*
 * Copyright 2023 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.ui.onboarding

interface OnBoardingContract {

    interface View {
        fun launchSignInActivity()
        fun launchSignUpActivity()
        fun launchExplorePacketActivity()

        fun runFadeLogoAnimation()
    }

    interface Presenter {

    }
}