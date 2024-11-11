/*
 * Copyright 2024 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.data.model.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import id.co.sherly.mart.data.model.AuthData
import id.co.sherly.mart.data.model.Token
import id.co.sherly.mart.data.model.User

@Keep
data class SignInResponse (

    @SerializedName("user"  ) var user  : User?  = null,
    @SerializedName("token" ) var token : Token? = null
)