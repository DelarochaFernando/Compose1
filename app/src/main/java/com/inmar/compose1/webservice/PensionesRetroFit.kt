package com.inmar.compose1.webservice

import retrofit2.Call
import retrofit2.http.GET

interface PensionesRetroFit {

    @GET("folios")
    fun getConsultaVigentes(key : String, user :String) : Call<ConsultaFoliosResponse>
}