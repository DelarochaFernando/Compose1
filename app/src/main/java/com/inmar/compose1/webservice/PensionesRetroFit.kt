package com.inmar.compose1.webservice

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface PensionesRetroFit {

    @POST("/conVigencia.aspx")
    fun getConsultaVigentes(key : String, user :String) : Call<ConsultaFoliosResponse>
}