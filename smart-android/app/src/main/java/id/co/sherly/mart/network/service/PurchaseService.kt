/*
 * Copyright 2024 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.network.service

import id.co.sherly.mart.data.model.Purchase
import id.co.sherly.mart.data.model.request.PurchaseRequest
import id.co.sherly.mart.data.model.response.SignInResponse
import id.co.sherly.mart.data.model.response.SuccessResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PurchaseService {


    @GET("purchase/data")
    fun data(
        @Query("with") with: String? = null
    ): Call<List<Purchase>?>

    @GET("purchase/data")
    fun data(
        @Query("purchase") purchase: String? = null,
        @Query("with") with: String? = null
    ): Call<List<Purchase>?>

    @FormUrlEncoded
    @POST("purchase/create")
    fun create(
        @Body purchaseRequest: PurchaseRequest
    ): Call<SuccessResponse?>


//    @FormUrlEncoded
//    @POST("purchase/update")
//    fun update(
//        @Field("purchase") purchaseId: Int,
//        @Field("name") name: String
//    ): Call<SuccessResponse?>

    @FormUrlEncoded
    @POST("purchase/delete")
    fun delete(
        @Field("purchase") purchaseId: String
    ): Call<SuccessResponse?>
}