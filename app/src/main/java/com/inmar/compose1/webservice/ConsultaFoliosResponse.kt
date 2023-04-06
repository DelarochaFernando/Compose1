package com.inmar.compose1.webservice


data class ConsultaFoliosJson(val key : String, val user : String)

val key = "50d0n27hb54ZMaqiSVpqO2Rn60d6m90ks05EhaZDnJfufNjyRF"
val user = "TI_APP"
val urlAPIPensiones = "https://productos.winstondata.com/ws_bcr_104t/"

data class ConsultaFoliosResponse(
    val estatus : String,
    val mensaje : String,
    val datos : MutableList<DatosFol>
)

data class DatosFol(
    var folio: String? = null,
    var poliza: String? = null,
    var nucleo: String? = null,
    var id_prim_proc: String? = null,
    var proc_prim: String? = null,
    var id_sub_proc: String? = null,
    var sub_proc: String? = null,
    var regimen: String? = null,
    var nombre :String? = null,//anterior razon
    var nss: String? = null,
    var estatus: String? = null,
    var obs: String? = null,
    var acc: String? = null,
    var pde: String? = null,
    var banderaAviso: String? = null,
    var flg_aut_ine: String? = null,
    var sub_proc_aux: String? = null,
    //String razon;
    //String regimen;
    var fec_alt: String? = null,
    var fec_mod: String? = null,
    //propiedades correspondientes a Autenticacion INE-beta
    var ocr: String? = null,
    var id_set: String? = null,
    var numeroDeEmision: String? = null,
    var claveElector: String? = null,
    var cic: String? = null,
    //Propiedades Sobrevivencia 500
    var ClaveSobrevivencia: String? = null,
    var NombreSobrevivencia: String? = null,//Propiedades Sobrevivencia 500
    var ParentescoSobrevivencia: String? = null,//Propiedades Sobrevivencia 500
    var SexoSobrevivencia: String? = null,//Propiedades Sobrevivencia 500
    var FechaNacSobrevivencia: String? = null,//Propiedades Sobrevivencia 500
    //Autenticación Alterna
    var flg_aut_alter: String? = null,
    var intent_aut_alter: String? = null,//Autenticación Alterna
    var id_img_aut_alter: String? = null,//Autenticación Alterna
    var id_vid_aut_alter: String? = null,//Autenticación Alterna
    var fec_aut_alter: String? = null,//Autenticación Alterna
    var accu_aut_alter: String? = null,//Autenticación Alterna
    var intent_disp_autalter: String? = null,
    var id_estatus: String? = null
)
