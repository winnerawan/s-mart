/*
 * Copyright 2024 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.ui.account.service

import android.app.Service
import android.content.Intent
import android.os.IBinder


class AccountAuthenticatorService : Service() {
    private var accountAuthenticator: AccountAuthenticator? = null
    override fun onCreate() {
        super.onCreate()
        accountAuthenticator = AccountAuthenticator(this)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        return accountAuthenticator!!.iBinder
    }
}