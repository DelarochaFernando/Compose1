package com.inmar.compose1.menuprocprim

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.inmar.compose1.data.ProcesoPrimario
import com.inmar.compose1.ui.theme.Purple500

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun MenuProcPrim(navController: NavController){

    //val navController = rememberNavController()
    val openDialog = remember { mutableStateOf(false)}
    var _idProcPrim = rememberSaveable { mutableStateOf("")}
    var procPrimViewModel = MenuProcPrimViewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Menu Principal",
                        textAlign = TextAlign.Left,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                navigationIcon = {},
                actions = {
                    /*IconButton(onClick = {*//*Refresh List*//*}) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "List Refresh"
                        )
                    }*/
                    IconButton(
                        onClick = {
                            openDialog.value = true
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ExitToApp,
                            contentDescription = "Exit to Login"
                        )
                    }
                }
            )
        }
    ) {
        procPrimViewModel.setListaProcPrim()
        Column() {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Elige el trámite que desees realizar."
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyVerticalGrid(
                contentPadding = it.apply { PaddingValues(20.dp)},
//                    contentPadding = it.apply {
//                        PaddingValues(
//                            start = 12.dp,
//                            top = 16.dp,
//                            end = 12.dp,
//                            bottom = 16.dp
//                        ) },
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                content = {
                    items(procPrimViewModel.listaProcesosPrimarios.value!!) { process ->
                        ListItem(
                            modifier = Modifier
                                //.padding(20.dp)
                                //.sizeIn(minHeight = 70.dp, minWidth = 160.dp)
                        ) {
                            procprimBox(
                                onClickAction = {
                                    _idProcPrim.value = process.idProcPrim
                                    navController.navigate("subprocesos/${_idProcPrim.value}/${process.procPrimText}")
                                },
                                process = process
                            )
                        }
                    }
                }
                //modifier = Modifier.padding(vertical = 8.dp)
            )
            showDialogExitApp(navController = navController, openDialog)
            /*Column() {
                listOfProcess.forEach { process ->
                    ListItem(
                        icon = {
                            Icon(imageVector = process.icon, contentDescription = process.idProcPrim)
                        },
                        text = { Text(text = process.procPrimText)}
                    )
                }
            }*/
            /*Box(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)) {
                LazyColumn(
                    Modifier.padding(innerPadding),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ){
                    val list = (0..25).map { it.toString() }
                    items(count = list.size){
                        //ListItem(headlineText = { Text(text = "Producto 1")})
                    }
                }
            }*/
        }
    }

}

//@Preview
@Composable
fun MenuProcPrimPreview(){
    val navController = rememberNavController()
    MenuProcPrim(navController)
}

@Composable
fun showDialogExitApp(navController: NavController,
                      openDialog : MutableState<Boolean>) {

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = "Cerrar Sesión.") },
            text = { Text(text = "¿Desea abandonar la sesión?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        navController.navigate("login")
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Cancelar")
                }
            },
            modifier = Modifier.padding(50.dp)
        )
    }
}

@Composable
fun procprimBox(
    onClickAction : () -> Unit,
    process: ProcesoPrimario,
){
    Card(
        shape = RoundedCornerShape(CornerSize(4.dp)),
        elevation = 8.dp,
        modifier = Modifier
            //.padding(40.dp)
            .clickable (onClick = onClickAction)
    ) {
        //IconButton(
           // modifier = Modifier.background(Purple500),
          //  onClick = { onClickAction },
        //) {
            Row(modifier = Modifier
                .background(Purple500)
                .fillMaxSize()
                .padding(40.dp)
            ) {
                Icon(
                    imageVector = process.icon,
                    contentDescription = process.idProcPrim,
                    tint= Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Text(
                    text = process.procPrimText,
                    color = Color.White
                )
            }
        //}
    }
}

@Composable
@Preview
fun procprimView(){
    Card(
        shape = RoundedCornerShape(CornerSize(4.dp)),
        elevation = 8.dp,
        modifier = Modifier
            .padding(8.dp)
        //.clickable (onClick = onClickAction)
    ) {
        //IconButton(
        // modifier = Modifier.background(Purple500),
        //  onClick = { onClickAction },
        //) {
        Row(modifier = Modifier
            .background(Purple500)
            .padding(20.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.GroupAdd,
                contentDescription = "Apertura de Cuenta",
                tint= Color.White,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = "Apertura de Cuenta",
                color = Color.White,
                modifier = Modifier
                    .padding(end = 8.dp)
            )
        }
        //}
    }
}
