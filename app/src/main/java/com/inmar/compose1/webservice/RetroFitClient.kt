package com.inmar.compose1.webservice

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

class RetroFitClient {
    object RetroFitClient{
        /*
        private fun getClient() : Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://productos.winstondata.com/ws_bcr_104t/conVigenciav101.aspx")
                .client(OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val apiService : PensionesRetroFit =
            getClient().create(PensionesRetroFit::class.java)
        */
        val apibooksService : APIService = getBooksRetrofitClient().create(APIService::class.java)

        fun getBooksRetrofitClient() : Retrofit{

            val client = OkHttpClient.Builder()
            client.connectTimeout(2,TimeUnit.MINUTES)
            client.readTimeout(2,TimeUnit.MINUTES)
            client.writeTimeout(2,TimeUnit.MINUTES)

            val gson = GsonBuilder()
                .serializeNulls()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .setLenient()
                .create()

            return Retrofit.Builder()
                .baseUrl("https://www.rrtutors.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
    }
}