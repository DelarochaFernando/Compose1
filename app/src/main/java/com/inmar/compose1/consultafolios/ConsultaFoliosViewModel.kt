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
import okhttp3.internal.wait
import org.apache.http.entity.mime.HttpMultipartMode
import org.apache.http.entity.mime.MultipartEntity
import org.apache.http.entity.mime.content.ByteArrayBody
import org.apache.http.entity.mime.content.StringBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.awaitResponse

class ConsultaFoliosViewModel(application: Application) : BaseViewModel(application) {

    var response  = ""

    private val _bookList = MutableStateFlow(Result.ResultBooks(state = State.Loading,categoryWithBooks = listOf()))
    val booklist get() = _bookList

    private val _foliosList = MutableStateFlow(Result.ResultFolios(state =State.Loading, listaFolios = listOf()))
    val foliosList get() = _foliosList

    init {
        fetchBooks()
        getFoliosFromServer(key = key,user = user)
    }

    fun getFoliosFromServer(key :String, user : String) {
        try {
            var consultaFoliosResponse: ConsultaFoliosResponse? = null
            launch {
                val json = ConsultaFoliosJson(key, user)
                val jsonString = "[${Gson().toJson(json)}]"
                val byteArray = jsonString.toByteArray()
//                val reqEntity = MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE)
//                val byteArrayBody = ByteArrayBody(byteArray,"forest.jpg")
//                reqEntity.addPart("uploaded",byteArrayBody)
//                reqEntity.addPart("photoCaption", StringBody("asasadgs"))

                val call = RetroFitClient
                    .RetroFitClient
                    .apiService
                    .getConsultaVigentes(byteArray,"photoCaption")

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