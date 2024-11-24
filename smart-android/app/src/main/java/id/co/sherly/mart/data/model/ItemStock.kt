package id.co.sherly.mart.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import id.co.sherly.mart.utils.ext.calculateSubtotal
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Keep
@Parcelize
data class ItemStock(

    @SerializedName("item_id") var itemId: String? = null,
    @SerializedName("sku") var sku: String? = null,
    @SerializedName("category_id") var categoryId: Int? = null,
    @SerializedName("stock") var stock: Int? = null,
    @SerializedName("purchase_price") var purchasePrice: String? = null,
    @SerializedName("selling_price") var sellingPrice: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("temp_qty") var tempQty: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("media_id") var mediaId: String? = null,
    @SerializedName("category_name") var categoryName: String? = null,
    @SerializedName("image") var image: String? = null,

    var selected: Boolean = false,
    var quantity: Int = 0,
    var recyclerViewPosition: Int = 0
) : Parcelable {

    fun getSubtotal(): BigDecimal {
        val t = sellingPrice!!.toBigDecimal().calculateSubtotal(itemQuantity = quantity)
        return t
    }
}