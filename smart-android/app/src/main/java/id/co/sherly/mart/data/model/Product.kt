/*
 * Copyright 2024 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Product (

    @SerializedName("id"                   ) var id                  : Int?    = null,
    @SerializedName("productstatus_id"     ) var productstatusId     : Int?    = null,
    @SerializedName("productstatus_name"   ) var productstatusName   : String? = null,
    @SerializedName("productcategory_id"   ) var productcategoryId   : Int?    = null,
    @SerializedName("productcategory_name" ) var productcategoryName : String? = null,
    @SerializedName("code"                 ) var code                : String? = null,
    @SerializedName("name"                 ) var name                : String? = null,
    @SerializedName("description"          ) var description         : String? = null,
    @SerializedName("packet_id"            ) var packetId            : Int?    = null,
    @SerializedName("packet_name"          ) var packetName          : String? = null,
    @SerializedName("subpay_id"            ) var subpayId            : Int?    = null,
    @SerializedName("subpay_name"          ) var subpayName          : String? = null,
    @SerializedName("subpay_hasvalue"      ) var subpayHasvalue      : Int?    = null,
    @SerializedName("subpayvalue"          ) var subpayvalue         : Int?    = null,
    @SerializedName("period_id"            ) var periodId            : Int?    = null,
    @SerializedName("period_name"          ) var periodName          : String? = null,
    @SerializedName("period_hasvalue"      ) var periodHasvalue      : Int?    = null,
    @SerializedName("periodvalue"          ) var periodvalue         : Int?    = null,
    @SerializedName("price"                ) var price               : String? = null,
    @SerializedName("disc_id"              ) var discId              : Int?    = null,
    @SerializedName("disc_name"            ) var discName            : String? = null,
    @SerializedName("disc_value"           ) var discValue           : String? = null,
    @SerializedName("tax_id"               ) var taxId               : Int?    = null,
    @SerializedName("tax_name"             ) var taxName             : String? = null,
    @SerializedName("tax_value"            ) var taxValue            : String? = null,
    @SerializedName("netspeed_id"          ) var netspeedId          : Int?    = null,
    @SerializedName("netspeed_name"        ) var netspeedName        : String? = null,
    @SerializedName("netspeed_value"       ) var netspeedValue       : Int?    = null,
    @SerializedName("netshare_id"          ) var netshareId          : Int?    = null,
    @SerializedName("netshare_name"        ) var netshareName        : String? = null,
    @SerializedName("netshare_value"       ) var netshareValue       : Int?    = null

)