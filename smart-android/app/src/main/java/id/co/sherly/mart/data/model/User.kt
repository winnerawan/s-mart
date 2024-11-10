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
data class User(
    @SerializedName("id"           ) var id           : Int?    = null,
    @SerializedName("name"         ) var name         : String? = null,
    @SerializedName("firstname"    ) var firstname    : String? = null,
    @SerializedName("hasfirstname" ) var hasfirstname : Int?    = null,
    @SerializedName("lastname"     ) var lastname     : String? = null,
    @SerializedName("haslastname"  ) var haslastname  : Int?    = null
):  Parcelable {

    constructor(am: AccountManager, account: Account) : this() {
    }

    fun writeToAccountManager(am: AccountManager, account: Account) {
        am.setUserData(account, "ID", id.toString())
        am.setUserData(account, "NAME", name)
    }
}