/*
 * Copyright 2024 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.ui.base.view

import androidx.annotation.StringRes

interface BaseView {

    fun showLoading()
    fun hideLoading()
    fun showMessage(message: String?)
    fun showMessage(@StringRes resId: Int)
}