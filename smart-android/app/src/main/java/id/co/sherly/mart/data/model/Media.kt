package id.co.sherly.mart.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Media(
    @SerializedName("id"          ) var uid         : String? = null,
    @SerializedName("name"         ) var name        : String? = null,
    @SerializedName("file"         ) var file        : String? = null,
    @SerializedName("path"         ) var path        : String? = null,
    @SerializedName("datetime"     ) var datetime    : String? = null,
    @SerializedName("file_baseurl" ) var fileBaseurl : String? = null,
    @SerializedName("url"          ) var url         : String? = null,

    var selected: Boolean = false
): Parcelable {
}