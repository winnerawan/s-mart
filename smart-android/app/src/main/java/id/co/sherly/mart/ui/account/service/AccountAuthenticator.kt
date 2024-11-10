/*
 * Copyright 2024 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.ui.account.service


import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import id.co.sherly.mart.ui.account.auth.Auth
import id.co.sherly.mart.ui.sign.`in`.SignInActivity


class AccountAuthenticator(private val context: Context) : AbstractAccountAuthenticator(
    context
) {
    override fun addAccount(
        response: AccountAuthenticatorResponse,
        accountType: String,
        authTokenType: String,
        features: Array<String>,
        options: Bundle
    ): Bundle {
        return if (TextUtils.isEmpty(accountType) || accountType != Auth.getAccountType()) {
            val bundle = Bundle()
            bundle.putInt("errorCode", 1)
            bundle.putString("errorMessage", "Invalid accountType")
            bundle
        } else if (TextUtils.isEmpty(authTokenType) || authTokenType == Auth.getAuthTokenType()) {
            val intent = Intent(
                context,
                SignInActivity::class.java
            )
            intent.putExtra("booleanResult", true)
            intent.putExtra("accountAuthenticatorResponse", response)
            val bundle2 = Bundle()
            bundle2.putParcelable("intent", intent)
            bundle2
        } else {
            val bundle3 = Bundle()
            bundle3.putInt("errorCode", 2)
            bundle3.putString("errorMessage", "Invalid authTokenType")
            bundle3
        }
    }

    override fun getAuthToken(
        response: AccountAuthenticatorResponse,
        account: Account,
        authTokenType: String,
        options: Bundle
    ): Bundle {
        val bundle = Bundle()
        bundle.putBoolean("booleanResult", false)
        return bundle
    }

    override fun getAuthTokenLabel(authTokenType: String): String? {
        return if (authTokenType == Auth.getAuthTokenType()) {
            Auth.getAuthTokenLabel()
        } else null
    }

    override fun confirmCredentials(
        response: AccountAuthenticatorResponse,
        account: Account,
        options: Bundle
    ): Bundle? {
        return null
    }

    override fun editProperties(
        response: AccountAuthenticatorResponse,
        accountType: String
    ): Bundle? {
        return null
    }

    override fun hasFeatures(
        response: AccountAuthenticatorResponse,
        account: Account,
        features: Array<String>
    ): Bundle {
        val bundle = Bundle()
        bundle.putBoolean("booleanResult", false)
        return bundle
    }

    override fun updateCredentials(
        response: AccountAuthenticatorResponse,
        account: Account,
        authTokenType: String,
        options: Bundle
    ): Bundle? {
        return null
    }
}