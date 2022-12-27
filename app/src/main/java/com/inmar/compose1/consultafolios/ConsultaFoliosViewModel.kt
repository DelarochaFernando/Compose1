package com.inmar.compose1.consultafolios

import android.app.Application
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.inmar.compose1.BaseViewModel
import com.inmar.compose1.webservice.ConsultaFoliosResponse
import com.inmar.compose1.webservice.DatosFol
import com.inmar.compose1.webservice.SimpleHttpRequest
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import org.json.JSONArray
import org.json.JSONObject

class ConsultaFoliosViewModel(application: Application) : BaseViewModel(application) {

    fun getFoliosFromServer() : ConsultaFoliosResponse?{

        var response : String? = null
        var consultaFoliosResponse : ConsultaFoliosResponse? = null
        launch {
            //response = SimpleHttpRequest.getRequest(SimpleHttpRequest.key)
            response = SimpleHttpRequest.consultaVIgentes()
            var jsonObject = JSONObject(response)
            var estatus = jsonObject.getString("estatus")
            var mensaje = jsonObject.getString("mensaje")
            var datos = jsonObject.getString("datos")

            var listofFolios = arrayListOf<JSONObject>()
            JSONArray(datos).let {
                it to listofFolios
            }
            for (folio in listofFolios){
                folio.let {
                    var jsonElement = JsonParser().parse(it.toString())
                    var datosFol = Gson().fromJson(jsonElement,DatosFol::class.java)
                    var consultaFoliosResponse = ConsultaFoliosResponse(estatus,mensaje,datosFol)
                }
            }
        }

        return consultaFoliosResponse

    }

}