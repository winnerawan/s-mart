package id.co.sherly.mart.data.db.entity

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
@Entity(tableName = "purchase_items")
data class PurchaseItemEntity (

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id"          ) var id          : Int?    = null,
    @SerializedName("item"           ) var item          : String? = null,
    @SerializedName("purchase_price" ) var purchasePrice : String?    = null,
    @SerializedName("selling_price"  ) var sellingPrice  : String?    = null,
    @SerializedName("qty"            ) var qty           : Int?    = null,
    @SerializedName("category"       ) var category      : Int?    = null,
    @SerializedName("sku"            ) var sku           : String? = null

): Parcelable