/*
 * Copyright 2024 © JAGONET  All Rights Reserved by PT. SARANA MEDIA CEMERLANG
 * Unauthorized copying of this file, via any medium is strictly
 * prohibited Proprietary and confidential
 */

package id.co.sherly.mart.network.service

import id.co.sherly.mart.data.model.response.SignInResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {

    @FormUrlEncoded
    @POST("signin")
    fun signIn(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<SignInResponse?>

    @POST("user/auth")
    fun auth(): Call<SignInResponse>

    @POST("signout")
    fun signOut(): Call<String?>
}