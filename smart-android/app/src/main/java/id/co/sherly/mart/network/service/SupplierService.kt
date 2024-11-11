/*
 * Copyright 2024 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.network.service

import id.co.sherly.mart.data.model.Customer
import id.co.sherly.mart.data.model.Supplier
import id.co.sherly.mart.data.model.response.SignInResponse
import id.co.sherly.mart.data.model.response.SuccessResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SupplierService {


    @GET("supplier/data")
    fun data(
        @Query("with") with: String? = null
    ): Call<List<Supplier>?>

    @FormUrlEncoded
    @POST("supplier/create")
    fun create(
        @Field("name") name: String,
        @Field("address") address: String?,
        @Field("phone") phone: String?
    ): Call<SuccessResponse?>

    @FormUrlEncoded
    @POST("supplier/update")
    fun update(
        @Field("supplier") supplierId: Int,
        @Field("name") name: String,
        @Field("address") address: String?,
        @Field("phone") phone: String?
    ): Call<SuccessResponse?>

    @FormUrlEncoded
    @POST("supplier/delete")
    fun delete(
        @Field("supplier") supplierId: Int
    ): Call<SuccessResponse?>
}