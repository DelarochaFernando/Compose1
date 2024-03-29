package com.inmar.compose1.consultafolios

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
//import androidx.lifecycle.viewmodel.CreationExtras.Empty.map
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.inmar.compose1.BaseViewModel
import com.inmar.compose1.data.Book
import com.inmar.compose1.data.CategoryWithBooks
import com.inmar.compose1.webservice.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import okhttp3.MediaType
import okhttp3.MediaType.Companion.parse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.internal.wait
import okio.internal.commonAsUtf8ToByteArray
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType
import org.apache.http.entity.mime.HttpMultipartMode
import org.apache.http.entity.mime.MultipartEntity
import org.apache.http.entity.mime.content.ByteArrayBody
import org.apache.http.entity.mime.content.StringBody
import org.apache.http.impl.client.DefaultHttpClient
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.awaitResponse
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.coroutines.CoroutineContext

class ConsultaFoliosViewModel(application: Application) : BaseViewModel(application) {

    var response  = ""

    private val _bookList = MutableStateFlow(Result.ResultBooks(state = State.Loading,categoryWithBooks = listOf()))
    val booklist get() = _bookList

    private val _foliosList = MutableStateFlow(Result.ResultFolios(state =State.Loading, listaFolios = listOf()))
    val foliosList get() = _foliosList

    init {
        fetchBooks()
        getFoliosFromServer(key = key,user = user)
        //getFoliosFromServerApache(key = key, user = user)
    }


