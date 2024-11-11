/*
 * Copyright 2024 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.network.service

import id.co.sherly.mart.data.model.Customer
import id.co.sherly.mart.data.model.response.SignInResponse
import id.co.sherly.mart.data.model.response.SuccessResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CustomerService {


    @GET("customer/data")
    fun data(
        @Query("with") with: String? = null
    ): Call<List<Customer>?>

    @FormUrlEncoded
    @POST("customer/create")
    fun create(
        @Field("name") name: String
    ): Call<SuccessResponse?>

    @FormUrlEncoded
    @POST("customer/update")
    fun update(
        @Field("customer") customerId: Int,
        @Field("name") name: String
    ): Call<SuccessResponse?>

    @FormUrlEncoded
    @POST("customer/delete")
    fun delete(
        @Field("customer") customerId: Int
    ): Call<SuccessResponse?>
}