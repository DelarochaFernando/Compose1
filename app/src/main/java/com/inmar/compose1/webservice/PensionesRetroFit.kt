package com.inmar.compose1.webservice

import okhttp3.RequestBody
import org.apache.http.entity.mime.MultipartEntity
import org.apache.http.entity.mime.content.ByteArrayBody
import org.apache.http.entity.mime.content.StringBody
import retrofit2.Call
import retrofit2.http.*

interface PensionesRetroFit {
//    @Headers("Accept: Application/json")
//    @Multipart
//    @POST("conVigenciav101.aspx")
//    fun getConsultaVigentes(
//        @Part("uploaded") bab : ByteArrayBody,
//        @Part("photocaption") stringBody: StringBody
//    ) : Call<ConsultaFoliosResponse>

//    @Headers("Accept: Application/json")
//    @Multipart
//    @POST("conVigenciav101.aspx")
//    fun getConsultaVigentes(
//        @Body reqEntity : MultipartEntity
//    ) : Call<ConsultaFoliosResponse>

    @Headers("Accept: Application/json")
    @POST("convigenciav101.aspx")
    fun getConsultaVigentes(
        @Body requestBody: RequestBody
    ) : Call<ConsultaFoliosResponse>
}