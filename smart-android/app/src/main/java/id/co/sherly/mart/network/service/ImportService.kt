/*
 * Copyright 2024 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.network.service

import id.co.sherly.mart.data.model.DocumentImport
import id.co.sherly.mart.data.model.Item
import id.co.sherly.mart.data.model.Purchase
import id.co.sherly.mart.data.model.request.PurchaseRequest
import id.co.sherly.mart.data.model.response.SignInResponse
import id.co.sherly.mart.data.model.response.SuccessResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Streaming

interface ImportService {

    @FormUrlEncoded
    @POST("import/upload")
    fun upload(
        @Field("file") file: String
    ): Call<SuccessResponse>

    @GET("import/data")
    fun data(
    ): Call<List<DocumentImport>?>

    @FormUrlEncoded
    @POST("import/process")
    fun import(
        @Field("purchaseImport") file: String,
        @Field("supplier") supplier: Int,
    ): Call<SuccessResponse>
}