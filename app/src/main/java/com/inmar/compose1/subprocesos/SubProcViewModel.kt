package com.inmar.compose1.subprocesos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inmar.compose1.data.SubProceso

class SubProcViewModel(idprocprim: String) : ViewModel(){

    private val _listaSubProcesos = MutableLiveData<List<SubProceso>>()
    val listaSubProcesos : LiveData<List<SubProceso>>
    get() = _listaSubProcesos
    
    fun setListaSubProcesos(idprocprim :String) {
        var lista : MutableList<SubProceso> =
            when(idprocprim.toInt()){
                100->{
                    mutableListOf(
                        SubProceso("101","Apertura de Cuenta Pensiones"),
                        SubProceso("102","Registro de Cuenta Otros Bancos"),
                        SubProceso("103","Registro de Cuenta Sucursal Banorte"),
                        SubProceso("104","Reposición de Plástico"),
                        SubProceso("105","Emisión de Plástico Adicional"),
                        SubProceso("106","Apertura de Cuenta Pensiones Híbrido"),
                        SubProceso("107","Apertura de Cuenta Paperless")
                    )
                }
                200->{
                    mutableListOf(
                        SubProceso("201","Solicitud de Préstamo PBG"),
                        SubProceso("202","Renovación de Préstamo PBG"),
                    )
                }
                300->{
                    mutableListOf(
                        SubProceso("301","Resolución"),
                        SubProceso("302","Acta de Defunción"),
                        SubProceso("303","Gastos de Marcha"),
                    )
                }
                400->{
                    mutableListOf(
                        SubProceso("401","Créditos Paperless")
                    )
                }
                500->{
                    mutableListOf(
                        SubProceso("501","Comprobación de Sobrevivencia")
                    )
                }
                else -> {
                    mutableListOf(SubProceso("000","ERROR"))
                }
            }
            
        _listaSubProcesos.value = lista
    }
    
}