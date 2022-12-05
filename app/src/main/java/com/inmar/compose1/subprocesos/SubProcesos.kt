package com.inmar.compose1.subprocesos

import android.service.autofill.OnClickAction
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.ColumnScopeInstance.align
//import androidx.compose.foundation.layout.RowScopeInstance.align
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.lazy.*
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
import java.util.Map


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun SubProcesos(navController: NavController, idprocprim :String?){

    var subProcViewModel = SubProcViewModel(idprocprim!!)

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
        }
    ) {
        Column() {
            Spacer(modifier = Modifier.height(8.dp))
            subProcViewModel.setListaSubProcesos(idprocprim)
            var listOfProcess = subProcViewModel.listaSubProcesos.value
            LazyVerticalGrid(
                contentPadding = it.apply {
                    PaddingValues(
                        start = 12.dp,
                        top = 16.dp,
                        end = 12.dp,
                        bottom = 16.dp
                    ) },
                cells = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.Center,
                //modifier = Modifier.padding(vertical = 8.dp)
            ) {
                items(listOfProcess!!) { process ->
                    ListItem(
                        modifier = Modifier
                            .padding(4.dp)
                    ) {
//                        TextButton(
//                            onClick = { navController.navigate("poliza/{${process.idSubProc}}") }
//                        ) {
//                            Text(text = process.subProcText)
//                        }
                        subProcCard(
                            onClickAction = {navController.navigate("poliza/${process.idSubProc}")},
                            process = process
                        )
                    }
                }
            }
            //showDialogExitApp(navController = navController,openDialog)
        }
    }
}


@Composable
fun subProcCard(onClickAction: () -> Unit, process : SubProceso){
    Card(
        shape = RoundedCornerShape(CornerSize(4.dp)),
        modifier = Modifier
            .wrapContentSize(Alignment.Center,true)
    ) {
        TextButton(
            modifier = Modifier
                .background(Purple500)
                .padding(8.dp),
            onClick = {onClickAction}) {
            Text(
                text = process.subProcText,
                color = Color.White
            )
        }
    }
}
