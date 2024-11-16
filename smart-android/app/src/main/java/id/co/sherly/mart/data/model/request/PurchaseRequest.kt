package id.co.sherly.mart.data.model.request

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class PurchaseRequest (

    @SerializedName("supplier"    ) var supplier    : Int?             = null,
    @SerializedName("description" ) var description : String?          = null,
    @SerializedName("total"       ) var total       : String?             = null,
    @SerializedName("items"       ) var items       : ArrayList<PurchaseItemRequest> = arrayListOf()

): Parcelable