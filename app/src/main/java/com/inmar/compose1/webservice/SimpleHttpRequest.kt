package com.inmar.compose1.webservice

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.mime.HttpMultipartMode
import org.apache.http.entity.mime.MultipartEntity
import org.apache.http.entity.mime.content.ByteArrayBody
import org.apache.http.impl.client.HttpClientBuilder
import java.io.BufferedReader
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

        fun consultaVIgentes(): String {

            var jsonResponse = ""
            try {

                var consultaFoliosJson = Gson().toJson(ConsultaFoliosJson(key,user))
                val httpClient = HttpClientBuilder.create().build()
                val bab = ByteArrayBody(consultaFoliosJson.toByteArray(),"forest.jpg")
                val multipartEntity = MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE)
                multipartEntity.addPart("uploaded",bab)
                val httpost = HttpPost()
                httpost.entity = multipartEntity

                val response = httpClient.execute(httpost)
                val inputStream = response.entity.content
                val inputStreamReader = inputStream.reader()
                val reader = BufferedReader(inputStreamReader)
                var sResponse = ""

                var s = StringBuilder()

                while (reader.readLine().also { sResponse = it } != null) {
                    s = s.append(sResponse)
                }

                jsonResponse = s.toString()

            }catch (e : Exception){
                e.printStackTrace()
                jsonResponse = "1;Error"
            }finally {
                try {
                    /*if ( != null) {
                        reqEntity.getContent().close()
                    }
                    if (reader != null) {
                        reader.close()
                    }
                    if (inputStream != null) {
                        inputStream.close()
                    }
                    if (inputStreamReader != null) {
                        inputStreamReader.close()
                    }
                    if (response != null) {
                        response.getEntity().getContent().close()
                    }*/
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                return jsonResponse
            }


        }
    }
}
