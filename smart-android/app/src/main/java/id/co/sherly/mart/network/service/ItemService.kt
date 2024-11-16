/*
 * Copyright 2024 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.network.service

import id.co.sherly.mart.data.model.Category
import id.co.sherly.mart.data.model.Item
import id.co.sherly.mart.data.model.response.SignInResponse
import id.co.sherly.mart.data.model.response.SuccessResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ItemService {


    @GET("item/data")
    fun data(
        @Query("category") category: Int? = null,
        @Query("query") query: String? = null
    ): Call<List<Item>?>

    @FormUrlEncoded
    @POST("item/create")
    fun create(
        @Field("category") category: Int,
        @Field("name") name: String,
        @Field("sku") sku: String,
        @Field("description") description: String?,
        @Field("media") media: String?,
    ): Call<SuccessResponse?>

    @FormUrlEncoded
    @POST("item/update")
    fun update(
        @Field("item") itemId: String,
        @Field("category") category: Int,
        @Field("name") name: String,
        @Field("sku") sku: String,
        @Field("description") description: String?,
        @Field("media") media: String?,
    ): Call<SuccessResponse?>

    @FormUrlEncoded
    @POST("item/updateTempQty")
    fun updateTempQty(
        @Field("item") itemId: String,
        @Field("temp_qty") tempQty: Int
    ): Call<SuccessResponse?>

    @FormUrlEncoded
    @POST("item/delete")
    fun delete(
        @Field("item") itemId: String
    ): Call<SuccessResponse?>
}