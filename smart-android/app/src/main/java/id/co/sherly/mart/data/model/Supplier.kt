package id.co.sherly.mart.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Supplier(
    @SerializedName("id"          ) var id          : Int?    = null,
    @SerializedName("name"        ) var name        : String? = null,
    @SerializedName("address" ) var address : String? = null,
    @SerializedName("phone"        ) var phone        : String?    = null,

    var selected: Boolean = false
) : Parcelable {
}