package id.co.sherly.mart.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Purchase (

    @SerializedName("id"            ) var id           : String?          = null,
    @SerializedName("supplier_id"   ) var supplierId   : Int?             = null,
    @SerializedName("total"         ) var total        : String?          = null,
    @SerializedName("description"   ) var description  : String?          = null,
    @SerializedName("created_at"    ) var createdAt    : String?          = null,
    @SerializedName("supplier_name" ) var supplierName : String?          = null,
    @SerializedName("item"          ) var item         : Int?             = null,
    @SerializedName("pieces"        ) var pieces       : Int?             = null,
    @SerializedName("items"         ) var items        : ArrayList<PurchaseItem> = arrayListOf(),
    var selected: Boolean = false

): Parcelable