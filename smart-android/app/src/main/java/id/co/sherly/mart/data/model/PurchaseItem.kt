package id.co.sherly.mart.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class PurchaseItem (

    @SerializedName("purchase_id" ) var purchaseId : String? = null,
    @SerializedName("item_id"     ) var itemId     : String? = null,
    @SerializedName("price"       ) var price      : String? = null,
    @SerializedName("qty"         ) var qty        : Int?    = null,
    @SerializedName("subtotal"    ) var subtotal   : String? = null,
    @SerializedName("created_at"  ) var createdAt  : String? = null,
    @SerializedName("updated_at"  ) var updatedAt  : String? = null

): Parcelable