package com.inmar.compose1.webservice

import okhttp3.RequestBody
import org.apache.http.entity.mime.MultipartEntity
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PensionesRetroFit {



    @Multipart
    @POST("/conVigencia.aspx")
    fun getConsultaVigentes(
        @Part("uploaded") bab : ByteArray,
        @Part("photoCaption") caption : String) : Call<ConsultaFoliosResponse>
}