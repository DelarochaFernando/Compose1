package com.inmar.compose1.webservice

import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URL

class SimpleHttpRequest {

    companion object{
        var client : OkHttpClient = OkHttpClient()
        val key = "50d0n27hb54ZMaqiSVpqO2Rn60d6m90ks05EhaZDnJfufNjyRF"
        var user = "TI_APP"

        fun getRequest(url : String) : String?{
            var result : String?  = null
            try {
                var url = URL(url)
                val request = Request.Builder().url(url).build()
                //var bab = ByteArrayBody

                val response = client.newCall(request).execute()
                result = response.body?.string()

            }catch (ex : Exception){
                ex.printStackTrace()
            }
            return result
        }
    }
}
