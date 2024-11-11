package id.co.sherly.mart.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Category(
    @SerializedName("id"          ) var id          : Int?    = null,
    @SerializedName("name"        ) var name        : String? = null,
    @SerializedName("description" ) var description : String? = null,
    @SerializedName("position"    ) var position    : Int?    = null,
    @SerializedName("icon"        ) var icon        : String? = null,
    @SerializedName("item"        ) var item        : Int?    = null,

    var selected: Boolean = false
) : Parcelable {
}