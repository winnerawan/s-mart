/*
 * Copyright 2023 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.data.model

import android.accounts.Account
import android.accounts.AccountManager
import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class Token(
    @SerializedName("access_type"   ) var accessType   : String? = null,
    @SerializedName("access_token"  ) var accessToken  : String? = null,
    @SerializedName("refresh_type"  ) var refreshType  : String? = null,
    @SerializedName("refresh_token" ) var refreshToken : String? = null
) : Parcelable {

    constructor(am: AccountManager, account: Account): this(null, null) {
        this.accessType = am.getUserData(account, "1.0")
        this.accessToken = am.peekAuthToken(account, "Bearer")
    }

    fun writeToAccountManager(am: AccountManager, account: Account?) {
        am.setUserData(account, "1.0", "Bearer")
        am.setUserData(account, "KEY_TYPE", accessType)
    }
}