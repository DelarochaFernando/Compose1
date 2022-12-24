package com.inmar.compose1.webservice

import retrofit2.http.GET

interface PensionesRetroFit {

    @GET("folios")
    suspend fun getConsultaVigentes(key : String, user :String) : ConsultaFoliosResponse
}