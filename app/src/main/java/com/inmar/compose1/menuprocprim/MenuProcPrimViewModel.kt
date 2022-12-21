package com.inmar.compose1.menuprocprim

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inmar.compose1.data.ProcesoPrimario

class MenuProcPrimViewModel : ViewModel() {

    private val _listaProcesosPrimarios = MutableLiveData<List<ProcesoPrimario>>()
    val listaProcesosPrimarios : LiveData<List<ProcesoPrimario>>
    get() = _listaProcesosPrimarios

    private val _textoProcPrim = MutableLiveData<String>()
    val textoProcPrim : LiveData<String>
    get() = _textoProcPrim

    fun setListaProcPrim(){
        var listOfProcess = mutableListOf<ProcesoPrimario>()
        listOfProcess.add(ProcesoPrimario(Icons.Filled.GroupAdd,"100","Apertura de Cuenta"))
        listOfProcess.add(ProcesoPrimario(Icons.Filled.LocalAtm,"200","Prestamo"))
        listOfProcess.add(ProcesoPrimario(Icons.Filled.Description,"300","Mentenimiento de Cartera"))
        listOfProcess.add(ProcesoPrimario(Icons.Filled.Assessment,"400","Tramites Paperless"))
        listOfProcess.add(ProcesoPrimario(Icons.Filled.HowToReg,"900","Autenticacion INE"))
        listOfProcess.add(ProcesoPrimario(Icons.Filled.VideoCall,"800","Autenticacion Alterna"))
        //listOfProcess.add("Comprobacion de Sobrevivencia")
        listOfProcess.add(ProcesoPrimario(Icons.Filled.Search,"00","Consulta de Folios"))

        _listaProcesosPrimarios.value = listOfProcess
    }

    fun getProcPrimText(idprocprim : String){

        var listProcPrim = _listaProcesosPrimarios.value

        listProcPrim!!.forEach {
          procesoPrimario ->
            if(procesoPrimario.idProcPrim.equals(idprocprim)){
                _textoProcPrim.value = procesoPrimario.procPrimText
            }
        }
    }
}