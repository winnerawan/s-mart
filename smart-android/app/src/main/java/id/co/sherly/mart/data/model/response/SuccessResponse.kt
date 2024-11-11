package id.co.sherly.mart.data.model.response

import com.google.gson.annotations.SerializedName

data class SuccessResponse(
    @SerializedName("status" ) var status : Status? = null
) {
}