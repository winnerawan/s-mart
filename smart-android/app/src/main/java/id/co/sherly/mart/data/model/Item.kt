package id.co.sherly.mart.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Item(
    @SerializedName("id"                  ) var id                : String? = null,
    @SerializedName("category_id"         ) var categoryId        : Int?    = null,
    @SerializedName("sku"                 ) var sku               : String? = null,
    @SerializedName("name"                ) var name              : String? = null,
    @SerializedName("description"         ) var description       : String? = null,
    @SerializedName("last_purchase_price" ) var lastPurchasePrice : String? = null,
    @SerializedName("image"               ) var image             : String? = null,
    @SerializedName("created_at"          ) var createdAt         : String? = null,
    @SerializedName("media_uid"           ) var mediaUid          : String? = null,

    @SerializedName("temp_qty"           ) var tempQty          : Int? = 0,

    var quantity: Int = 0,
    var selected: Boolean = false,
    var recyclerViewPosition: Int = 0
) : Parcelable {
}