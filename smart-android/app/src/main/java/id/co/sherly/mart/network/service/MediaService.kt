package id.co.sherly.mart.network.service

import id.co.sherly.mart.data.model.Media
import id.co.sherly.mart.data.model.response.SuccessResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MediaService {

    @FormUrlEncoded
    @POST("media/remove")
    fun removeMedia(
        @Field("uid") mediaUid: String
    ): Call<SuccessResponse>

    @FormUrlEncoded
    @POST("media/upload")
    fun uploadMedia(
        @Field("media") mediaUid: String
    ): Call<SuccessResponse>

//    @FormUrlEncoded
//    @POST("media/editorUpload")
//    fun editorUpload(
//        @Field("media") mediaUid: String
//    ): Call<EditorUpload>

    @GET("media/data")
    fun fetchMedia(
    ): Call<List<Media>?>
}