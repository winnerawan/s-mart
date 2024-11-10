/*
 * Copyright 2024 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.data.model.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class BaseResponse<T>(
    @SerializedName("data" ) var data : ArrayList<T> = arrayListOf()
)