package com.inmar.compose1.webservice

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

class RetroFitClient {
    object RetroFitClient{

        private fun getPensionesClient() : Retrofit {

            val gson = GsonBuilder()
                .serializeNulls()
                .setLenient()
                .create()
            val okHttpClient = OkHttpClient.Builder()
            val httpInterpector = HttpLoggingInterceptor()
            httpInterpector.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient.interceptors().add(httpInterpector)

            return Retrofit.Builder()
                .baseUrl("https://productos.winstondata.com/ws_bcr_104t/")
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

        val apiService : PensionesRetroFit =
            getPensionesClient().create(PensionesRetroFit::class.java)

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