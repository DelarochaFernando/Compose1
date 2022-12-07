package com.inmar.compose1.subprocesos


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.ColumnScopeInstance.align
//import androidx.compose.foundation.layout.RowScopeInstance.align
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.inmar.compose1.data.ProcesoPrimario
import com.inmar.compose1.data.SubProceso
import com.inmar.compose1.menuprocprim.showDialogExitApp
import com.inmar.compose1.ui.theme.Purple500


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun SubProcesos(navController: NavController, idprocprim :String?, procprimText : String?){

    val subProcViewModel = SubProcViewModel(idprocprim!!)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "SubProcesos",
                        textAlign = TextAlign.Left,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                       Icon(
                           imageVector = Icons.Filled.ArrowBack,
                           contentDescription = "back to MenuProcPrim")
                    }
                }
            )
        },
        content = {
            Column() {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${procprimText!!}>",Modifier.size(25.dp))
                Spacer(modifier = Modifier.height(8.dp))
                subProcViewModel.setListaSubProcesos(idprocprim)
                val listOfProcess = subProcViewModel.listaSubProcesos.value
                LazyVerticalGrid(
                    contentPadding = it.apply {
                        PaddingValues(
                            start = 12.dp,
                            top = 16.dp,
                            end = 12.dp,
                            bottom = 16.dp
                        ) },
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.Center,
                    content = {
                        items(listOfProcess!!) { process ->
                            ListItem(
                                modifier = Modifier
                                    .padding(4.dp)
                            ) {
                                SubProcCard(
                                    onClickAction = {
                                        navController.navigate("poliza/${process.idSubProc}/${process.subProcText}")
                                    },
                                    process = process
                                )
                            }
                        }
                    }
                    //modifier = Modifier.padding(vertical = 8.dp)
                )
                //showDialogExitApp(navController = navController,openDialog)
            }
        }
    )
}


@Composable
fun SubProcCard(onClickAction: () -> Unit, process : SubProceso){
    Card(
        shape = RoundedCornerShape(CornerSize(4.dp)),
        modifier = Modifier
            .clickable(onClick = onClickAction)
    ) {
        Row(
            modifier = Modifier
                .background(Purple500)
                .fillMaxSize()
                .padding(40.dp)
        ) {
            Text(
                text = process.subProcText,
                color = Color.White
            )
        }
    }
}
