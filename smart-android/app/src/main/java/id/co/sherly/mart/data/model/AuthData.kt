/*
 * Copyright 2024 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.data.model

import com.google.gson.annotations.SerializedName


data class AuthData (

    @SerializedName("user"   ) var user   : User?  = User(),
    @SerializedName("status" ) var status : Int?   = null,
    @SerializedName("token"  ) var token  : Token? = Token()

)