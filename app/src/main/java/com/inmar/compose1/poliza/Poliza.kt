package com.inmar.compose1.poliza

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.inmar.compose1.menuprocprim.MenuProcPrimPreview
import com.inmar.compose1.menuprocprim.MenuProcPrimViewModel
import java.time.format.TextStyle
import kotlin.math.max

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Poliza(navController: NavController,
           _procprimText : String?,
           idsubproc : String?,
           subproctext : String?){

    val menuProcPrimViewModel = MenuProcPrimViewModel()
    var procprimText = rememberSaveable { mutableStateOf(_procprimText)}
    var polizanumber = rememberSaveable { mutableStateOf("")}
    var nucleo = rememberSaveable{ mutableStateOf("")}
    var regimen = rememberSaveable { mutableStateOf("")}
    var regimenSelected = rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar (
                title = {
                    Text(
                        text = "Validación Poliza",
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
                            contentDescription = "back to SubProcesos")
                    }
                }
            )
        },
        content = {
            Column() {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${procprimText.value!!} >",
                    fontSize = 30.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                    //Modifier.fillMaxWidth().padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${subproctext!!} >",
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp))
                Spacer(modifier = Modifier.height(32.dp))
                Text(text = "Elige el régimen adecuado.",
                    modifier = Modifier.align(Alignment.CenterHorizontally))
                polizaRegimenYNucleo(
                    onTextOutPut = {polizanumber.value = it},
                    polizaNumber = polizanumber,
                    nucleo = nucleo,
                    regimen = regimen,
                    regimenSelected = regimenSelected)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
//@Preview
fun polizaRegimenYNucleo(onTextOutPut : (String) ->Unit,
polizaNumber: MutableState<String>, nucleo : MutableState<String>,
regimen : MutableState<String>,regimenSelected : MutableState<Boolean>
){

    var enableChip = rememberSaveable { mutableStateOf(false) }
    Column() {
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            //verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {

            Chip(
                onClick = {
                    regimen.value = "IMSS"
                    regimenSelected.value = true
                },
                leadingIcon = {
                    Icon(
                        Icons.Filled.Check,
                        contentDescription = "IMSS Chip",
                        Modifier.size(AssistChipDefaults.IconSize)
                    )
                },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(text = "IMSS")
            }
            Chip(
                onClick = {
                    regimen.value = "ISSSTE"
                    regimenSelected.value = true
                },
                leadingIcon = {
                    Icon(
                        Icons.Filled.Check,
                        contentDescription = "ISSSTE Chip",
                        Modifier.size(AssistChipDefaults.IconSize)
                    )
                }
            ) {
                Text(text = "ISSSTE")
            }


//            AssistChip(
//                onClick = {
//                    regimen.value = "IMSS"
//                    regimenSelected.value = true
//                },
//                label = { Text(text = "IMSS") },
//                leadingIcon = {
//                    Icon(
//                        Icons.Filled.Check,
//                        contentDescription = "Localized description",
//                        Modifier.size(AssistChipDefaults.IconSize)
//                    )
//                },
//                modifier = Modifier.padding(end = 8.dp)
//            )
//            AssistChip(
//                onClick = {
//                    regimen.value = "ISSSTE"
//                    regimenSelected.value = true
//                },
//                label = { Text(text = "ISSSTE") },
//                leadingIcon = {
//                    Icon(
//                        Icons.Filled.Check,
//                        contentDescription = "Localized description",
//                        Modifier.size(AssistChipDefaults.IconSize)
//                    )
//                }
//            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
        ) {
            Text(text = "hola ")
            Text(text = "mundo")
        }
    }
    
}