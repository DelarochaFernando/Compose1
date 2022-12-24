package com.inmar.compose1.webservice

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetroFitClient {
    object RetroFitClient{
        private fun getClient() : Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://productos.winstondata.com/ws_bcr_104t/conVigenciav101.aspx")
                .client(OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val apiService : PensionesRetroFit =
            getClient().create(PensionesRetroFit::class.java)
    }
}