    fun getFoliosFromServerApache(key: String, user: String){
        try {
            launch {
                val httpClient = DefaultHttpClient()
                val httpPostRequest = HttpPost(urlAPIPensiones)
                val json = ConsultaFoliosJson(key, user)
                val jsonString = "[${Gson().toJson(json)}]"
                val byteArray = jsonString.encodeToByteArray()
                Log.i("getFoliosFromServer - jsonString: ",jsonString)
                val reqEntity = MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE)
                val byteArrayBody = ByteArrayBody(byteArray,"forest.jpg")
                val photoCaptionBody = StringBody("photocaption", ContentType.APPLICATION_JSON)
                reqEntity.addPart("uploaded",byteArrayBody)
                reqEntity.addPart("photoCaption", StringBody("asasadgs"))
                httpPostRequest.entity = reqEntity


                    val response = httpClient.execute(httpPostRequest)


                val inputStream = response.entity.content
                val inputStreamReader = InputStreamReader(inputStream,"utf-8")
                val reader = BufferedReader(inputStreamReader)

                var sResponse = StringBuilder()
                while (reader.readLine()!= readlnOrNull()){
                    sResponse = sResponse.append(reader.readLine())
                }

                Log.i("getFoliosFromServerApache :", sResponse.toString())
            }

        }catch ( ex : Exception){
            ex.printStackTrace()
        }
    }

    fun getFoliosFromServer(key :String, user : String) {
        try {

            launch {
                val json = ConsultaFoliosJson(key, user)
                val jsonString = "[${Gson().toJson(json)}]"
                Log.i("getFoliosFromServer - jsonString: ",jsonString)
                val byteArray = jsonString.encodeToByteArray()
                Log.i("getFoliosFromServer - jsonString: ",jsonString)
                val reqEntity = MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE)
                val byteArrayBody = ByteArrayBody(byteArray,"forest.jpg")
                val photoCaptionBody = StringBody("photocaption", ContentType.APPLICATION_JSON)
                reqEntity.addPart("uploaded",byteArrayBody)
                reqEntity.addPart("photoCaption", StringBody("asasadgs"))

                val _requestBody = RequestBody.create(
                    "application/json".toMediaTypeOrNull(),
                    "{\"key\":\"50d0n27hb54ZMaqiSVpqO2Rn60d6m90ks05EhaZDnJfufNjyRF\"," +
                            "\"usuario\":\"TI_APP\"}"
                )
                val call = RetroFitClient
                    .RetroFitClient
                    .apiService
                    //.getConsultaVigentes(reqEntity)
                    //.getConsultaVigentes(bab = byteArrayBody, stringBody = photoCaptionBody)
                    //.getConsultaVigentes(byteArray,photoCaptionBody)
                    .getConsultaVigentes(requestBody = _requestBody)

                val response = call?.awaitResponse()

                if (response?.isSuccessful == true) {
                    val getResponse = response?.body()
                    if (getResponse?.estatus.equals("0")) {
                        val listaDeFolios = mutableListOf<DatosFol>()
                        getResponse?.datos?.forEach {
                            listaDeFolios.add(it)
                        }
                        _foliosList.emit(
                            Result.ResultFolios(state = State.Success, listaFolios = listaDeFolios)
                        )
                    } else if (getResponse?.estatus.equals("1")) {
                        _foliosList.emit(
                            Result.ResultFolios(state = State.Failed, listaFolios = listOf())
                        )
                    }else if (getResponse?.estatus.equals("101")){
                        _foliosList.emit(
                            Result.ResultFolios(state = State.Failed, listaFolios = listOf())
                        )
                    } else{
                        _foliosList.emit(
                            Result.ResultFolios(state = State.Failed, listaFolios = listOf())
                        )
                    }
                } else {
                    _foliosList.emit(
                        Result.ResultFolios(state = State.Failed, listaFolios = listOf())
                    )
                }
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        /*launch {
               //response = SimpleHttpRequest.getRequest(SimpleHttpRequest.key)
               response = SimpleHttpRequest.consultaVIgentes().toString()
               var jsonObject = JSONObject(response)
               var estatus = jsonObject.getString("estatus")
               var mensaje = jsonObject.getString("mensaje")
               var datos = jsonObject.getString("datos")

               var listofFolios = arrayListOf<JSONObject>()

               if(estatus.equals("0")){

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

           }*/
    }

    fun searchByName(query : String){

    }

    //https://rrtutors.com/tutorials/How-to-Integrate-REST-API-with-Jetpack-compose-in-Android

    //how to use POST request Retrofit2
    //https://code.tutsplus.com/es/tutorials/sending-data-with-retrofit-2-http-client-for-android--cms-27845

    public fun fetchBooks(){
        launch {
            try{
                val call = RetroFitClient.RetroFitClient.apibooksService.fetchBookList()
                val response = call?.awaitResponse()
                if(response?.isSuccessful == true){
                    val getResponse = response.body()
                    if(getResponse?.success == 1L){
                        val list = mutableListOf<CategoryWithBooks>()
                        val categories = mutableMapOf<String,String>()
                        getResponse.booklist.forEach{
                            categories[it.categoryId] = it.categoryName
                        }
                        categories.forEach{map ->
                            val books = getResponse.booklist.filter {it.categoryId == map.key}
                                .toMutableList()
                            books.sortBy { it.title }
                            list.add(CategoryWithBooks(map.key,map.value,books))
                        }
                        list.sortBy { it.name }
                        _bookList.emit( Result.ResultBooks(state = State.Success, categoryWithBooks = list))
                    }else{
                        _bookList.emit(Result.ResultBooks(state = State.Failed, categoryWithBooks = listOf()))
                    }
                }else{
                    _bookList.emit(Result.ResultBooks(state = State.Failed, categoryWithBooks = listOf()))
                }
            }catch (e:Exception){
                Log.e("FetchBooks Exception",e.message?:"",e)
                _bookList.emit(Result.ResultBooks(state = State.Failed, categoryWithBooks = listOf()))
            }
        }
    }

    sealed class Result{
        class ResultBooks(val state: State, val categoryWithBooks: List<CategoryWithBooks>) : Result()
        class ResultFolios(val state : State,val listaFolios : List<DatosFol>) : Result()
    }

    enum class State {
        Success,
        Failed,
        Loading
    }

    class ConsultaViewModelFactory(app: Application) : ViewModelProvider.Factory{
        val application = app
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(ConsultaFoliosViewModel::class.java)){

                return ConsultaFoliosViewModel(application = application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}