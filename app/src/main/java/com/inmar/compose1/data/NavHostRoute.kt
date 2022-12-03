package com.inmar.compose1.data

sealed class NavHostRoute(val path: String){

    object Login : NavHostRoute("login")

    object MenurProcPrim : NavHostRoute("menuprocprim")

    object SubProcesos : NavHostRoute("subprocesos"){
        val idSubProc = "idSubProc"
    }


}
