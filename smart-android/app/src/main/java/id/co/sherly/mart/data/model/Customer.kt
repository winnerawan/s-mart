package id.co.sherly.mart.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Customer(
    @SerializedName("id"          ) var id          : Int?    = null,
    @SerializedName("name"        ) var name        : String? = null,
    @SerializedName("updated_at" ) var updatedAt : String? = null,
    @SerializedName("default"        ) var default        : Int?    = null,

    var selected: Boolean = false
) : Parcelable {
}