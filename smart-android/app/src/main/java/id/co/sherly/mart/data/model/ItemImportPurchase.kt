package id.co.sherly.mart.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import id.co.sherly.mart.utils.ext.calculateSubtotal
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Keep
@Parcelize
data class ItemImportPurchase(
    @SerializedName("id"                  ) var id                : String? = null,
//    @SerializedName("category"         ) var category        : String?    = null,
    @SerializedName("sku"                 ) var sku               : String? = null,
    @SerializedName("name"                ) var name              : String? = null,
    @SerializedName("quantity"           ) var quantity          : Int? = 0,
    @SerializedName("purchase_price"           ) var purchasePrice          : Int? = 0,
    @SerializedName("selling_price"           ) var sellingPrice          : Int? = 0,


) : Parcelable {

}