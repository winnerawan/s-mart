package id.co.sherly.mart.data.db.entity

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
@Entity(tableName = "purchase")
data class PurchaseEntity (

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id"          ) var id          : Int?    = null,
    @SerializedName("supplier"    ) var supplier    : Int?             = null,
    @SerializedName("description" ) var description : String?          = null,
    @SerializedName("total"       ) var total       : String?             = null,

): Parcelable