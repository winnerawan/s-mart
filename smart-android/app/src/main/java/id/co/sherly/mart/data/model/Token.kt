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
    @SerializedName("type"  ) var type  : String? = null,
    @SerializedName("token" ) var token : String? = null

) : Parcelable {

    constructor(am: AccountManager, account: Account): this(null, null) {
        this.type = am.getUserData(account, "1.0")
        this.token = am.peekAuthToken(account, "Bearer")
    }

    fun writeToAccountManager(am: AccountManager, account: Account?) {
        am.setUserData(account, "1.0", "Bearer")
        am.setUserData(account, "KEY_TYPE", type)
    }
}